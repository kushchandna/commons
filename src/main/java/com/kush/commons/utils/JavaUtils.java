package com.kush.commons.utils;

import static java.util.Arrays.asList;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class JavaUtils {

    public static void compile(String targetDirectory, JavaFileObject... javaFileObjects) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String classPath = Arrays.toString(getClassPath()).replace(", ", ";").replace('\\', '/');
        String classPathText = "\"" + classPath.substring(1, classPath.length() - 1) + "\"";
        Iterable<String> options = Arrays.asList(
                "-d", targetDirectory,
                "-cp", classPathText);
        CompilationTask task = compiler.getTask(null, null, null, options, null, asList(javaFileObjects));
        task.call();
    }

    private static File[] getClassPath() {
        URLClassLoader classLoader = (URLClassLoader) JavaUtils.class.getClassLoader();
        URL[] urls = classLoader.getURLs();
        return Arrays.stream(urls).map(url -> new File(url.getFile())).toArray(File[]::new);
    }
}
