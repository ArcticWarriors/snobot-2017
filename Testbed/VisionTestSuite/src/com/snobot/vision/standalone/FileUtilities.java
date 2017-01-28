package com.snobot.vision.standalone;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtilities
{
    public static char getByteAsHex(byte b)
    {
        return String.format("%02X ", b).charAt(0);
    }

    public static char getByteAsAscii(byte b)
    {
        char output = '.';

        if (b > 31)
        {
            output = (char) b;
        }

        return output;
    }

    public static void dumpBytesToFile(byte[] bytes, String outputFile)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < bytes.length; ++i)
            {
                bw.write(getByteAsAscii(bytes[i]));
                if ((i + 1) % 16 == 0)
                {
                    bw.write("\n");
                }
            }

            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
