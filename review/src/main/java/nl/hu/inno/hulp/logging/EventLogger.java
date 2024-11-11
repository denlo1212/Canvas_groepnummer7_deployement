package nl.hu.inno.hulp.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EventLogger {
    private static final Logger logger = Logger.getLogger(EventLogger.class.getName());

    static {
            logger.setLevel(Level.INFO);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warning(message);
    }

    public static void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}

