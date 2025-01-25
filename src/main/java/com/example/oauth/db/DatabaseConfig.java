package com.example.oauth.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final String CONFIG_FILE = "db.properties";

    private static final Properties properties = loadProperties();


    private static Properties loadProperties() {
        Properties props = new Properties();
        try(InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if(input == null) {
                throw new RuntimeException("Unable to find " + CONFIG_FILE);
            }

            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error loading properties file: " + CONFIG_FILE);
        }
        return props;
    }

    public static String getUrl() {
        return properties.getProperty("db.url");

    }

    public static String getUsername(){
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
