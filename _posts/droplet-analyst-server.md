---
layout: post
section-type: post
title: "Setting Up Conveyal's Analyst-Server on a Ubuntu 14.04 Droplet"
date: 2015-07-28 16:06:00
published: false
categories: Tutorials
author: Raphael Dumas
tags: [ 'digital ocean', 'server']
thumbnail: map-marker  
---

[Conveyal's](https://www.conveyal.com) [Analyst Server](https://github.com/conveyal/analyst-server/) is:

> Analyst Server is a graphical user interface-driven, web-based tool for accessibility analysis of transportation networks. Its current focus is on
> public transportation systems, but that is not a design goal; that is simply what has been implemented so far. It allows the user to upload a
> representation of a transit network (real or hypothetical) and perform analysis against it almost immediately.

These instructions detail how to set up an instance in a Digital Ocean Droplet running `Ubuntu 14.04`

##Dependencies
The software requires [maven](https://en.wikipedia.org/wiki/Apache_Maven) to manage dependencies. Unfortunately (and this seems to be a theme), maven 3.1.1+. Toget this I referred to [these instructions](http://askubuntu.com/questions/420281/how-to-update-maven-3-0-4-3-1-1) but using the latest binary:
{% highlight bash %}
wget http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
{% endhighlight %}
Note that the rest of the instructions must use the `3.3.3` version number for this maven build. Not that a restart is required after editing `~/.profile`. Once you log back in check the maven version with `mvn --version`

## Analyst Server

### Building
Clone the github repo and then build with `mvn clean package`

### Stormpath
You have to create a [Stormpath](https://stormpath.com) account. Once you log in click on the create key button on the right (I have a key so it now says manage key). Once you click that link you will be prompted to download a file containing the keys.

![]({{site.baseurl}}/img/posts/sp_create_key.png)

Since Analyst Server is hosted on a server, and not the laptop with which I'm browsing the internet, I had to copy the key to the machine using `scp` from my laptop's download folder. (use TAB to autocomplete the first file-name, and don't forget to change the ip and homefolder name for your droplet).
{% highlight bash %}
scp apiKey-RANDOMLETTERS.properties user@dro.pl.et.ip:/home/user/Downloads/
{% endhighlight %}

On the server, move the key to the `analyst-server` folder.

Still on the Stormpath admin webpage, go to the `Directories` tab, click on `My Application Directory` to the left, then `Accounts` and create a new account, the username and password for this account will be what you use to log in to the OpenTripPlanner Analyst web-app. 

Click on the `Applications` tab and then on `My Application`, hover over and then click on the `Name` field of `My Application` to edit it to something without a spacebar, because something will freak out if this has a spacebar. I set it to `MyApplication`

If your account isn't a part of a group Stormpath and Analyst-Server will freak out, so do that too. Go to the `Groups` tab and create a new Group. Then go to `Accounts` tab, click on your account (the version in "My Application Directory Directory", then click on `Groups` on the left, and then `Add to Group`.

### Editing Application Configuration

Then edit `application.conf` by first doing `cp application.conf.template application.conf` and then `nano application.conf`. Edit the path to the key you just moved, and edit `stormpath-name` to the name you changed the StormPath application above (here it is `MyApplication`).  
{% highlight bash %}
# The path to your Stormpath apiKey.properties file
auth.stormpath-config=apiKey-RANDOMLETTERS.properties             
# The name of your stormpath application.
# The Stormpath app name is also used as a prefix for custom data stored with u$
auth.stormpath-name=MyApplication
{% endhighlight %}

Point to conveyal's vex server. The local vex server support doesn't work [well anymore](https://github.com/conveyal/analyst-server/issues/117). While working on a different application I got [this workaround](https://github.com/opentraffic/traffic-engine-app/issues/2) to get the vex server to work. Check that issue thread to see what the status is.
```shell
application.vex=http://osm.conveyal.com
```

### Running 
And then running with `java -Xmx1800m -jar target/analyst-server.jar` (my VPS has 2GB of memory). 

You can navigate in your web browser (Chrome(ium) is the necessary browser, file uploads don't work in [Firefox](https://github.com/conveyal/analyst-server/issues/116#event-358016955)) to `dro.pl.et.ip:9090` or `localhost:9090` if you did this all locally. 

## Navigating Analyst-Server
You can click on Tutorial to read more, or log in with the credentials you created on StormPath above, and create a new project. Give your project a name and then navigate to the area you're interested in on the map and click `Set Location`