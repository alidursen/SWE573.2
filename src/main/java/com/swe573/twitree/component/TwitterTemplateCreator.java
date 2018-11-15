package com.swe573.twitree.component;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitterTemplateCreator {
    @Autowired
    public Environment env;

    public Twitter getTwitterTemplate(String accountName){
        String consumerKey = env.getProperty(accountName + ".consumerKey");
        String consumerSecret = env.getProperty(accountName + ".consumerSecret");
        String accessToken = env.getProperty(accountName + ".accessToken");
        String accessTokenSecret = env.getProperty(accountName + ".accessTokenSecret");

//      What? Is this the so-called Guava? Do I need it *right now*?
//      Turns out a simple maven import solves the issue. Just don't use 27.0 but stick to 23.5 as wiki suggests.
        Preconditions.checkNotNull(consumerKey);
        Preconditions.checkNotNull(consumerSecret);
        Preconditions.checkNotNull(accessToken);
        Preconditions.checkNotNull(accessTokenSecret);

        TwitterTemplate twitterTemplate =
                new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        return twitterTemplate;
    }
}
