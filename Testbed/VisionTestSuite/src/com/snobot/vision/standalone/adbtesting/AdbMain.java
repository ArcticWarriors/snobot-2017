package com.snobot.vision.standalone.adbtesting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class AdbMain
{
    public final static class LogFormatter extends Formatter
    {

        private static final String LINE_SEPARATOR = System.getProperty("line.separator");

        @Override
        public String format(LogRecord record)
        {
            StringBuilder sb = new StringBuilder();

            Date date = new Date(record.getMillis());
            SimpleDateFormat df = new SimpleDateFormat("y/M/d H:m:s");

            sb.append(df.format(date)).append(" ").append(record.getLevel().getLocalizedName()).append(": ")
                    .append(formatMessage(record)).append(LINE_SEPARATOR);

            if (record.getThrown() != null)
            {
                try
                {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    record.getThrown().printStackTrace(pw);
                    pw.close();
                    sb.append(sw.toString());
                }
                catch (Exception ex)
                {
                    // ignore
                }
            }

            return sb.toString();
        }
    }

    public static void main(String[] args)
    {
        LogManager.getLogManager().getLogger("").getHandlers()[0].setFormatter(new LogFormatter());
        new VisionAdbServer(8254);
    }
}
