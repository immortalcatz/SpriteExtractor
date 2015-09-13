package io.github.mribby.junkjack;

import org.apache.commons.io.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Downloader {
    private static final File LIB_DIRECTORY = new File("lib");
    private static Method addURL;

    public static void downloadDependencies() {
        System.out.println("Downloading dependencies");
        downloadDependency("org.apache.commons.io.IOUtils", "commons-io", "commons-io", "2.4", false);
        downloadDependency("org.json.JSONArray", "org/json", "json", "20140107");
        System.out.println("Downloaded dependencies");
    }

    public static File createDir(File dir) {
        if ((dir.exists() || !dir.mkdirs()) && !dir.isDirectory()) {
            throw new RuntimeException("Could not create directory: " + dir.getName());
        }

        return dir;
    }

    private static void downloadDependency(String className, String groupId, String artifactId, String version) {
        downloadDependency(className, groupId, artifactId, version, true);
    }

    private static void downloadDependency(String className, String groupId, String artifactId, String version, boolean hasCommonsIO) {
        if (!isExistingClass(className)) {
            createDir(LIB_DIRECTORY);

            String fileName = artifactId + "-" + version + ".jar";
            File file = new File(LIB_DIRECTORY, fileName);

            if (!file.exists()) {
                InputStream input = null;
                FileOutputStream output = null;

                try {
                    URL url = new URL(String.format("http://central.maven.org/maven2/%s/%s/%s/%s", groupId, artifactId, version, fileName));

                    if (hasCommonsIO) {
                        FileUtils.copyURLToFile(url, file);
                    } else {
                        input = url.openStream();
                        output = new FileOutputStream(file);

                        byte[] data = new byte[4096];
                        int count;

                        while ((count = input.read(data)) != -1) {
                            output.write(data, 0, count);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Could not download file: " + fileName, e);
                } finally {
                    close(output, input);
                }
            }

            addFileToClasspath(file);
        }
    }

    private static boolean isExistingClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static void addFileToClasspath(File file) {
        try {
            if (addURL == null) {
                addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
            }

            addURL.invoke(ClassLoader.getSystemClassLoader(), file.toURI().toURL());
        } catch (Exception e) {
            throw new RuntimeException("Could not add file to classpath: " + file.getName(), e);
        }
    }

    private static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }
}
