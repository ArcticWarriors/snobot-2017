package com.snobot.coordinate_gui.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface ILayer
{

    public void render(Graphics2D aGraphics);

    public Dimension getPreferredSize();
}
