package com.snobot.lib.ui;

import org.junit.Assert;
import org.junit.Test;

public class TestToggleButton
{

    @Test
    public void testToggleButton()
    {
        ToggleButton btn = new ToggleButton();

        Assert.assertFalse(btn.getState());
        Assert.assertFalse(btn.update(false));
        Assert.assertTrue(btn.update(true));
        Assert.assertTrue(btn.getState());

        // Continue to issue true
        Assert.assertTrue(btn.update(true));
        Assert.assertTrue(btn.getState());

        // Reset it back to false (released). It should still be toggled high
        Assert.assertTrue(btn.update(false));
        Assert.assertTrue(btn.getState());

        // Press it again, should now toggle low
        Assert.assertFalse(btn.update(true));
        Assert.assertFalse(btn.getState());

        // Continue to press it for multiple loops, should remain false
        Assert.assertFalse(btn.update(true));
        Assert.assertFalse(btn.getState());
    }
}
