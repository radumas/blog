---
layout: post
section-type: post
title: "Setting Up OpenTraffic on a Ubuntu 14.04 Laptop"
published: false
categories: Tutorials
author: Raphael Dumas
summary: "Tutorial on using GPS data in OpenTraffic."
tags: [ 'ubuntu 14.04', 'opentraffic']
thumbnail: road  
---

#Set Up

[OpenTraffic](https://opentraffic.io)

Install maven
{% highlight bash %}
sudo apt-get install maven
{% endhighlight %}

Seems the engine is now neatly packaged into a [web-app](https://github.com/opentraffic/traffic-engine-app) which you run as a jar from the command-line and then access from a browser. 


Building it from source fails with:

{% highlight bash %}
[ERROR] Failed to execute goal on project traffic-engine-app: Could not resolve dependencies for project com.conveyal:traffic-engine-app:jar:0.0.1-SNAPSHOT: Failure to find com.conveyal:traffic-engine:jar:0.1-SNAPSHOT in http://maven.conveyal.com/ was cached in the local repository, resolution will not be reattempted until the update interval of conveyal has elapsed or updates are forced -> [Help 1]
org.apache.maven.lifecycle.LifecycleExecutionException: Failed to execute goal on project traffic-engine-app: Could not resolve dependencies for project com.conveyal:traffic-engine-app:jar:0.0.1-SNAPSHOT: Failure to find com.conveyal:traffic-engine:jar:0.1-SNAPSHOT in http://maven.conveyal.com/ was cached in the local repository, resolution will not be reattempted until the update interval of conveyal has elapsed or updates are forced
{% endhighlight %}

The app runs on `Java 1.8`, which isn't officially available via the `Ubuntu 14.04` channels, so I had to manually install `open-jdk-8-jdk` 
{% highlight bash %}
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get update
sudo apt-get install openjdk-8-jdk
{% endhighlight %}

Some notes on implementation 

1. The csv shouldn't have a header.
2. Column order is wrong is csv-loader README. It should be `utc_timestamp,unique_vehicle_id,lon,lat`
3. The timestamp must be [ISO 8601 with timezone](https://en.wikipedia.org/wiki/ISO_8601) e.g. `2015-09-08 01:55:28+00:00` 
4. The vehicle id needs to be an integer. Since vehicle IDs in the Rio dataset are 6 character strings with a leading letter and 5 numbers, I converted the first letter to a number using Postgresql's `ascii(string)` [function](http://www.postgresql.org/docs/9.3/static/functions-string.html#FUNCTIONS-STRING-OTHER) which converts a ascii character into an "code point of the character" (an integer). 
5. There was an error when running the csv-uploader, that the calls to Conveyal's vex server were failing. After some support on twitter, I opened an [issue on github](https://github.com/opentraffic/traffic-engine-app/issues/2). While I was able to resolve the issue with [this workaround](https://github.com/opentraffic/traffic-engine-app/issues/2#issuecomment-139228168), it seemed the situation was is still developing.

##To be continued....