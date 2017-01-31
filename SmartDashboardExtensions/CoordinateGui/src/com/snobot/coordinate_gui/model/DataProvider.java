package com.snobot.coordinate_gui.model;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DataProvider<DataType>
{
    public static final int sABSOLUTE_MAX_POINT_MEMORY = 1500; //50 updates/sec * 30 seconds

    protected Deque<DataType> mCoordinates;
    protected int mMaxPoints;


    public DataProvider()
    {
        this(sABSOLUTE_MAX_POINT_MEMORY);
    }

    public DataProvider(int aMaxPoints)
    {
        if (aMaxPoints > sABSOLUTE_MAX_POINT_MEMORY)
        {
            throw new IndexOutOfBoundsException("Max memory (" + aMaxPoints + ") must be last than the absolute max (" + sABSOLUTE_MAX_POINT_MEMORY
                    + ")");
        }
        mCoordinates = new LinkedList<>();
        mMaxPoints = aMaxPoints;
    }

    public void addData(DataType aCoordinate)
    {
        mCoordinates.add(aCoordinate);
        trim();
    }

    protected void trim()
    {
        while (mCoordinates.size() > mMaxPoints)
        {
            mCoordinates.remove();
        }
    }

    public DataType getMostRecentData()
    {
        return mCoordinates.peekLast();
    }

    public Iterator<DataType> getReverseIterator()
    {
        return mCoordinates.descendingIterator();
    }

    public Deque<DataType> getAllData()
    {
        return mCoordinates;
    }

    public void clear()
    {
        mCoordinates.clear();
    }
}
