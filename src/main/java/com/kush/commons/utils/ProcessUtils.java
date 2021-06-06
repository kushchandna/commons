package com.kush.commons.utils;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ProcessUtils {

    public static int launch(Class<?> klass, Object... args) throws IOException, InterruptedException {

        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
                File.separator + "bin" +
                File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = klass.getName();

        List<String> command = new LinkedList<>();
        command.add(javaBin);
        command.add("-cp");
        command.add(classpath);
        command.add(className);
        if (args != null) {
            command.addAll(Stream.of(args)
                .map(String::valueOf)
                .collect(toList()));
        }

        ProcessBuilder builder = new ProcessBuilder(command);

        Process process = builder.inheritIO().start();
        process.waitFor();
        return process.exitValue();
    }

}
