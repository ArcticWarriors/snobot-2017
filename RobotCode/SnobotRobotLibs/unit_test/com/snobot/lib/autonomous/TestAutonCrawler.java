package com.snobot.lib.autonomous;

import org.junit.Assert;
import org.junit.Test;

import com.snobot2017.test.utilities.BaseTest;


public class TestAutonCrawler extends BaseTest
{

    @Test
    public void testCrawler()
    {
        SnobotAutonCrawler crawler = new SnobotAutonCrawler("", false);

        crawler.loadAutonFiles("test_inputs/auton_test");
        crawler.createSendableChooser(null);
        Assert.assertEquals(4, crawler.getPaths().size());
    }

    @Test
    public void testVerboseCrawler()
    {
        SnobotAutonCrawler crawler = new SnobotAutonCrawler("ignore_dir", true);

        crawler.loadAutonFiles("test_inputs/auton_test");
        crawler.createSendableChooser("empty.auto");
        Assert.assertEquals(3, crawler.getPaths().size());
    }

    @Test
    public void testIgnoreDir()
    {
        SnobotAutonCrawler crawler = new SnobotAutonCrawler("ignore_dir", false);

        crawler.loadAutonFiles("test_inputs/auton_test");
        Assert.assertEquals(3, crawler.getPaths().size());
    }

    @Test
    public void testCrawlerDoesNotExistDirectory()
    {
        SnobotAutonCrawler crawler = new SnobotAutonCrawler("");

        crawler.loadAutonFiles("does_not_exist");
    }
}
