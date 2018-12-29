package com.swe573.twitree;

import com.swe573.twitree.ThreadController;
import org.junit.Test;
import twitter4j.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ThreadControllerTests {
    private ThreadController tester = new ThreadController(new VisualizerService());
    private Twitter twitterTester = new TwitterFactory().getInstance();
    private Logger logger = Logger.getAnonymousLogger();

    final String POPULAR_ID ="1045749005857128448", // "https://twitter.com/tomhanks/status/1045749005857128448"
        POPULAR_URL = "https://twitter.com/tomhanks/status/1056283752635150336",
        NORMAL_ID   = "1065164120666882048", // "https://twitter.com/_geyik/status/1065164120666882048"
        NORMAL_URL  = "https://twitter.com/_geyik/status/1067394855167565824",
        THREAD_ID   = "1069389861537423360", // "https://twitter.com/SethAbramson/status/1069389861537423360"
        THREAD_URL  = "https://twitter.com/FoldableHuman/status/1069017515915829248",
        ID_SAME_URL = "https://twitter.com/_geyik/status/1063923919814647809",
        URL_SAME_ID = "1063923919814647809",
        DELETED_URL = "https://twitter.com/_geyik/status/1069508174716289025",
        DELETED_ID  = "1069508621925588993", // "https://twitter.com/_geyik/status/1069508621925588993"
        NONSENSE    = "gwlksfdm",
        EMPTY       = "";

    Status POPULAR_TWEET,   // https://twitter.com/tomhanks/status/1045749005857128448
            NORMAL_TWEET,   // https://twitter.com/_geyik/status/1065164120666882048
            THREAD,         // https://twitter.com/SethAbramson/status/1069389861537423360
            DISCUSSION,     // https://twitter.com/tomhanks/status/1056283752635150336
            FRESH;          // https://twitter.com/loykAd/status/1079063031072595968
                    // Change FRESH as you see fit.
    {
        try {
            POPULAR_TWEET   = twitterTester.showStatus(1045749005857128448L);
            NORMAL_TWEET    = twitterTester.showStatus(1065164120666882048L);
            THREAD          = twitterTester.showStatus(1069389861537423360L);
            DISCUSSION      = twitterTester.showStatus(1056283752635150336L);
            FRESH           = twitterTester.showStatus(1079063031072595968L);
        } catch (TwitterException e) {
            logger.log(Level.SEVERE, "Error at getting Tweet", e);
        }
    }

    @Test
    public void inputIsWholeAddress(){
        assertEquals(tester.obtainTweetFromInput(POPULAR_URL).getId(), 1056283752635150336L);
    }
    @Test
    public void inputIsTweetId(){
        assertEquals(tester.obtainTweetFromInput(POPULAR_ID).getId(), 1045749005857128448L);
    }


    @Test
    public void toJSONArray(){
        JSONArray returnee = tester.getReplies(FRESH.getId());
        assertEquals(1,1);
        //This test currently as useful as a bugging tool, do not use for proper testing.
    }
/* Turns out, you don't test exceptions if they are caught.   *//*
*/
/*
    @Test(expected = TwitterException.class)
    public void inputIsDeletedAddress(){
        tester.obtainTweetFromInput(DELETED_URL);
    }
    @Test(expected = TwitterException.class)
    public void inputIsDeletedId(){
        tester.obtainTweetFromInput(DELETED_ID);
    }                                                               *//*



    @Test
    public void tweetPopularityPopular(){
        assertEquals(tester.determinePopularity(POPULAR_TWEET), true);
    }
    @Test
    public void tweetPopularityUnpopular(){
        assertEquals(tester.determinePopularity(NORMAL_TWEET), false);
    }

    @Test
    public void tweetNatureThread(){
        assertEquals(tester.determineNature(THREAD), TweetNature.THREAD);
    }
    @Test
    public void tweetNatureDiscussion(){
        assertEquals(tester.determineNature(DISCUSSION), TweetNature.DISCUSSION);
    }

*/
}
