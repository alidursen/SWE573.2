package com.swe573.twitree.controller;

import com.swe573.twitree.InitializerPackage;
import com.swe573.twitree.TweetNature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import twitter4j.*;
import twitter4j.api.SearchResource;
import twitter4j.api.TweetsResources;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DisplayDeterminer {
    private Twitter determiner;
    private Status initializer;
    private int[] POPULARITY_COEFFICIENTS = { 2, 1 };
    private int   POPULARITY_CONSTRAINT = 60;
    private InitializerPackage initPack;

    @Autowired
    public DisplayDeterminer() {
        /**/
    }

    @GetMapping(value = "/")
    public String home(final Model moder){
        return "home";
    }

    @GetMapping(path = "/*")
    public String isThread(final Model model, HttpServletRequest request){
        initializer = obtainTweetFromInput(request.getQueryString());

        if(determineNature(initializer)==TweetNature.THREAD){
            //ask for confirmation
            //if user chooses DISCUSSION over THREAD
            initPack = new InitializerPackage(initializer, TweetNature.DISCUSSION, determinePopularity(initializer));
        }
        initPack = new InitializerPackage(initializer, determineNature(initializer), determinePopularity(initializer));

        //set pop-up to open
        return "home";
    }

/*
    //read popup
    @PostMapping("/")
    public String confirmThread(final Model model){
        if(Decision == Thread){
            return "/thread";
        }
        else{
            return "/discussion";
        }
    }
*/

    //read header
    @GetMapping(value = "/header")
    public String getHeader(final Model model, @ModelAttribute("form") String tweetId){
        initializer = obtainTweetFromInput(tweetId);
        //pop-up
        return "/popup";
    }


    private Status obtainTweetFromInput(String s){
        if(s.contains("/"))
            s = s.substring( s.lastIndexOf('/') );

        Status tw = null;

        try {
            tw = determiner.showStatus(Long.parseLong(s));
        } catch (NumberFormatException e){
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tw;
    }

    private boolean determinePopularity(Status tw){
    /**     If the tweet in question is determined to be "popular", then returns true. For our purposes,
     *      popularity means whether they have more than a set number of replies.
     *
     *      Turns out Twitter API releases mention count to Premium and Enterprise tier users, which we are not.
     *      Thus we will instead revert to a combination of retweet and like count.         */

        int tweetPopularity = tw.getRetweetCount()*POPULARITY_COEFFICIENTS[0] +
                tw.getFavoriteCount()*POPULARITY_COEFFICIENTS[1];
        return tweetPopularity > POPULARITY_CONSTRAINT;
    }

    private TweetNature determineNature(Status tw){
    /**     AN INITIALIZER BELONGS TO A THREAD IF ITS AUTHOR REPLIED TO IT, AND IS A DISCUSSION OTHERWISE.
     *
     *      Since Twitter does not, for one reason or another, supply the user with a list of replies,
     *      we will instead try this: we know the author, so search their tweets to find one that is in fact
     *      reply to initializer.
     *
     *      Library we use (Twitter4j) suggests that their method returns last 20 tweets of the use;
     *          (http://twitter4j.org/javadoc/twitter4j/api/TimelinesResources.html#getUserTimeline-long-)
     *      but they also say that this "calls https://api.twitter.com/1.1/statuses/user_timeline"
     *      which in turn is said to return up to 3200 tweets by the user;
     *          (https://developer.twitter.com/en/docs/tweets/timelines/api-reference/get-statuses-user_timeline)
     *      One possible explanation may be that, of course, twitter4j is old. All in all, there is some discrepancy
     *      AND that is also probably why we can't use additional parameters.
     *
     *      METHOD UPDATE: we have access to Search functionalities, which CAN search by author and replyto.    */

        long tweetID = tw.getId();
        Query q = new Query("from:" + tw.getUser().getName() + " to:" + tw.getUser().getName());
        q.setSinceId(tweetID);
        QueryResult queryResult = null;
        List<Status> authorTweets;
        do {
            try {
                queryResult = determiner.search(q);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            authorTweets = queryResult.getTweets();
            if(authorTweets != null){
                for (Status s: authorTweets) {
                    if ( s.getInReplyToStatusId() == tweetID )
                        return TweetNature.THREAD;
                }
                q = queryResult.nextQuery();
            }
        } while (q != null);
        return TweetNature.DISCUSSION;
    }
}
