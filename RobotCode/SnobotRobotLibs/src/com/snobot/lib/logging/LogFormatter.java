package com.snobot.lib.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class LogFormatter extends Formatter
{

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public String format(LogRecord record)
    {
        StringBuilder sb = new StringBuilder();

        Date date = new Date(record.getMillis());
        SimpleDateFormat df = new SimpleDateFormat("y/M/d H:m:s");

        sb.append(df.format(date)).append("\t ").append(record.getLevel().getLocalizedName()).append("   \t: ")
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