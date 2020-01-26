package main.java.com.task.daolayer.configure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * A class for retrieving data from configuration files by keys.
 * The class is implemented by the singleton pattern.
 */
public class ConfigurationManager {

    private static final Logger logger = Logger.getLogger(ConfigurationManager.class);
    private static final ConfigurationManager ourInstance = new ConfigurationManager();
    private static final String PATH_FROM_FILE = "E:\\Development\\Final-task\\ServletsProject\\resources\\config.properties";
    private static final String DEFAULT_KEY = "db.url";
    private FileInputStream fileInputStream = null;

    public static ConfigurationManager getInstance() {
        return ourInstance;
    }

    private ConfigurationManager() {

    }


    public String getDataByKey(String key) throws IOException {
        String data = null;
        Properties property = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_FROM_FILE);
            property.load(fileInputStream);
            data = property.getProperty(key, DEFAULT_KEY);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {
            fileInputStream.close();
        }

        return data;
    }
}
