package com.team254.lib.trajectory.gen;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.Trajectory;
import com.team254.lib.trajectory.Trajectory.Segment;

/**
 * Serializes a Path to a simple space and CR separated text file.
 * 
 * @author Jared341
 */
public class TextFileSerializer
{

  /**
   * Format:
   *   PathName
   *   NumSegments
   *   LeftSegment1
   *   ...
   *   LeftSegmentN
   *   RightSegment1
   *   ...
   *   RightSegmentN
   * 
   * Each segment is in the format:
   *   pos vel acc jerk heading dt x y
   * 
   * @param path The path to serialize.
   * @return A string representation.
   */
  public String serialize(Path path) {
      Trajectory left = path.getLeftWheelTrajectory();
      Trajectory right = path.getRightWheelTrajectory();;

      String content = "";
      content += "LeftPos,LeftVelocity,LeftAcceleration,LeftJerk,LeftHeading,LeftDt,LeftX,LeftY,";
      content += "RightPos,RightVelocity,RightAcceleration,RightJerk,RightHeading,RightDt,RightX,RightY,";
      content += "\n";
      for (int i = 0; i < left.getNumSegments(); ++i)
      {
          Segment left_segment = left.getSegment(i);
          Segment right_segment = right.getSegment(i);

          double leftHeadingDeg = Math.toDegrees(left_segment.heading);
          double rightHeadingDeg = Math.toDegrees(right_segment.heading);

          content += String.format("%.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f\n",
                  left_segment.pos, left_segment.vel, left_segment.acc, left_segment.jerk, leftHeadingDeg, left_segment.dt, left_segment.x, left_segment.y, 
                  right_segment.pos, right_segment.vel, right_segment.acc, right_segment.jerk, rightHeadingDeg, right_segment.dt, right_segment.x, right_segment.y);
      }

      return content;
  }
  
}