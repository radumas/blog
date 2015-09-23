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

Note that the rest of the instructions must use the `3.3.3` version number for this maven build. Not that a restart is required after editing `~/.profile`. Once you log back in check the maven version with `mvn --version`


##Analyst Server
Clone the github repo and then edit `conf/application.conf.template`
Point to conveyal's vex server (the default). The local vex server support doesn't work [well anymore](https://github.com/conveyal/analyst-server/issues/117)
```shell
application.vex=
```
And then running `activator run -mem 1500` (my VPS has 2GB of memory). I had to run this command twice due to dependency downloads failing the first time. The create user HTTP request doesn't appear to work but by navigating to `vps.ip:9000` I was able to both create a user and access the tutorial. Chrome(ium) is the necessary browser, file uploads don't work in [Firefox](https://github.com/conveyal/analyst-server/issues/116#event-358016955)