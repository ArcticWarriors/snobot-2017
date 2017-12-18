package com.snobot.sd2017.robot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot.sd.util.SmartDashboardUtil;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;

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
        boolean spchincterOpen = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sSPCHINCTER_OPEN).getBoolean(false);;
        boolean isGearBossUp = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sGEAR_BOSS_SOLENOID).getBoolean(false);
        double spoolSpeed = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sROBOT_ROPE_MOTOR_SPEED).getDouble(0);
        boolean inAction = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sIN_ACTION).getBoolean(false);
        String actorStateName = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sSNOBOT_ACTION).getString("Not Found");
        String actorActionName = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sSNOBOT_ACTION_NAME).getString("");

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
