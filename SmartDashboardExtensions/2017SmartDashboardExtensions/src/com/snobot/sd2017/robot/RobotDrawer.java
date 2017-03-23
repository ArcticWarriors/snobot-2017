package com.snobot.sd2017.robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.snobot.sd.util.Util;

public class RobotDrawer extends JPanel
{
    // Chassis dimensions (CHANGE AT SOME POINT) in inches
    private static final double sROBOT_WIDTH = 75;
    private static final double sROBOT_HEIGHT = 12;
    private static final double sSPOOL_RADIUS = 12;
    private static final double sGEAR_WIDTH = 10;
    private static final double sGEAR_HEIGHT = 25;
    private static final double sGEAR_BOSS_WIDTH = 10;
    private static final double sGEAR_BOSS_HEIGHT = 15;
    private static final double FUEL_LOWER_START = 1; // inch
    private static final double FUEL_LOWER_END = 7 + sROBOT_HEIGHT; // inch
    private static final double FUEL_FLAP_SIZE = 6;

    // Size to draw, in inches
    private static final double sDRAWING_WIDTH = sROBOT_WIDTH + 30;
    private static final double sDRAWING_HEIGHT = (sROBOT_HEIGHT + sGEAR_HEIGHT + sGEAR_BOSS_HEIGHT) + 30;

    // Drawing Locations in pixels
    private static final double sCHASSIS_X_START = 10;
    private static final double sCHASSIS_Y_START = 65;
    private static final double sSPOOL_X_START = 40;
    private static final double sSPOOL_Y_START = 53;
    private static final double sGEAR_X_START = 10;
    private static final double sGEAR_Y_START = 25;
    private static final double sGEAR_BOSS_X_START = 10;
    private static final double sGEAR_BOSS_Y_START = 50;

    // Component Colors
    private static final Color sROBOT_BASE_COLOR = Color.black;
    private static final Color sROBOT_GEARBOX_COLOR = Color.blue;
    private static final Color sROBOT_SPOOL_COLOR = Color.gray;
    private static final Color sROBOT_GEARFUNNEL_COLOR = Color.cyan;
    private static final Color sROBOT_NOACTION_COLOR = Color.white;
    private static final Color sROBOT_INACTION1_COLOR = Color.orange;
    private static final Color sROBOT_INACTION2_COLOR = new Color(0xD79B24);
    private static final Color sROBOT_STATE_TEXT_COLOR = Color.black;

    /**
     * The scaling factor used for drawing. For example, 1 would mean draw every
     * inch as one pixel, 5 would mean draw every inch as 5 pixels
     */
    private double mScaleFactor;

    // Robot State
    private double mSpoolSpeed;
    private boolean mGearBossIsUp;
    private boolean mSpchingerOpen;
    private boolean mInAction;
    private boolean mCycleFlash = false;
    private String mActorStateName = "";
    private String mActorActionName = "";
    private Font mFont = new Font("SansSerif", Font.BOLD, 14);

