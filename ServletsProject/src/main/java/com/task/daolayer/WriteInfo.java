package main.java.com.task.daolayer;

import main.java.com.task.daolayer.mysqldao.MySqlDaoCrudEmployee;
import org.apache.log4j.Logger;

public class WriteInfo {
    private static final Logger LOGGER = Logger.getLogger(MySqlDaoCrudEmployee.class);

    public static void writeErrorMessage(String message) {
        LOGGER.error(message);
    }

    public static void writeInfoMessage(String message) {
        LOGGER.info(message);
    }
}
