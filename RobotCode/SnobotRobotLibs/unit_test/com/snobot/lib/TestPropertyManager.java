package com.snobot.lib;

import org.junit.Assert;
import org.junit.Test;

import com.snobot.lib.PropertyManager.BooleanProperty;
import com.snobot.lib.PropertyManager.DoubleProperty;
import com.snobot.lib.PropertyManager.IntegerProperty;
import com.snobot.lib.PropertyManager.StringProperty;
import com.snobot2017.test.utilities.BaseTest;

import edu.wpi.first.wpilibj.Preferences;


public class TestPropertyManager extends BaseTest
{
    private static final double DOUBLE_EPSILON = .00001;

    @Test
    public void testProperties()
    {

        BooleanProperty boolProp = new BooleanProperty("Boolean", false);
        Assert.assertEquals("Boolean", boolProp.getKey());
        Assert.assertEquals(boolProp.getValue(), false);
        Preferences.getInstance().putBoolean("Boolean", true);
        Assert.assertEquals(boolProp.getValue(), true);

        DoubleProperty doubleProp = new DoubleProperty("Double", .1);
        Assert.assertEquals("Double", doubleProp.getKey());
        Assert.assertEquals(doubleProp.getValue(), .1, DOUBLE_EPSILON);
        Preferences.getInstance().putDouble("Double", .5);
        Assert.assertEquals(doubleProp.getValue(), .5, DOUBLE_EPSILON);

        IntegerProperty intProp = new IntegerProperty("Integer", 174);
        Assert.assertEquals("Integer", intProp.getKey());
        Assert.assertEquals(intProp.getValue().intValue(), 174);
        Preferences.getInstance().putInt("Integer", 191);
        Assert.assertEquals(intProp.getValue().intValue(), 191);

        StringProperty stringProp = new StringProperty("String", "Hello");
        Assert.assertEquals("String", stringProp.getKey());
        Assert.assertEquals(stringProp.getValue(), "Hello");
        Preferences.getInstance().putString("String", "World");
        Assert.assertEquals(stringProp.getValue(), "World");
    }

    @Test
    public void testDefaultConstruction()
    {
        BooleanProperty boolProp = new BooleanProperty("Boolean2");
        StringProperty stringProp = new StringProperty("String2");

        Assert.assertEquals(boolProp.getValue(), false);
        Assert.assertEquals(stringProp.getValue(), "");

        // Should print out
        PropertyManager.saveIfUpdated();

        // Should NOT print out
        PropertyManager.saveIfUpdated();
    }

}
