package com.swe573.twitree;

import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class VisualizerService {
    private Twitter determiner = new TwitterFactory().getInstance();

    //private Logger logger = LoggerFactory.getLogger(ThreadVisualizerService.class);

    /**
     * Due to way Twitter Search works, this produces only <10 day old tweets.
     * This severely limits Thread display capabilities of our app.
     * Still, being a free user, it's beyond out capabilities. For a more thorough discussion:
     * https://twittercommunity.com/t/twitter-search-api-returns-much-fewer-results-than-the-twitter-client-search-engine-why/33388
     */
    public List<Status> GetChain(Status t) {
        LinkedList<Status> CHAIN = new LinkedList<Status>();
        List<Status> AUTHOR_TWEETS = new ArrayList<Status>();
        final User AUTHOR = t.getUser();
        Status last = t;
        boolean cond = true;

        // Populate the AUTHOR_TWEETS
        Query q = new Query("from:" + AUTHOR.getScreenName() + " to:" + AUTHOR.getScreenName());
        q.setSinceId(t.getId());
        q.setCount(100);
        do {
            QueryResult queryResult = null;
            try {
                queryResult = determiner.search(q);
            } catch (TwitterException e) {
                //logger.error("error querying Tweet", e);
            }
            AUTHOR_TWEETS.addAll(queryResult.getTweets());
            q = queryResult.nextQuery();
        } while (q != null);

        // Iterate and search over AUTHOR_TWEETS
        while (cond) {
            int i = 0;
            int currentSize = AUTHOR_TWEETS.size();
            for (; i < AUTHOR_TWEETS.size(); i++) {
                if (AUTHOR_TWEETS.get(i).getInReplyToStatusId() == last.getId()) {
                    CHAIN.add(last);
                    last = AUTHOR_TWEETS.get(i);
                    AUTHOR_TWEETS.remove(i);
                    i--;
                    break;
                }
                if (AUTHOR_TWEETS.get(i).getId() < last.getId() ||
                        AUTHOR_TWEETS.get(i).getInReplyToStatusId() < last.getId()) {
                    AUTHOR_TWEETS.remove(i);
                    i--;
                }
            }
            cond = (i < AUTHOR_TWEETS.size());
        }
        CHAIN.add(last);
        return CHAIN;
    }

    /**
     * Given a Tweet and a Popularity parameter, returns replies to it as a List object,
     * as ordered by Twitter Search according to Popularity.
     */
    public List<Status> GetReplies(Status tweet, boolean popularity) {
        String AuthorName = tweet.getUser().getScreenName();
        Query q = new Query("to:" + AuthorName);
        QueryResult qr;
        List<Status> returnee = new ArrayList<Status>();
        q.setSinceId(tweet.getId());
        q.setCount(100);
        /*if (popularity) q.setResultType(Query.ResultType.popular);
        else */q.setResultType(Query.ResultType.recent);

        do {
            try {
                qr = determiner.search(q);
            } catch (TwitterException e) {
                //logger.warn("Couldn't query, returning", e);
                return returnee;
            }
            for (Status t : qr.getTweets()) {
                if (t.getInReplyToStatusId() == tweet.getId()) returnee.add(t);
            }
            if (qr.hasNext()) q = qr.nextQuery();
            else break;
        } while (true);

        return returnee;
    }

    public List<Status> ShowDiscussion(InitializerPackage init) {
        if (init.nature() == TweetNature.THREAD) return GetChain(init.initializer());
        else return GetReplies(init.initializer(), init.popularity());
    }

}
