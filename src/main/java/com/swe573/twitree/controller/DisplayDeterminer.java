package com.swe573.twitree.controller;

import com.swe573.twitree.InitializerPackage;
import com.swe573.twitree.TweetNature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import twitter4j.*;
import twitter4j.api.TweetsResources;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DisplayDeterminer {
    private Twitter determiner;
    private Status initializer;
    private int[] POPULARITY_COEFFICIENTS = { 2, 1 };
    private int   POPULARITY_CONSTRAINT = 60;

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
        int tweetPopularity = tw.getRetweetCount()*POPULARITY_COEFFICIENTS[0];
        tweetPopularity += tw.getFavoriteCount()*POPULARITY_COEFFICIENTS[1];
        return tweetPopularity > POPULARITY_CONSTRAINT;
    }

    private TweetNature determineNature(Status tw){
        /**     AN INITIALIZER BELONGS TO A THREAD IF ITS AUTHOR REPLIED TO IT, AND IS A DISCUSSION OTHERWISE.
         *
         *      Since Twitter does not, for one reason or another, supply the user with a list of replies,
         *      we will instead try this: we know the author, so search their tweets to find one that is in fact
         *      reply to initializer.       */
        long authorID = (tw.getUser()).getId();
        ResponseList<Status> authorTweets=null;
        try {
            authorTweets = determiner.timelines().getUserTimeline(authorID);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status s: authorTweets) {
            if ( ((s.getUser()).getId() == authorID) && (s.getInReplyToStatusId() == tw.getId()) ){
                return TweetNature.THREAD;
            }
        }
        return TweetNature.DISCUSSION;
    }

}
