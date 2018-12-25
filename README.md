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

## Current Status and Missing Features

Currently, the system has the ability determine if a given Tweet belongs to a Thread or not. Further, it can obtain
Threads in their entirety and get replies to a specific Tweet. (As stated above, these features are only available to
fresh Tweets.)

We are missing any graphical user interaction. Project as it stands is not suitable for deployment.

## Deployment

Project is deployed via [Heroku](www.heroku.com) at https://twitree573.herokuapp.com/. Heroku, as a platform, is very easy
to set up. It allows GitHub integration as one of deployment methods. For our purposes, we connected it to this repository
and deployed `release` branch manually. It also supports continuous deployment of branches, which requires branches to
be in a deployable state at all times. To ensure this, integration with continuous integration services are also allowed.

It is established that, as a measure of security, API keys shall not be released publicly. Heroku allows management of
this _out-of-repo_ information by allowing externally declaring configuration variables in its management interface.