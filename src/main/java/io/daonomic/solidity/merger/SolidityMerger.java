package io.daonomic.solidity.merger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolidityMerger {
    public static final Pattern IMPORT_PATTERN = Pattern.compile("import ['\"](.+?)['\"];(\\s)*");
    public static final Pattern PRAGMA_PATTERN = Pattern.compile("pragma solidity (.+?);(\\s)*");

    private final SourceLoader loader;

    public SolidityMerger(SourceLoader loader) {
        this.loader = loader;
    }

    public String merge(String sourcePath) throws IOException {
        return merge(sourcePath, new HashSet<>());
    }

    private String merge(String sourcePath, Set<String> imported) throws IOException {
        final String source;
        if (imported.isEmpty()) {
            source = loader.load(sourcePath);
        } else {
            source = stripPragma(loader.load(sourcePath));
        }
        imported.add(sourcePath);
        return replace(source, IMPORT_PATTERN, m -> {
            String importPath = m.group(1);
            String realPath = getRealPath(sourcePath, importPath);
            if (!imported.contains(realPath)) {
                String merged = merge(realPath, imported);
                if (merged.endsWith("\n\n")) {
                    return merged;
                } else if (merged.endsWith("\n")) {
                    return merged + "\n";
                } else {
                    return merged + "\n\n";
                }
            } else {
                return "";
            }
        });
    }

    private String stripPragma(String source) throws IOException {
        return replace(source, PRAGMA_PATTERN, m -> "");
    }

    private String replace(String source, Pattern pattern, IOExceptionFunction<Matcher, String> f) throws IOException {
        Matcher m = pattern.matcher(source);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(result, f.apply(m));
        }
        m.appendTail(result);
        return result.toString();
    }

    private String getRealPath(String sourcePath, String importPath) {
        if (importPath.startsWith("./") || importPath.startsWith("../")) {
            Path path = Paths.get(sourcePath).getParent();
            return path.resolve(importPath).normalize().toString();
        } else {
            return findNpmImport(Paths.get(sourcePath).getParent(), importPath);
        }
    }

    private String findNpmImport(Path path, String importPath) {
        final String current;
        if (path == null) {
            current = Paths.get("node_modules").resolve(importPath).normalize().toString();
        } else {
            current = path.resolve("node_modules").resolve(importPath).normalize().toString();
        }
        if (loader.exists(current)) {
            return current;
        }
        if (path != null) {
            return findNpmImport(path.getParent(), importPath);
        } else {
            throw new IllegalArgumentException("Unable to find file by import path: " + importPath);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new SolidityMerger(new FileSourceLoader(".")).merge(args[0]));
    }
}
