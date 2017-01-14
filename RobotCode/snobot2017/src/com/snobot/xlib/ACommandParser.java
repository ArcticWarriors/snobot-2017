package com.snobot.xlib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class ACommandParser
{
    protected final String mDelimiter;
    protected final String mCommentStart;

    protected String mErrorText;
    protected boolean mSuccess;

    public ACommandParser(String aDelimiter, String aCommentStart)
    {
        mErrorText = "";
        mSuccess = false;
        mDelimiter = aDelimiter;
        mCommentStart = aCommentStart;
    }

    protected void addError(String aError)
    {
        // Put the '#' so we can pretend like the error text is a comment
        mErrorText += mCommentStart + aError + "\n";
        mSuccess = false;
    }

    protected void initReading()
    {
        mSuccess = true;
        mErrorText = "";
    }

    /**
     * Specialization wrapper for a command group. Simply will print out that
     * the command group has finished
     * 
     * @param aName
     *            Name of the command group
     * @return The newly created command group
     */
    protected CommandGroup createNewCommandGroup(String aName)
    {
        return new CommandGroup(aName)
        {
            @Override
            protected void end()
            {
                super.end();
                System.out.println("Command group '" + aName + "' finished!");
            }
        };
    }

    /**
     * Interprets a line as a Command and adds it to mCommands
     * 
     * @param aLine
     *            Line of text
     * @param b
     */
    protected void parseLine(CommandGroup aGroup, String aLine, boolean aAddParallel)
    {
        aLine = aLine.trim();
        if (aLine.isEmpty() || aLine.startsWith(mCommentStart))
        {
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer(aLine, mDelimiter);

        List<String> args = new ArrayList<>();

        while (tokenizer.hasMoreElements())
        {
            args.add(tokenizer.nextToken());
        }

        Command newCommand = parseCommand(args);

        if (newCommand == null)
        {
            mSuccess = false;
        }
        else
        {
            if (aAddParallel)
            {
                aGroup.addParallel(newCommand);
            }
            else
            {
                aGroup.addSequential(newCommand);
            }
        }
    }

    /**
     * Parses a parallel command (commands separated by '|'
     * 
     * @param args
     *            The list of arguments
     * @return The command group for the parallel command
     */
    protected CommandGroup parseParallelCommand(List<String> args)
    {
        String parallel_line = "";
        for (int i = 1; i < args.size(); ++i)
        {
            parallel_line += args.get(i) + " ";
        }

        String[] split_commands = parallel_line.split("\\|");
        CommandGroup parallelCommands = new CommandGroup();

        for (String this_line : split_commands)
        {
            parseLine(parallelCommands, this_line, true);
        }

        return parallelCommands;
    }

    /**
     * Reads the given file into autonomous commands
     * 
     * @param aFilePath
     *            The path to the file to read
     * @return The constructed command group to run
     */
    public CommandGroup readFile(String aFilePath)
    {
        initReading();

        CommandGroup output = createNewCommandGroup(aFilePath);

        String fileContents = "";

        File file = new File(aFilePath);

        if (file.exists())
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(aFilePath));

                String line;
                while ((line = br.readLine()) != null)
                {
                    this.parseLine(output, line, false);
                    fileContents += line + "\n";
                }

                br.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            addError("File " + aFilePath + " not found!");
        }

        publishParsingResults(fileContents);

        return output;
    }

    protected abstract Command parseCommand(List<String> args);

    protected abstract void publishParsingResults(String fileContents);
}
