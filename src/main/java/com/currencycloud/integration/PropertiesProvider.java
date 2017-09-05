package com.currencycloud.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProvider {

    private static Properties properties = new Properties();

    static {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = loader.getResourceAsStream("rest.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertiesProvider.get("do.i"));
    }

}
