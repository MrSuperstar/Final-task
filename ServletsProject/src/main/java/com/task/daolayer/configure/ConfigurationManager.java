package main.java.com.task.daolayer.configure;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * A class for retrieving data from configuration files by keys.
 * The class is implemented by the singleton pattern.
 */
public class ConfigurationManager {

    private static final ConfigurationManager ourInstance = new ConfigurationManager();
    private static final ResourceBundle bundle = ResourceBundle.getBundle("config");

    public static ConfigurationManager getInstance() {
        return ourInstance;
    }

    private ConfigurationManager() {

    }


    public String getDataByKey(String key) {
        String s = bundle.getString(key);
        return s;
    }
}
