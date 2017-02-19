package com.snobot2017.fuel_pooper;

import com.snobot.lib.modules.ISubsystem;

public interface IFuelPooper extends ISubsystem
{
    /**
     * Opens the sphincter to eject fuel into boiler
     */
    void openSphincter();

    /**
     * Closes the sphincter to keep fuel in
     */
    void closeSphincter();
}
