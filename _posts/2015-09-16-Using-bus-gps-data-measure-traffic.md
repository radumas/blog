---
layout: post
section-type: post
title: "Using Bus GPS Data to Generate Street Speeds with OpenTraffic"
published: true
categories: coppe-ltc
author: Raphael Dumas
summary: "Hearing many people mentioning the difficulty of reliable travel times in Rio without citing data, I decided to see if OpenTraffic could shed some light."
tags: [ 'traffic', 'opentraffic']
thumbnail: bus  
---

When you say you work in transportation, it is common in major cities for people to complain to you about the uniquely horrible traffic there. Rio is certainly no exception, and it does have the [longest commute times in Brazil](http://urbandemographics.blogspot.com.br/2014/11/the-evolution-of-commute-times-in.html). What seems unique (to me) here is the semi-predictable reliability of the traffic. Since most public transit riders travel in buses in mixed-traffic as opposed to BRT, Metro, Boat, or Gondola (which, due to gang violence, has its own unique reliability issues), this affects a large proportion of commuters. Unreliability can seriously affect peoples' lives: if a typical 1hr commute can *occasionally* be **2 hours** (or more), do you buffer your commute time according to the occasional disastrous conditions?

Though I heard many stories of this lack reliability in travel speeds on the street network, no one had any data beyond anecdotes and hearsay. Though so-called "indigenous knowledge", the experiences of locals, can be valuable, I was taught to rely on impersonal data, preferably lots of it. Before arriving here in Rio I had heard of [OpenTraffic.io](https://opentraffic.io). It is an Open-Source (free to use and free to modify the code) platform built by a partnership of Conveyal, the World Bank, Mapbox and Mapzen which aims to collect GPS traces from fleets of vehicles in cities around the world and eventually provide historic traffic speeds in [Open Street Map](http://www.openstreetmap.org), an API, and other formats. 

!['Open Traffic Diagram (C) 2015 opentraffic'](http://opentraffic.io/img/process.png)

Data on traffic is currently expensive to collect, though with ubiquitous GPS sensors the passive collection of massive amounts of vehicle locations is possible. Most of the time this data is proprietary, such as data from the crowd-sourced Waze app, which can be bought from Google, or the data collected by large commercial fleets such as FedEx or UPS for their private uses. But cities can have access to data from fleets of city-owned vehicles, or from city-managed vehicles such as taxis and buses, and these are the kinds of data generators that OpenTraffic is interested in collecting data from. Input a collection of data points consisting of a vehicle id and a timestamped GPS position, and the OpenTraffic engine will produce anonymized information on traffic speeds on city streets.

With this data one can more accurately model accessibility, the number of potential opportunities an individual can access using either bus or car modes. It will allow apps to better represent travel times for users seeking directions. With more accurate travel time and accessibility estimation more informed decision-making can occur over which transportation investments to make.

# Further Reading on this Project
The following other posts will appear in this series (check back for links):  
1. Explaining how I pulled and archived data from FeTranspor (the local bus conglomerate)
2. A introductory analysis of the bus GPS data
3. How I setup OpenTraffic and processed the gps data to work with the platform
4. Analysis of the resulting traffic speeds and the coverage of the bus GPS data
