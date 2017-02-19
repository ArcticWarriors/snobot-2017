package FuelPooper;

import com.snobot.lib.ISubsystem;

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
