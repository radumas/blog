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
The software requires [Play](http://www.playframework.com) and a [vanilla extract](https://github.com/conveyal/vanilla-extract.git) server.

###Play
Download the latest "online" zip with 
```shell
wget https://downloads.typesafe.com/typesafe-activator/1.3.5/typesafe-activator-1.3.5-minimal.zip
7z x typesafe-activator-1.3.5-minimal.zip
#Verify that the activator script is executable
cd activator-1.3.5-minimal/
ls -lha
export PATH=/path/to/activatorfolder/:$PATH
```

###~~vanilla extract~~
**not used**

##Analyst Server
Clone the github repo and then edit `conf/application.conf.template`
Point to conveyal's vex server (the default). The local vex server support doesn't work [well anymore](https://github.com/conveyal/analyst-server/issues/117)
```shell
application.vex=
```
And then running `activator run -mem 1500` (my VPS has 2GB of memory). I had to run this command twice due to dependency downloads failing the first time. The create user HTTP request doesn't appear to work but by navigating to `vps.ip:9000` I was able to both create a user and access the tutorial. Chrome(ium) is the necessary browser, file uploads don't work in [Firefox](https://github.com/conveyal/analyst-server/issues/116#event-358016955)