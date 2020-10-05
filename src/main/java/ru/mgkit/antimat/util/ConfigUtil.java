package ru.mgkit.antimat.util;

import ru.mgkit.antimat.Main;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigUtil {

    public static enum ConfigParam {
        PORT("port"),
        VK_TOKEN("vk.token"),
        DICTIONARY_FILE("dictionary.file"),
        DICTIONARY_DELIM("dictionary.delimiter");

        private String key;

        ConfigParam(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }

    private static List<String> badWords;

    public static Properties getConfig(String filename) {
        Properties properties = new Properties();
        try {
            if (!new File(filename).exists()) loadDefaultConfigFile(filename);
            properties.load(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static List<String> getBadWords() {
        return badWords;
    }

    public static String getConfigValue(String filename, ConfigParam param) {
        return getConfig(filename).getProperty(param.getKey());
    }

    public static void loadDefaultConfigFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            System.out.printf("Config [%s] already exists\n", path);
            return;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(path)) {
            if (Main.class.getClassLoader().getResourceAsStream("config.properties") != null)
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("config.properties")))) {

                    reader.lines().forEach(line -> {
                        try {
                            fos.write((line + "\n").getBytes(Charset.forName("UTF-8")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    System.out.printf("Default config [%s] created!\n", path);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadDictionary(String filePath, String delim) {

        File file = new File(filePath);
        if (file.exists()) {
            System.out.printf("Dictionary [%s] already exists\n", filePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                loadDictionary(reader.lines().collect(Collectors.joining("")).split(delim));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            if (Main.class.getClassLoader().getResourceAsStream("dictionary.file") != null)
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("dictionary.file")))) {
                    List<String> words = new ArrayList<>();
                    reader.lines().forEach(line -> {
                        Arrays.stream(line.split(",")).forEach(word -> {
                            try {
                                fos.write((word.trim() + ",\n").getBytes(Charset.forName("UTF-8")));
                                words.add(word);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    });
                    System.out.printf("Default dictionary [%s] created!\n", filePath);
                    loadDictionary(words);
                }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDictionary(String... words) {
        loadDictionary(Arrays.asList(words));
    }

    public static void loadDictionary(List<String> words) {
        badWords = words;
        System.out.printf("Dictionary filled with {%s} words\n", words.size());
    }

}
