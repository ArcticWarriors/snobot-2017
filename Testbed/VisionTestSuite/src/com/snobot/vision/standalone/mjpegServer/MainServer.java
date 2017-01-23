package com.snobot.vision.standalone.mjpegServer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.yaml.snakeyaml.Yaml;

public class MainServer
{
    private static final String IMAGE_CONFIG = "config/test_images.yml";

    private interface ImageWaiter
    {
        public void waitForNextImage();
    }

    public static class ImageWaiterThreadSleep implements ImageWaiter
    {
        private int delay;

        public ImageWaiterThreadSleep(int delay)
        {
            this.delay = delay;
        }

        public void waitForNextImage()
        {
            try
            {
                Thread.sleep(delay);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static class ImageWaiterConsoleInput implements ImageWaiter
    {
        public ImageWaiterConsoleInput()
        {
        }

        public void waitForNextImage()
        {
            try
            {
                System.out.println("Press enter for next image...");
                 int input = -1;
                 do
                 {
                    input = System.in.read();
                } while (input != '\n');
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public MainServer(ImageWaiter imageWaiter) throws IOException
    {
        MjpgServer server = MjpgServer.getInstance();

        Yaml yaml = new Yaml();
        Map<String, Object> config = (Map<String, Object>) yaml.load(new FileInputStream(IMAGE_CONFIG));
        List<String> files = (List<String>) config.get("images");

        while (true)
        {
            for (String file : files)
            {
                BufferedImage image = ImageIO.read(new File(file));

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();

                imageWaiter.waitForNextImage();
                server.update(imageInByte);
            }
        }
    }

    public static void main(String[] args) throws IOException
    {
        // new MainServer(new ImageWaiterThreadSleep(1000));
        new MainServer(new ImageWaiterConsoleInput());
    }
}
