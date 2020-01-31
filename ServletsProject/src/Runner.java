import main.java.com.task.daolayer.configure.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    private static final Logger logger = LogManager.getLogger(Runner.class);
    public static void main(String[] args) {
        System.out.println("ok");

        if(logger.isDebugEnabled()){
            logger.debug("This is debug");
        }

        //logs an error message with parameter
        logger.error("This is error : " + 1);

        //logs an exception thrown from somewhere
        logger.error("This is error");

        logger.trace("HELLO");
    }
}
