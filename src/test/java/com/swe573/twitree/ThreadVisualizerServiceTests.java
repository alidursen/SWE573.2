/*
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
            normalDiscussionInitializer = twitterTester.showStatus(1077522206303928326L);
                // https://twitter.com/RealTimeWWII/status/1077522206303928326

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

    Long nDiscussionIds[] = new Long[]{
            1077552772357259265L, 1077544982960136192L, 1077536816696303620L,
            1077535925519806464L, 1077533204670746625L, 1077528194494988288L,
            1077524949542338560L, 1077523086428291072L, 1077522322427428864L
    };

    @Test
    public void GetReplies(){
        List<Status> unpopular = tester.GetReplies(normalDiscussionInitializer, false);
        Long ids[] = new Long[unpopular.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = unpopular.get(i).getId();
        }
        Assert.assertArrayEquals(nDiscussionIds, ids);
    }

}
*/
