package com.snobot.vision_app.app2017.java_algorithm.common;

import java.util.Comparator;

public class TargetComparators
{
    public static class AspectRatioComparator implements Comparator<TapeLocation>
    {
        @Override
        public int compare(TapeLocation o1, TapeLocation o2)
        {
            double aspectRatioDifference_1 = Math.abs(0.4 - o1.getAspectRatio());
            double aspectRatioDifference_2 = Math.abs(0.4 - o2.getAspectRatio());

            if (aspectRatioDifference_1 > aspectRatioDifference_2)
                return 1;
            else
                return -1;
        }
    }

    public static class AngleComparator implements Comparator<TapeLocation>
    {
        @Override
        public int compare(TapeLocation o1, TapeLocation o2)
        {
            if (o1.getAngle() > o2.getAngle())
                return 1;
            else
                return -1;
        }
    }
}
