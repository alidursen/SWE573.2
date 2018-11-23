package com.swe573.twitree;

import twitter4j.Status;

public class InitializerPackage {
    private Status initializer;
    private TweetNature nature;
    private boolean popularity;

    public InitializerPackage(Status initializer, TweetNature nature, boolean popularity) {
        this.initializer = initializer;
        this.nature = nature;
        this.popularity = popularity;
    }

    public Status initializer(){ return this.initializer;   }
    public TweetNature nature(){ return this.nature;        }
    public boolean popularity(){ return this.popularity;    }
}
