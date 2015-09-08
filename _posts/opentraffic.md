---
layout: post
section-type: post
title: "Setting Up OpenTraffic on a Ubuntu 14.04 Laptop"
published: false
categories: Tutorials
author: Raphael Dumas
summary: "OpenTraffic is an open-source engine to generate observed speed statistics on road links from GPS observations of vehicles"
tags: [ 'ubuntu', 'traffic']
thumbnail: road  
---

OpenTraffic 

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

The app runs on `Java 1.8`, which isn't officially available on the `Ubuntu 14.04` channels, so I had to manually install `open-jdk-8-jdk` 
{% highlight bash %}
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get update
sudo apt-get install openjdk-8-jdk
{% endhighlight %}

column order is wrong is csv-loader README.

need to have a timezone in the time column

vehicle id needs to be an int