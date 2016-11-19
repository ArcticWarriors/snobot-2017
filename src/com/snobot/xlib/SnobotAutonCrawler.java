package com.snobot.xlib;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public final class SnobotAutonCrawler extends SimpleFileVisitor<Path>
{
    private List<Path> mPaths;
    private String mIgnoreString;

    public SnobotAutonCrawler(String aIgnoreString)
    {
        mPaths = new ArrayList<Path>();
        mIgnoreString = aIgnoreString;
    }

    public List<Path> getPaths()
    {
        return mPaths;
    }

    @Override
    public FileVisitResult visitFile(Path aFile, BasicFileAttributes aAttrs) throws IOException
    {
        System.out.println("  Keeping file " + aFile);
        mPaths.add(aFile);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path aDir, BasicFileAttributes aAttrs) throws IOException
    {
        Path dirName = aDir.getFileName();
        if (dirName.startsWith(mIgnoreString))
        {
            System.out.println(" Skipping directory: " + dirName);
            return FileVisitResult.SKIP_SUBTREE;
        }
        else
        {
            System.out.println(" Processing directory: " + dirName);
            return FileVisitResult.CONTINUE;
        }
    }

}