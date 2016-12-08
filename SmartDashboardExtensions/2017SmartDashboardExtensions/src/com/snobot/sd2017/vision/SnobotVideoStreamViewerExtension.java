package com.snobot.sd2017.vision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.snobot.sd2017.vision.MjpegReceiver.ImageReceiver;

import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;

/**
 *
 * @author Greg Granito
 */
public class SnobotVideoStreamViewerExtension extends StaticWidget {

	public static final String NAME = "2017 Vision Viewer";

    private double rotateAngleRad = 0;
	private BufferedImage imageToDraw;
	private MjpegReceiver bgThread = new MjpegReceiver();

    private ImageReceiver mImageReceiver = new ImageReceiver()
    {

        @Override
        public void onImage(BufferedImage image)
        {
            imageToDraw = image;
            repaint();
        }

    };

    
    @Override
    public void init() {
        setPreferredSize(new Dimension(100, 100));
        bgThread.addImageReceiver(mImageReceiver);
        bgThread.start("http://127.0.0.1:1180");
        revalidate();
        repaint();
    }

    @Override
    public void propertyChanged(Property property) {

    }

    @Override
    public void disconnect() {
        bgThread.stop();
        super.disconnect();
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage drawnImage = imageToDraw; 
        
        if (drawnImage != null) {
        	// cast the Graphics context into a Graphics2D
            Graphics2D g2d = (Graphics2D)g;
            
            // get the existing Graphics transform and copy it so that we can perform scaling and rotation
            AffineTransform origXform = g2d.getTransform();
            AffineTransform newXform = (AffineTransform)(origXform.clone());
            
            // find the center of the original image
            int origImageWidth = drawnImage.getWidth();
            int origImageHeight = drawnImage.getHeight();
            int imageCenterX = origImageWidth/2;
            int imageCenterY = origImageHeight/2;
            
            // perform the desired scaling
            double panelWidth = getBounds().width;
            double panelHeight = getBounds().height;
            double panelCenterX = panelWidth/2.0;
            double panelCenterY = panelHeight/2.0;
            double rotatedImageWidth = origImageWidth * Math.abs(Math.cos(rotateAngleRad)) + origImageHeight * Math.abs(Math.sin(rotateAngleRad));
            double rotatedImageHeight = origImageWidth * Math.abs(Math.sin(rotateAngleRad)) + origImageHeight * Math.abs(Math.cos(rotateAngleRad));         
            		
            // compute scaling needed
            double scale = Math.min(panelWidth / rotatedImageWidth, panelHeight / rotatedImageHeight);
                      
            // set the transform before drawing the image
            // 1 - translate the origin to the center of the panel
            // 2 - perform the desired rotation (rotation will be about origin)
            // 3 - perform the desired scaling (will scale centered about origin)
            newXform.translate(panelCenterX,  panelCenterY);
            newXform.rotate(rotateAngleRad);
            newXform.scale(scale, scale);
            g2d.setTransform(newXform);

            // draw image so that the center of the image is at the "origin"; the transform will take care of the rotation and scaling
            g2d.drawImage(drawnImage, -imageCenterX, -imageCenterY, null);
            
            // restore the original transform
            g2d.setTransform(origXform);
            
            // g.setColor(Color.PINK);
            // g.drawString("FPS: "+lastFPS, 10, 10);
        } else {
            g.setColor(Color.PINK);
            g.fillRect(0, 0, getBounds().width, getBounds().height);
            g.setColor(Color.BLACK);
            g.drawString("NO CONNECTION", 10, 10);
        }
    }
}