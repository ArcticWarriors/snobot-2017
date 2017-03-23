package com.snobot.lib.modules;

public interface ILoggableModule
{
    /**
     * Perform initialization.
     */
    void initializeLogHeaders();

    /**
     * Updates the logger.
     */
    void updateLog();
}
