# TwiTree
### For SWE573 Course @ BoUn, Fall 2018

This repository contains whole (or at least *most*) body of work that will be Ali Durşen's project for SWE573 course,
taught at BoUn in Fall 2018 by S. Üsküdarlı.

This contains the code, obviously, but also various documentation: requirements, design,
scheduling and plan, issue tracking. These non-code sub-products can be found under Issues, Projects, Wiki sections.

## How to Use

This project aims to offer a quality of life enhancement for Twitter users. Our service allows users to:
1. See long Twitter _Threads_ (ie. successive tweets authored by the same account) in their entirety.
1. See replies to a specific Tweet in tandem, as opposed to Twitter's _depth-first_ approach first.

To achieve this, users simply enter Tweet id or URL of Tweet they want to investigate. If system determines Tweet as
beginning of a Thread it asks the user for confirmation. Then a series of Tweets are displayed. From there, the user can
click `Replies` icon of a Tweet to get replies to it displayed.

For best results, users are encouraged to display everyday conversations, as opposed to discussions under high-profile
users. Alternatively, _specific discussions_ under high profile users can be tracked.

**Due to technical limitations, this service cannot be used to discussions/Threads under Tweets older than 10 days.**
If such a Tweet is entered by the user, only that Tweet will be displayed.

## Deployment

Project is deployed via [Heroku](www.heroku.com) at https://twitree573.herokuapp.com/. Heroku, as a platform, is very easy
to set up. It allows GitHub integration as one of deployment methods. For our purposes, we connected it to this repository
and deployed `release` branch manually. It also supports continuous deployment of branches, which requires branches to
be in a deployable state at all times. To ensure this, integration with continuous integration services are also allowed.

It is established that, as a measure of security, API keys shall not be released publicly. Heroku allows management of
this _out-of-repo_ information by allowing externally declaring configuration variables in its management interface.

## Current Status and Missing Features

GitHub repo is divided into 4 branches, with `master/release` coming from previous release (which essentially failed to load)
and `take2/take2dev` being our second attempt. The reason for this divergence is most likely our configuration or dependencies
disrupted Spring's capabilities. Heretafter `take2/take2dev` are our main development branches, with `take2` being used for
deployment and shall not be committed into but merged from `take2dev`. This divergence will reflect in README files as well;
as usual most recent one is the official document of the repository. If any significant change happens on off-branch README's,
`take2dev`'s will be updated accordingly.

The system/app is, as said above, is deployed on Heroku. Since we're using free version, program is **NOT** always running and
more than likely will take a while to open on browsers. This is because Heroku is booting the program up.

Currently, the system has a _Welcome_ page with a header to accept user input. Unlike our initial design with a single button
which then later pop-upped a confirmation window if necessary, we now relegated the responsibility of Thread/Discussion
determination to user by employing two buttons respectively. When supplies with a Tweet id or URL, the system takes the user
to appropriate view with relevant functionality.

For `Thread` views, system displays all Tweets of a Thread chain in a sequence on the lefthand side of the page.
For `Discussion` views, system displays supplied Tweet. Both pages are designed so that clicking general vicinity of a Tweet
text will bring forth replies to it: For Thread chains to the right of the view, and for every other type of Tweets directly
below original Tweet. This primarily satisfies our project goals and thus our product is released in beta stage. 

As for the missing features,
* *We need clearer user communication*: a how-to guide on home page should be welcome, as well as an indicator of which
Tweet's have replies. Even better is the case if we can somehow embed Tweet's per
[Twitter's guides and expectations](https://developer.twitter.com/en/docs/twitter-for-websites/embedded-tweets/overview.html).
Currently not prioritized.
* The **prioritized** communication tool is Tweet's being placed in a visibly distinct level than their parents.
* *Graphical display* of system is very bare-bones. There is room for improvement and building blocks of such improvement is
set in the form of `div` classes.
* Lastly, following our initial vision, replies are desired to be displayed in a vertical levelling system.

Cannot be (currently) completed due to technical limitations:
* Twitter API does not allow us to search for Tweet's older than a specific time. This greatly hampers our app's reach.
* Used library *does* have a way to obtain queries based on popularity... however, it doesn't seem to work and apparently
Twitter's search always returns in
[mixed type](https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets). Further testing required
to determine if this is due to our library, API access or Twitter API in general.
* On a Thread chain, getting replies will also bring other chain elements forth. This is another **Priority** item.
