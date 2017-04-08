package com.snobot.lib.autonomous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.snobot2017.test.utilities.BaseTest;

import edu.wpi.first.wpilibj.command.Command;

public class TestCommandParser extends BaseTest
{
    private class TestCommand extends Command
    {

        @Override
        protected boolean isFinished()
        {
            return true;
        }

    }

    private class MockCommandParser extends ACommandParser
    {

        public MockCommandParser()
        {
            super("!", "~");
        }

        @Override
        protected void publishFileName(String aFileName)
        {

        }

        @Override
        protected void publishParsingResults(String aCommandString)
        {

        }

        @Override
        protected Command parseCommand(List<String> args)
        {
            String cmdName = args.get(0);
            if (cmdName.equals("TestCommand"))
            {
                return new TestCommand();
            }
            return null;
        }

    }

    @Test
    public void testParseEmptyCommand()
    {
        MockCommandParser parser = new MockCommandParser();
        Command cmd = parser.readFile("test_inputs/auton_test/empty.auto");
        Assert.assertNotNull(cmd);
        Assert.assertTrue(parser.wasParsingSuccessful());
    }

    @Test
    public void testParseNonExistingFile()
    {
        MockCommandParser parser = new MockCommandParser();
        Command cmd = parser.readFile("test_inputs/does_not_exist.auto");
        Assert.assertNotNull(cmd);
        Assert.assertFalse(parser.wasParsingSuccessful());
    }

    @Test
    public void testParseTestCommand()
    {
        MockCommandParser parser = new MockCommandParser();
        Command cmd = parser.readFile("test_inputs/auton_test/test_auton.auto");
        Assert.assertNotNull(cmd);
        Assert.assertTrue(parser.wasParsingSuccessful());
    }

    @Test
    public void testParseParallelCommand()
    {
        List<String> args = new ArrayList<>();
        args.add("TestCommand, 0, 1 | TestCommand, 2, 3");

        MockCommandParser parser = new MockCommandParser();
        parser.parseParallelCommand(args);

    }

    @Test
    public void testParseWaitCommand()
    {
        MockCommandParser parser = new MockCommandParser();
        parser.parseWaitCommand(Arrays.asList("WaitCommand", "1.2"));
    }

}
