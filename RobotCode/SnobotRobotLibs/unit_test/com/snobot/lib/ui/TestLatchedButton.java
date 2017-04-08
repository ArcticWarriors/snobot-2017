package com.snobot.lib.ui;

import org.junit.Assert;
import org.junit.Test;

public class TestLatchedButton
{

    @Test
    public void testLatchedButton()
    {
        LatchedButton btn = new LatchedButton();

        Assert.assertFalse(btn.getState());
        Assert.assertFalse(btn.update(false));
        Assert.assertTrue(btn.update(true));
        Assert.assertTrue(btn.getState());

        // Continue to issue true
        Assert.assertFalse(btn.update(true));
        Assert.assertFalse(btn.getState());

        // Reset it back to false (released)
        Assert.assertFalse(btn.update(false));
        Assert.assertFalse(btn.getState());
    }
}
