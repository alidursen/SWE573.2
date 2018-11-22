package com.swe573.twitree.controller;

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
        Status initializer = obtainTweetFromInput(request.getQueryString());

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
    @PostMapping(value = "/header")
    public String getHeader(final Model model, @ModelAttribute("form") String tweetId){
        Status initializer = obtainTweetFromInput(tweetId);
        //pop-up
        return "/popup";
    }


    private Status obtainTweetFromInput(String s){
        long id = 0;
        Status tw = null;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        try {
            tw = determiner.showStatus(id);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tw;
    }
}
