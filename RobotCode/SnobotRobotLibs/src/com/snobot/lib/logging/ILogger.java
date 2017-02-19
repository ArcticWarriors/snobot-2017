package com.snobot.lib.logging;

import java.text.SimpleDateFormat;

public interface ILogger
{

    /**
     * Initializes the internal components of the loggers
     */
    public void initializeLogger();

    /**
     * Adds a new header to represent logged data
     * 
     * @param aHeader
     */
    void addHeader(String aHeader);

    /**
     * Stops accepting new headers
     */
    void endHeader();

    /**
     * Forces a flush of the file writer
     */
    void flush();

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    void updateLogger(String aEntry);

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    void updateLogger(int aEntry);

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    void updateLogger(double aEntry);

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    void updateLogger(boolean aEntry);

    /**
     * Begins accepting new log entries
     */
    void startRow();

    /**
     * Stops accepting log entries
     */
    void endRow();

    void startLogging(SimpleDateFormat aLogFormat, String aLogPath);

}