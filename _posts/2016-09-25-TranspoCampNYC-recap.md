---
layout: post
section-type: post
title: "Transportation Camp NYC 2016 Recap #TCNYC2016"
published: true
date: 2016-09-26 22:00:00 -0500
categories: conference-recaps
author: Raphael Dumas
summary: "I attended and led a number of sessions at Transportation Camp NYC on September 24th 20126"
tags: [ 'bikeways4everybody', 'crowdsourcing', 'opentraffic','TranspoCamp','Conference']
thumbnail: subway
---

On Saturday, September 24th 2016 I attended [Transportation Camp NYC](http://transportationcamp.org/events/nyc-2016/), an "unconference" about all (most) things transportation (mostly urban terrestrial). Before, during, and after a lengthy introduction of each attendee where each lists 3 words to describe themself, attendees propose sessions on a 8 x 11" colourful page which are then organized into a colourful laundry board. That's right there is no predefined schedule, it's all made on the spot! Attendees are also encouraged to collaborate to improve sessions as well as "vote with their feet" and leave if a session is boring. 

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">The final schedule is up on The Board and live on our website: <a href="https://t.co/5j77xUsnwG">https://t.co/5j77xUsnwG</a> Enjoy the rest of <a href="https://twitter.com/hashtag/TCNYC2016?src=hash">#TCNYC2016</a>! <a href="https://t.co/j6jpmXiRRU">pic.twitter.com/j6jpmXiRRU</a></p>&mdash; TransportationCamp (@TranspoCampNYC) <a href="https://twitter.com/TranspoCampNYC/status/779741831055695872">September 24, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

The full schedule of the day and links to all the notes is [here](https://docs.google.com/spreadsheets/d/1J0ciEbFZTjj-x2Elv33cFdLhZmjvmqxf5e9RdDpQJNc/pubhtml?gid=1321426310&single=true). Unfortunately the TCNYC migrated away from the traditional "Hackpad" form of collaborative notes in favour of Google Docs, and for whatever reason you had to sign to view/edit every. Single. Session. The encouragement to take notes collaboratively was not there, so most of those session notes are actually empty.

I had the fortune/misfortune of leading and co-leading 3 of 4 sessions, so didn't get as much chance to see other sessions as I now realize I would have liked. Below are a summary of the sessions I led and attended.

## Session 1: How 2 Engage Communities Online 
*(Co-led with [Lou Huang](https://twitter.com/saikofish))*

[notes](https://docs.google.com/document/d/1dtxpZnqJxiQednick_pz_Tvkg4-dQil2YFhKQyqRNqE/edit)

During my time with the [Boston Cyclists Union](http://bostoncyclistsunion.org/) I created an online map to crowdsource where people want better bicycling infrastructure called "Bikeways for Everybody". I've written about it more [here]({{site.baseurl}}/bcu/2016/02/22/Draw-where-you-want-better-bike-infra.html). Lou created the much cooler tool [Streemix](http://streetmix.net/), which is a website that allows anyone to edit and remix street cross-sections. It's a powerful way to explore what a street could be like, while also understanding what tradeoffs exist when you have a fixed width to work with. 

I showed the timelapse map below of Bikeways user submissions being captured, and explained the architecture of the project as well as some mishaps along the way (like not making the site mobile-responsive [from the start]({{site.baseurl}}/bcu/tutorial/2016/05/12/This-one-simple-line-of-code-could-make-your-map-mobile-responsive.html)). Now that it's mostly responsive, cyclists could input locations or routes they want improved while they ride, or, at fundraisers, we can have people walk around with tablets and get folks to input their comments to the map.

<iframe width='100%' height='480' frameborder='0' src='https://bcu.carto.com/viz/eec647c8-d744-11e5-89cf-0e3ff518bd15/embed_map/' allowfullscreen webkitallowfullscreen mozallowfullscreen oallowfullscreen msallowfullscreen></iframe>

I also showed [this map](https://bcu.carto.com/viz/4aa385ec-f83d-11e5-9dc1-0e3ff518bd15/public_map) of the aggregated input. Because the stack involved in the app is all hosted on free cloud services, it is (should be) easy for another city/organization to bring Bikeways to their own city. And this app isn't limited to being about collecting input about bike routes, it could be about great walking routes, where sidewalks are inaccessible, where you want new bus service. [Go create your version of it now please.](http://github.com/radumas/bikeways4everybody) Seriously, it's easy.

Lou talked about how he used Universal Design principles to try to make Streetmix easily usable by people speaking all languages. It's currently been(being?) translated into Spanish and Chinese. Someone from the audience suggested [Sapelli](https://www.ucl.ac.uk/excites/software/sapelli), data collection tool for low literacy (and/or low digital literacy) communities (such as people in Africa). Streetmix kind of got the shorter half of the session, sorry Lou. 


## Lunch (notable for reasons unrelated to food)

Is there any environment more prone to rumours than Academia? Lunch was abuzz with rumours that, after slowly and painfully suggesting a number of academic staff and professors retire (as well as other rumoured underhanded tactics), MIT's Department of Civil and Environmental Engineering was finally going to get  rid of the [Master of Science in Transportation (MST)](http://cee.mit.edu/graduate/mst). The program I graduated from (and, not to be too biased, but probably the best Transportation Masters in America). All alums from the program at TCNYC held an emergency meeting of what to do. It was heartening to see how many of us there were in attendance, and how much we care about the program. What to do about the impending shuttering of it... is to be continued. 

## Session 2: How to Create you own Crowsourcing Webmap 
*(led by me)*

I led a workshop that I've led at a number of Transportation Camps before teaching the use of Github, Leaflet, and Carto(DB) through a goal-oriented session to create a crowdsourcing map that works in 40-50 minutes. The repository is [here](https://github.com/radumas/crowdmap-basic) and you can follow along to it yourself with minimal guidance by scrolling down to the *Readme*. 

A few hiccoughs happened, the projector just refused to accept input, due to a faulty connection, so I had to try to explain entirely by describing what I saw on my screen while going through the steps. This worked about as well as I thought it would (pretty poorly) and highlighted the lack of clarity in my instructions AND the complexity of the UIs of Carto & Github.

There was further a problem with some of the imports on the page being of mixed security protocol, which inspires me to try to revise the content and trim it down even more (e.g. why do I need to import a font) and upgrade Leaflet to 1.0 (and see if the leaflet JS packages are actually being served over HTTPS).

## Session 3: Transit and Traffic Data for Developing Countries and the US 
*(co-led with Holly Krambeck of the World Bank)*

Holly introduced a suite of open-source reporting and analytical tools the World Bank ([see here](https://github.com/WorldBank-Transport)) was funding including:
 - DRIVER
 - Rural Road Accessibility 
 - OpenTraffica
 - the OpenTransport Foundation
 
[**DRIVER**](https://github.com/WorldBank-Transport/DRIVER) is an incident reporting tool for the Philippines for the police to use. It will vastly improve upon their paper-based system and enables basic geographic analysis of hotspots of incidents. Someone asked about the validity of the underlying basemap and Holly replied that the Bank had been helping organize a number of Open Street Map training sessions for government employees to make sure OSM is up to date.

[**Rural Road Accessibility**](https://github.com/WorldBank-Transport/Rural-Road-Accessibility) (a work in progress still, since the repo appears to be mostly empty) is a browser-based tool that calculates accessibility to jobs, hospitals, and some other amenities for rural populations based on a road file and other GIS files for those points of interest.

[**OpenTraffic**](http://opentraffic.io/) is a free and open alternative to traffic probe data (from the likes of Inrix, Here, and TomTom) based on data provided by ride-hailing (or TNC) companies in developing countries. It ingests GPS records from "taxi" drivers and spits out aggregated traffic speeds by time of day along with a handy browser analytic tool. The ambition is for this to be a global repository of traffic speed data. This data could then be used for planning and analysis, but also in open-source navigation apps that don't have access to real-time feeds like those mentioned above.

**The OpenTransport Foundation**(couldn't find a link) is in part a migration of [transitland](https://transit.land/), a repository of GTFS schedules, to a more global organization: a World Bank/UN foundation, in order to make it easier for transit navigation apps (Google, Apple, Transit, moovit, CityMapper, etc...) to receive schedule information. Its role is also expanded to include open-source tools for creating, editing, visualizing, and analysing GTFS feeds that the World Bank has funded, as well as a course on how to create your own feed. 

### My link to this session: the discussion
Most of my work thus far at the City of Toronto has been using 3rd party traffic data provided by a major vendor to look at congestion trends in the city, as well as lay the groundwork for: quantifying delays from incidents, before-after studies of disruptions or Vision Zero improvements, etc... We would be interested in finding ways to procure this traffic data for free and I was also interested in having a discussion around other potential uses for this data. Since my time in Rio I'd become interested in OpenTraffic [when I noticed](http://www.radumas.info/blog/coppe-ltc/2015/09/16/Using-bus-gps-data-measure-traffic.html) that buses were both victims and causes of traffic. I've been curious to see how more open (and citizen-science-y) sources of data could be fed into OpenTraffic.

Alas this was not the audience for the discussion I wanted. It instead veered towards whether there's a global standard for crash reporting/statistics (which would be valuable for comparative policy analyses) and what possible evolutions GTFS might take. 

## Session 4: Measuring Transit Performance with Big Open Data 
*([Laura Riegel](https://twitter.com/LauraRiegel), [Zak Accuardi](https://twitter.com/zaccuardi), and someone who's name I missed)*

Laura is a fellow [MIT Transit Lab alum](http://transitlab.mit.edu/) who now works for [IBI](http://www.ibigroup.com/contact/ibi-group-in-boston). They've been merging disparate real-time produced by the MBTA into the MBTA's GTFS-RealTime Feed while also adding a portal for Operations Controllers to put out service alerts via GTFS-RT. From these open feeds, Laura presented, IBI has also built a performance dashboard and analytics platform that's been used internally. There was also some good news.

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">Performance analytics package <a href="https://twitter.com/ibigroup">@ibigroup</a> developed for <a href="https://twitter.com/hashtag/MBTA?src=hash">#MBTA</a> based on <a href="https://twitter.com/hashtag/gtfs?src=hash">#gtfs</a> <a href="https://twitter.com/hashtag/gtfsrt?src=hash">#gtfsrt</a> will soon be <a href="https://twitter.com/hashtag/opensource?src=hash">#opensource</a>! <a href="https://twitter.com/hashtag/TCNYC2016?src=hash">#TCNYC2016</a></p>&mdash; Raphael Dumas (@DumasRaphael) <a href="https://twitter.com/DumasRaphael/status/779778669061496832">September 24, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

Next Zak talked about work that Transit Center helped facilitate: [the MTA Bus Data API](http://busdataapi1.cloudapp.net/about). The MTA produces a lot of real-time data, and some interested citizens and civic technologists wanted to create an archive of the data with add-on analytics capabilities so that other interested citizens can analyze Bus performance for themselves. 

The third speaker also talked about MTA Bus Data, but I've got no notes or tweets to refresh my memory. Sorry.

## Takeaways
It's always nice, if a little overwhelming, to catch up with so many friends in transportation. Being involved in so many sessions was exhausting and something I will definitely avoid in the future. 

One of the great aspects of Transportation Camp is the local vibe and the low-barrier to entry: cheap admission and held on the weekend allow enthusiasts, activists, and government employees to rub shoulders with academics and transportation professionals. But it does prevent some people from attending: people with families. I tweeted about this 

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr"><a href="https://twitter.com/DumasRaphael">@DumasRaphael</a> <a href="https://twitter.com/TranspoCamp">@TranspoCamp</a> have an online component</p>&mdash; Josh De La Rosa (@JoshdelaRosa1) <a href="https://twitter.com/JoshdelaRosa1/status/779427353302818818">September 23, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

Now all the notes are online (though we didn't do so well this time), and there's an active Twitter conversation, but is this enough for a remote enthusiast? I thought livestreaming every session would be prohibitive, but in the age of Periscope, is there really much of a barrier? But do we really want livestreaming of sessions? The less formal setting allows for meaningful engagement with people you don't usually get access to, and they often tend to be more open than in other settings. Livestreaming, while enabling more participation, would add a panopticon effect to sessions, which could well ruin what makes Transportation Camp great. Let me know what you think [on Twitter](https://twitter.com/DumasRaphael) or [get in touch](http://www.radumas.info/#contact) 

