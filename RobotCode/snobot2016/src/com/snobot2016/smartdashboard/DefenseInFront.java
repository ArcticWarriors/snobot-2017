package com.snobot2016.smartdashboard;

import com.snobot2016.Properties2016;
import com.snobot2016.SmartDashBoardNames;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * @author Andrew/Alec
 */
public class DefenseInFront
{
    /**
     * This is the sendable chooser that the class makes.
     */
    private SendableChooser mDefenseInFront;

    /**
     * This enum lists all of the defenses and other options for things to do
     * before crossing the Outer Works in autonomous. All of these are sent as a
     * sendableChooser to the smart dashboard; the selected is used in
     * AutonFactory.
     */
    public enum Defenses
    {
        LOW_BAR, PORTCULLIS, CHIVAL_DE_FRISE, MOAT, RAMPARTS, DRAWBRIDGE, SALLY_PORT, ROCK_WALL, ROUGH_TERRAIN, DO_NOTHING;
    }

    /**
     * In the constructor, we new up the chooser and add all of the enum
     * options.
     */
    public DefenseInFront()
    {
        mDefenseInFront = new SendableChooser();
        mDefenseInFront.addDefault("Low Bar", Defenses.LOW_BAR);
        mDefenseInFront.addObject("Portcullis", Defenses.PORTCULLIS);
        mDefenseInFront.addObject("Chival de Frise", Defenses.CHIVAL_DE_FRISE);
        mDefenseInFront.addObject("Moat", Defenses.MOAT);
        mDefenseInFront.addObject("Ramparts", Defenses.RAMPARTS);
        mDefenseInFront.addObject("Drawbridge", Defenses.DRAWBRIDGE);
        mDefenseInFront.addObject("Sally Port", Defenses.SALLY_PORT);
        mDefenseInFront.addObject("Rock Wall", Defenses.ROCK_WALL);
        mDefenseInFront.addObject("Rough Terrain", Defenses.ROUGH_TERRAIN);
        mDefenseInFront.addObject("Do Nothing", Defenses.DO_NOTHING);
    }

    /**
     * This method can be called to put the defenses sendable chooser onto the
     * dashboard.
     */
    public void putOnDash()
    {
        SmartDashboard.putData(SmartDashBoardNames.sDEFENSE_SENDER_NAME, mDefenseInFront);
    }

    /**
     * This method is used to get the selected output (defaulting to Low Bar).
     */
    public String getDefensePath()
    {
        return Properties2016.sAUTON_DEFENSE_DIRECTORY.getValue() + "/" + mDefenseInFront.getSelected().toString() + ".txt";
    }

    public void addChangeListener(ITableListener aListener)
    {
        mDefenseInFront.getTable().addTableListener(aListener);
    }
}