    public RobotDrawer()
    {
        updateSize();
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
        setSize(400, 300);
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent arg0)
            {
                updateSize();
            }
        });
    }

    private void drawRobotBase(Graphics2D g2d)
    {
        Rectangle2D robotBase = new Rectangle2D.Double(
                sCHASSIS_X_START * mScaleFactor, 
                sCHASSIS_Y_START * mScaleFactor, 
                sROBOT_WIDTH * mScaleFactor,
                sROBOT_HEIGHT * mScaleFactor);

        g2d.setColor(sROBOT_BASE_COLOR);
        g2d.fill(robotBase);
    }

    private void drawSpool(Graphics2D g2d)
    {
        Color color = sROBOT_SPOOL_COLOR;
        if (getSpoolMotorSpeed() != 0)
        {
            color = Util.getMotorColor(getSpoolMotorSpeed());
        }
        else
        {
            color = sROBOT_SPOOL_COLOR;
        }

        Ellipse2D spool = new Ellipse2D.Double(
                sSPOOL_X_START * mScaleFactor, 
                sSPOOL_Y_START * mScaleFactor, 
                sSPOOL_RADIUS * mScaleFactor,
                sSPOOL_RADIUS * mScaleFactor);

        g2d.setColor(color);
        g2d.fill(spool);
    }

    private void drawGearFunnel(Graphics2D g2d)
    {
        Rectangle2D gearBoss = new Rectangle2D.Double(
                sGEAR_X_START * mScaleFactor, 
                sGEAR_Y_START * mScaleFactor, 
                sGEAR_WIDTH * mScaleFactor,
                sGEAR_HEIGHT * mScaleFactor);

        g2d.setColor(sROBOT_GEARFUNNEL_COLOR);
        g2d.fill(gearBoss);

    }

    private void drawGearBoss(Graphics2D g2d)
    {
        double mGear_Boss_Y_Start = sGEAR_BOSS_Y_START;
        if (mGearBossIsUp)
        {
            // in pixels
            mGear_Boss_Y_Start = 50;
        }
        else
        {
            // in pixels
            mGear_Boss_Y_Start = 62;
        }
        Rectangle2D gearBoss = new Rectangle2D.Double(
                sGEAR_BOSS_X_START * mScaleFactor, 
                mGear_Boss_Y_Start * mScaleFactor,
                sGEAR_BOSS_WIDTH * mScaleFactor, 
                sGEAR_BOSS_HEIGHT * mScaleFactor);

        g2d.setColor(sROBOT_GEARBOX_COLOR);
        g2d.fill(gearBoss);
    }

    protected void drawFuel(Graphics2D g2d)
    {
        g2d.setColor(Color.black);

        int fuelHolderStartX = (int) ((sGEAR_X_START + sGEAR_BOSS_WIDTH) * mScaleFactor);
        int fuelHolderEndX = (int) ((sCHASSIS_X_START + sROBOT_WIDTH) * mScaleFactor);
        int fuelHolderStartY = (int) ((sGEAR_Y_START + FUEL_LOWER_START) * mScaleFactor);
        int fuelHolderEndY = (int) ((sGEAR_Y_START + FUEL_LOWER_END) * mScaleFactor);
        int fuelBottomeY = (int) ((sROBOT_HEIGHT + sGEAR_HEIGHT + sGEAR_BOSS_HEIGHT - FUEL_LOWER_END) * mScaleFactor) + fuelHolderEndY;

        int flapClosedEndY = fuelHolderEndY;
        int flapClosedStartY = fuelHolderEndY - (int) ((FUEL_FLAP_SIZE) * mScaleFactor);
        int flapOpenEndX = fuelHolderEndX + (int) ((FUEL_FLAP_SIZE) * mScaleFactor);

        g2d.drawLine(fuelHolderStartX, fuelHolderStartY, fuelHolderEndX, fuelHolderEndY);
        g2d.drawLine(
                fuelHolderEndX, 
                fuelHolderEndY, 
                fuelHolderEndX,
                fuelBottomeY);

        if (mSpchingerOpen)
        {
            g2d.drawLine(fuelHolderEndX, flapClosedEndY, flapOpenEndX, flapClosedEndY);
        }
        else
        {
            g2d.drawLine(fuelHolderEndX, flapClosedStartY, fuelHolderEndX, flapClosedEndY);
        }
    }

    protected void drawActorState(Graphics2D g2d)
    {
        if (mInAction)
        {
            if (mCycleFlash)
            {
                g2d.setColor(sROBOT_INACTION1_COLOR);
            }
            else
            {
                g2d.setColor(sROBOT_INACTION2_COLOR);
            }
            mCycleFlash = !mCycleFlash;
        }
        else
        {
            g2d.setColor(sROBOT_NOACTION_COLOR);
        }
        g2d.fillRect(0, 0, (int) getWidth(), (int) getHeight());

        // Write State on Image
        String nameString = "";
        if (!mActorActionName.isEmpty())
        {
            nameString = mActorActionName + ": ";
        }
        nameString = nameString + mActorStateName;

        g2d.setColor(sROBOT_STATE_TEXT_COLOR);
        g2d.setFont(mFont);
        g2d.drawString(nameString, 10, 20);
    }

    public void updateSize()
    {
        double horizontalScaleFactor = (getWidth() / sDRAWING_WIDTH);
        double verticalScaleFactor = (getHeight() / sDRAWING_HEIGHT);

        double minScaleFactor = Math.min(horizontalScaleFactor, verticalScaleFactor);

        mScaleFactor = minScaleFactor;

        repaint();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        g.clearRect(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());

        // Draw Robot Parts
        drawActorState(g2d);
        drawRobotBase(g2d);
        drawSpool(g2d);
        drawGearFunnel(g2d);
        drawGearBoss(g2d);
        drawFuel(g2d);
    }

    public double getSpoolMotorSpeed()
    {
        return mSpoolSpeed;
    }

    public void setSpoolMotorSpeed(double aRopeMotorSpeed)
    {
        mSpoolSpeed = aRopeMotorSpeed;
    }

    public boolean isGearBossUp()
    {
        return mGearBossIsUp;
    }

    public void setGearBossUp(boolean aGearBossIsUp)
    {
        mGearBossIsUp = aGearBossIsUp;
    }

    public void setInAction(boolean inAction)
    {
        mInAction = inAction;
    }

    public void setActorState(String actorStateName)
    {
        mActorStateName = actorStateName;
    }

    public void setActionName(String actorActionName)
    {
        mActorActionName = actorActionName;

    }

    public boolean isFuelFlapIsUp()
    {
        return mSpchingerOpen;
    }

    public void setFuelSpchingerOpen(boolean aSpchingerOpen)
    {
        mSpchingerOpen = aSpchingerOpen;
    }

}
