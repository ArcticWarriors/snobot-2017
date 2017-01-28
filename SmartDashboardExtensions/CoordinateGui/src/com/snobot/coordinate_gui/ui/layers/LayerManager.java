package com.snobot.coordinate_gui.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.snobot.coordinate_gui.model.PixelConverter;

public class LayerManager extends JPanel implements ILayerManager
{
    protected final List<IFieldClickListener> mFieldClickListeners;
    protected final List<ILayer> mLayers;
    protected PixelConverter mConverter;
    protected Object mLock;

    protected MouseAdapter mMouseListener = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            double x_feet = mConverter.convertXPixelsToFeet(e.getX());
            double y_feet = mConverter.convertYPixelsToFeet(e.getY());

            for (IFieldClickListener listener : mFieldClickListeners)
            {
                listener.onClicked(x_feet, y_feet);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            double x_feet = mConverter.convertXPixelsToFeet(e.getX());
            double y_feet = mConverter.convertYPixelsToFeet(e.getY());

            for (IFieldClickListener listener : mFieldClickListeners)
            {
                listener.onDrag(x_feet, y_feet);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            double x_feet = mConverter.convertXPixelsToFeet(e.getX());
            double y_feet = mConverter.convertYPixelsToFeet(e.getY());

            for (IFieldClickListener listener : mFieldClickListeners)
            {
                listener.onHover(x_feet, y_feet);
            }
        }
    };

    protected ComponentAdapter mResizeListener = new ComponentAdapter()
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            mConverter.updateScaleFactor(getWidth(), getHeight(), 27, 54);
            repaint();
        }
    };

    public LayerManager(PixelConverter aConverter, Object aLock)
    {
        mFieldClickListeners = new ArrayList<>();
        mLayers = new ArrayList<>();
        mConverter = aConverter;
        mLock = aLock;
        addComponentListener(mResizeListener);
        addMouseListener(mMouseListener);
        addMouseMotionListener(mMouseListener);
    }

    public void addFieldClickListener(IFieldClickListener aListener)
    {
        mFieldClickListeners.add(aListener);
    }

    public void removeFieldClickListener(IFieldClickListener aListener)
    {
        mFieldClickListeners.remove(aListener);
    }

    public void addLayer(ILayer aLayer)
    {
        mLayers.add(aLayer);
    }

    @Override
    public Dimension getPreferredSize()
    {
        Dimension output = null;

        for (ILayer layer : mLayers)
        {
            Dimension d = layer.getPreferredSize();
            if (d != null)
            {
                output = d;
            }
        }

        return output;
    }

    @Override
    public void paint(Graphics g)
    {
        synchronized (mLock)
        {
            Graphics2D graphics = (Graphics2D) g;

            for (ILayer layer : mLayers)
            {
                layer.render(graphics);
            }
        }
    }

    public void render()
    {
        repaint();
    }

}
