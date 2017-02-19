package com.snobot.lib;

import com.snobot.lib.modules.IControllableModule;
import com.snobot.lib.modules.ILoggableModule;
import com.snobot.lib.modules.ISmartDashboardUpdaterModule;
import com.snobot.lib.modules.IUpdateableModule;

/**
 * Main interface for the Robot subsystems.
 * 
 * @author Ayush/Ammar
 *
 */
public interface ISubsystem extends IControllableModule, IUpdateableModule, ILoggableModule, ISmartDashboardUpdaterModule
{
}
