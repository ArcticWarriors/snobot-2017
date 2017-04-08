package com.snobot.lib.autonomous;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public final class SnobotAutonCrawler extends SimpleFileVisitor<Path>
{
    private List<Path> mPaths;
    private String mIgnoreString;
    private boolean mVerbose;

    public SnobotAutonCrawler(String aIgnoreString)
    {
        this(aIgnoreString, true);
    }

    public SnobotAutonCrawler(String aIgnoreString, boolean aVerbose)
    {
        mPaths = new ArrayList<Path>();
        mIgnoreString = aIgnoreString;
        mVerbose = aVerbose;
    }

    public List<Path> getPaths()
    {
        return mPaths;
    }

    @Override
    public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) throws IOException
    {
        if (mVerbose)
        {
            System.out.println("  Keeping file " + aFile);
        }
        mPaths.add(aFile);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) throws IOException
    {
        Path dirName = aDir.getFileName();
        if (dirName.startsWith(mIgnoreString))
        {
            if (mVerbose)
            {
                System.out.println(" Skipping directory: " + dirName);
            }
            return FileVisitResult.SKIP_SUBTREE;
        }
        else
        {
            if (mVerbose)
            {
                System.out.println(" Processing directory: " + dirName);
            }
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * This is just part of the auton crawler wee lifted from last year. It
     * makes a chooser out of all the files in a specified folder.
     */
    public void loadAutonFiles(String aDir)
    {
        File autonDr = new File(aDir);

        if (autonDr.exists())
        {
            if (mVerbose)
            {
                System.out.println("Reading auton files from directory " + autonDr.getAbsolutePath());
                System.out.println(" Using filter : \"" + mIgnoreString + "\"");
            }

            try
            {
                Files.walkFileTree(Paths.get(autonDr.toURI()), this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("Auton directory " + aDir + " does not exist!");
        }
    }

    public SendableChooser<File> createSendableChooser(String aDefaultName)
    {
        SendableChooser<File> output = new SendableChooser<>();

        boolean isFirst = true;
        for (Path p : mPaths)
        {
            if ((isFirst && aDefaultName == null) || p.getFileName().toString().equals(aDefaultName))
            {
                output.addDefault(p.getFileName().toString(), p.toFile());
                isFirst = false;
            }
            else
            {
                output.addObject(p.getFileName().toString(), p.toFile());
            }
        }

        return output;
    }

}