package prj.library.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to load configuration from a properties file
 */
public class ConfigLoader {
    private static final String CONFIG_FILE = "/config.properties";
    private static Properties properties;

    static {
        loadConfig();
    }

    /**
     * Method that loads configuration from the properties file
     */
    private static void loadConfig() {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("Cannot find " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration", ex);
        }
    }

    /**
     * Method to get a property from the configuration file
     * @param key the key of the property
     * @return the value of the property
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property " + key + " not found in config file");
        }
        return value;
    }
}