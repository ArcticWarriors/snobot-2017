package com.snobot2016.autonomous.trajectory;

import java.text.DecimalFormat;

import com.team254.lib.trajectory.Trajectory;
import com.team254.lib.trajectory.Trajectory.Segment;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * PID + Feedforward controller for following a Trajectory.
 *
 * @author Jared341
 */
public class TrajectoryFollower
{

    private double kp_;
    // private double ki_; // Not currently used, but might be in the future.
    private double kd_;
    private double kv_;
    private double ka_;
    private double last_error_;
    private double current_heading = 0;
    private int current_segment;
    private Trajectory profile_;
    public String name;

    public TrajectoryFollower(String name)
    {
        this.name = name;
    }

    public void configure(double kp, double ki, double kd, double kv, double ka)
    {
        kp_ = kp;
        // ki_ = ki;
        kd_ = kd;
        kv_ = kv;
        ka_ = ka;
    }

    public void reset()
    {
        last_error_ = 0.0;
        current_segment = 0;
    }

    public void setTrajectory(Trajectory profile)
    {
        profile_ = profile;
    }

    public double calculate(double distance_so_far)
    {

        if (current_segment < profile_.getNumSegments())
        {
            Segment segment = profile_.getSegment(current_segment);
            double error = segment.pos - distance_so_far;

            double p_term = error * kp_;
            double d_term = kd_ * ((error - last_error_) / segment.dt - segment.vel);
            double v_term = kv_ * segment.vel;
            double a_term = ka_ * segment.acc;
            double output = p_term + d_term + v_term + a_term;

            DecimalFormat df = new DecimalFormat("#.000");

            if (name.equals("right"))
            {
                System.out.println(name + " - " + 
                        "Current: " + df.format(distance_so_far) + ", " + 
                        "Desired: " + df.format(segment.pos) + ", " + 
                        "p: " + df.format(p_term) + ", " + 
                        "d: " + df.format(d_term) + ", " + 
                        "v: " + df.format(v_term) + ", " + 
                        "a: " + df.format(a_term) + ", " +
                        "output: " + output);
            }

            last_error_ = error;
            current_heading = segment.heading;
            current_segment++;
            SmartDashboard.putNumber(name + "FollowerSensor", distance_so_far);
            SmartDashboard.putNumber(name + "FollowerGoal", segment.pos);
            SmartDashboard.putNumber(name + "FollowerError", error);
            return output;
        }
        else
        {
            return 0;
        }
        // return 0;
    }

    public double getHeading()
    {
        return current_heading;
    }

    public boolean isFinishedTrajectory()
    {
        return current_segment >= profile_.getNumSegments();
    }

    public int getCurrentSegment()
    {
        return current_segment;
    }

    public int getNumSegments()
    {
        return profile_.getNumSegments();
    }
}
