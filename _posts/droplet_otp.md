---
layout: post
section-type: post
title: "Setting up Open Trip Planner on a Droplet"
date: 2015-07-28 16:06:00
published: false
categories: Tutorials
author: Raphael Dumas
tags: [ 'digital ocean', 'server', 'otp']
thumbnail: map-marker  
---


Had to manually install `open-jdk-8-jdk` 

    sudo add-apt-repository ppa:openjdk-r/ppa
    sudo apt-get update
    sudo apt-get install openjdk-8-jdk


Had to remove `gtfs-lib` from `pom.xml` and build using a different command (`mvn -T 1C package -D maven.test.skip`) as per [bug report ](https://github.com/opentripplanner/OpenTripPlanner/issues/2023#issuecomment-118058175]

Maven build successful

To build otp graph for Boston

    ./otp --basePath build --build build


To host server

    ./otp --analyst --autoScan --basePath build --server

