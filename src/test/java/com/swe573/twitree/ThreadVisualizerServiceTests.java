package com.swe573.twitree;

import com.swe573.twitree.service.ThreadVisualizerService;
import org.junit.Assert;
import org.junit.Test;
import twitter4j.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadVisualizerServiceTests {
    private ThreadVisualizerService tester = new ThreadVisualizerService();
    private Twitter twitterTester = new TwitterFactory().getInstance();
    private Logger logger = Logger.getAnonymousLogger();

    Status threadInitializer, popularDiscussionInitializer, normalDiscussionInitializer;
    {
        try {
            threadInitializer = twitterTester.showStatus(1072416425229332481L);
                // https://twitter.com/_geyik/status/1072416425229332481
            popularDiscussionInitializer = twitterTester.showStatus(1071804193848074241L);
                // https://twitter.com/CREWcrew/status/1071804193848074241
            normalDiscussionInitializer = twitterTester.showStatus(1071381180489089025L);
                // https://twitter.com/BurakKadercan/status/1071381180489089025

        } catch (TwitterException e) {
            logger.log(Level.SEVERE, "Error at getting Tweet", e);
        }
    }
    Long ShortThreadIds[] = new Long[]{
            1072416425229332481L, 1072416426374426624L, 1072416427842396161L,
            1072416430451253249L, 1072416432183492608L, 1072416433433440258L,
            1072416434700148736L, 1072416436050628609L, 1072416437787127808L
    };

    @Test
    public void GetChainWorks(){
        Long resultIds[] = new Long[9];
        List<Status> result = tester.GetChain(threadInitializer);
        for (int i=0; i<result.size(); i++){
            resultIds[i] = result.get(i).getId();
        }
        Assert.assertArrayEquals(ShortThreadIds, resultIds);
    }

/*
    @Test
    public void GetReplies(){
        QueryResult popular = tester.GetReplies(popularDiscussionInitializer, true);
        QueryResult unpopular = tester.GetReplies(normalDiscussionInitializer, false);
    }
*/

}
