package com.snobot.lib.logging;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.junit.Test;

public class TestLogger
{

    @Test
    public void testLogger()
    {
        Logger logger = new Logger();
        logger.startLogging(new SimpleDateFormat(""), ".");
        logger.initializeLogger();
        logger.addHeader("StringHeader");
        logger.addHeader("IntHeader");
        logger.addHeader("DoubleHeader");
        logger.addHeader("BooleanAHeader");
        logger.addHeader("BooleanBHeader");
        logger.endHeader();
        logger.startRow();
        logger.updateLogger("Hello");
        logger.updateLogger(1);
        logger.updateLogger(1.0);
        logger.updateLogger(false);
        logger.updateLogger(true);
        logger.endRow();
        logger.flush();
        logger.stop();
    }

    @Test
    public void testLoggerWithBadDirectory()
    {
        Logger logger = new Logger();
        logger.startLogging(new SimpleDateFormat(""), "does_not_exist");
        logger.initializeLogger();
        logger.addHeader("StringHeader");
        logger.addHeader("IntHeader");
        logger.addHeader("DoubleHeader");
        logger.addHeader("BooleanAHeader");
        logger.addHeader("BooleanBHeader");
        logger.endHeader();
        logger.startRow();
        logger.updateLogger("Hello");
        logger.updateLogger(1);
        logger.updateLogger(1.0);
        logger.updateLogger(false);
        logger.updateLogger(true);
        logger.endRow();
        logger.flush();
        logger.stop();

    }

    @Test
    public void testJavaLogger()
    {
        LogManager.getLogManager().getLogger("").getHandlers()[0].setFormatter(new LogFormatter());
        java.util.logging.Logger.getGlobal().log(Level.INFO, "Test");
    }
}
