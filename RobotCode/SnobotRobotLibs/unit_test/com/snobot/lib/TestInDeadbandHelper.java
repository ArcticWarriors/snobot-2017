package com.snobot.lib;

import org.junit.Assert;
import org.junit.Test;

public class TestInDeadbandHelper
{

    @Test
    public void testDeadbandHelper()
    {
        InDeadbandHelper helper = new InDeadbandHelper(10);

        for (int i = 0; i < 100; ++i)
        {
            Assert.assertFalse(helper.isFinished((i % 2) == 0));
            Assert.assertEquals((i % 2) == 0 ? 1 : 0, helper.inDeadband());
        }

        Assert.assertFalse(helper.isFinished(false));

        for (int i = 0; i < 9; ++i)
        {
            Assert.assertFalse(helper.isFinished(true));
            Assert.assertEquals(i + 1, helper.inDeadband());
        }
        Assert.assertTrue(helper.isFinished(true));
    }
}
