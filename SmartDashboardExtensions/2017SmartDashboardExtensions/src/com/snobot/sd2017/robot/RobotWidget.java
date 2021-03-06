package com.snobot.sd2017.robot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.snobot.sd.util.AutoUpdateWidget;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;

public class RobotWidget extends AutoUpdateWidget
{
    public static final String NAME = "2017 Robot Widget";

    private RobotDrawer mDrawer;

    public RobotWidget()
    {
        super(false, 200);
        setLayout(new BorderLayout());
        mDrawer = new RobotDrawer();
        add(mDrawer, BorderLayout.CENTER);
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    public void propertyChanged(Property arg0)
    {
    }

    @Override
    protected void poll() throws Exception
    {
        boolean spchincterOpen = Robot.getTable().getBoolean(com.snobot2017.SmartDashBoardNames.sSPCHINCTER_OPEN, false);;
        boolean isGearBossUp = Robot.getTable().getBoolean(com.snobot2017.SmartDashBoardNames.sGEAR_BOSS_SOLENOID, false);
        double spoolSpeed = Robot.getTable().getNumber(com.snobot2017.SmartDashBoardNames.sROBOT_ROPE_MOTOR_SPEED, 0);
        boolean inAction = Robot.getTable().getBoolean(com.snobot2017.SmartDashBoardNames.sIN_ACTION, false);
        String actorStateName = Robot.getTable().getString(com.snobot2017.SmartDashBoardNames.sSNOBOT_ACTION, "Not Found");
        String actorActionName = Robot.getTable().getString(com.snobot2017.SmartDashBoardNames.sSNOBOT_ACTION_NAME, "");

        if (mDrawer != null)
        {
            mDrawer.setFuelSpchingerOpen(spchincterOpen);
            mDrawer.setGearBossUp(isGearBossUp);
            mDrawer.setSpoolMotorSpeed(spoolSpeed);
            mDrawer.setInAction(inAction);
            mDrawer.setActorState(actorStateName);
            mDrawer.setActionName(actorActionName);
        }
        repaint();
    }

    @Override
    public void init()
    {
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent arg0)
            {
                mDrawer.updateSize();
            }
        });
    }

}
