---
layout: post
section-type: post
title: "Transportation Camp NYC"
published: false
date: 2017-10-07 12:00:00 -0500
categories: ['conference-recaps']
author: Raphael Dumas
summary: "TranspoCampNYC 17 sessions I attended, ones I wish I had a time-turner to attend, and ones where others took really nice notes"
tags: [ 'transit', 'scenario-planning', 'big-data', 'transportation camp']
thumbnail: map
---

Recap of my recent trip to NYC to attend Transportation Camp NYC, a lovely unconference with about 500 attendees. Here's the [full session board](https://docs.google.com/spreadsheets/d/e/2PACX-1vTEKtPxGJKzL2go1h3s1AG1A0gDR2yCtA68AAiSWs_8gytc8CnhiMSYjShessGcP9Fc669EyM1f-mGG/pubhtml?gid=1321426310&single=true). Follows a discussion of what I attended, what I wish I had a time-turner to attend, and what others took really nice notes from.

## Session 1
Chatted to old friends

## Session 2: Transit Scenario Planning
Led by Anson Stewart & Kate Chanba - [Conveyal](https://conveyal.com)

A demo of different tools for creating and editing transit routes, updating frequency and seeing more or less how that would affect service.

### Tools:
#### Enmodal
[Link](https://app.enmodal.co)
 - Free and open-source
#### Remix
https://remix.com
 - Not free, hard to get for advocates
 - Population & Jobs layers
   * Poverty
   * Minority
   * Other demographic data
 - Very easy to use
 - Good sense of “What it would actually cost to make transit changes”
 - Used for communicating scenarios to the public
#### Conveyal Analysis
[Link](https://www.conveyal.com/analysis)
 - Potential modifications:
   * Reroute
   * frequency
 * Compare accessibility to jobs
   * Based on LODES data
 * Open Source. Conveyal offers a hosted service.
   * Possible to run on a consumer laptop, analysis will be computed slower
   * Installation is a little trickier https://github.com/conveyal/analysis-ui
     - Requires Amazon Web Services S3 buckets
#### Brand New Subway
[link](http://jpwright.net/subway/)

## Session 3: NYT Subway Delays
Presented by Adam Pearce, Graphics Person at the NYT, [@adamrpearce](https://twitter.com/adamrpearce), [GitHub](https://github.com/1wheel)


Adam pulled data from MTA's real-time subway API, as well as went through old performance reports to look at the MTA's current performance during "The Summer of Hell" and compare it to historical trends.
1. Pulled data from the MTA’s GTFS-RT feed.
   - Processed with a node.js package
     * https://github.com/google/gtfs-realtime-bindings
     * https://github.com/1wheel/subway-parse
   - Dealing with missing data
     * If train sits at a station, the expected arrival time doesn’t get updated.
     * If train gets cancelled, feed gets updated with a garbage time
2. Initially hoped to talk to analysts at the MTA, but press office got cold feet. Then NYT burned their political capital with the first SummerOfHell article
3. But MTA had previously published research reports analysing their own data.
4. MTA changed everyone’s developer keys
5. Instead of individual days, ended up publishing aggregate details, demonstrated that MTA could never provide as much service as scheduled

Adam’s data wishlist:
 - Dwell time data
 - Historical data

There was discussion around Adam's dream of comparing similar data feeds for other global subway systems, to further build the political will to improve the MTA. It sounded like a tough project to pitch to a newspaper. But maybe if Transit Center, or some other researchers were interested...


## Session 4: Sources of Big Data for Road Ops (me!)
I led essentially the same session at Transportation Camp Toroonto, but I thought folks in NYC might be interested in learning about the sources of big data we use within Toronto Transportation's Big Data Innovation Team. Thanks to a great pitch card from Lauren, the session was well attended.

<blockquote class="twitter-tweet" data-lang="en"><p lang="en" dir="ltr">No seats left at <a href="https://twitter.com/DumasRaphael?ref_src=twsrc%5Etfw">@DumasRaphael</a> &#39;s session on using City of Toronto&#39;s data for road operations <a href="https://twitter.com/hashtag/TCNYC17?src=hash&amp;ref_src=twsrc%5Etfw">#TCNYC17</a> <a href="https://t.co/qVCqnK7nQG">pic.twitter.com/qVCqnK7nQG</a></p>&mdash; Lauren Tarte (@RacingTheNTrain) <a href="https://twitter.com/RacingTheNTrain/status/921832055931113479?ref_src=twsrc%5Etfw">October 21, 2017</a></blockquote>
<script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

I walked through our [Data Sources](https://github.com/cityoftoronto/bdit_data-sources) Github repo, talking about where we get each data source and how we use it. This had a [sedative effect on some](https://twitter.com/ThePlangineer/status/922240497783058433).

One questioner heavily directed the session. He wanted more specifics relative how our work has led to tranformation in the department. The main point is that transportation policy evaluation can be more rigorous. Manual data collection is so expensive that it is generally of unreliably small sample size. With automatically collected data, and often passively collected data, we don't need to be as deliberate with before-after data collection for small projects. So it's easier to evaluate a multitude of interventions across the city. Our team is still only two years old, and we've only been 3 full-time staff since 2016, so I don't think I have a good concrete example of internal change our philosophy has brought about.

This presentation made me Wonder whether we should have a Github pages Reveal.js presentation for our datasets. It would be a high-level overview of each datasst linkinng to its folder.

People seemed shocked multiple times that [all our code was available on Github](https://github.com/CityofToronto?utf8=%E2%9C%93&q=bdit&type=&language=), guess that's a kind of revolutionary government thing.

## Honourable mentions
Presentations I kind of wish I attended

### New Urbanist Memes for Transit-Oriented Teens
Basically, [the dankest group on Facebook if you're a transit nerd](https://www.facebook.com/groups/whatwouldjanejacobsdo/), the founder and admin Juliet Eldred gave [a presentation](https://docs.google.com/presentation/d/1EOS82aFnN1UPKQGST33Gfo5JKFV1Wt6-vZra2VMwM2o/edit#slide=id.p) at the same time as my talk. It's more than just memes, promise.

### Uphill Battle - Improving Transit From Within a Difficult Political Climate
Led by two MIT Transit Lab alums at the MTA (I think we still outnumbered Transit App employees), this discussion partially talked about how transit employees can lead change from within alrge poltiical organizations like the MTA. Nobody took public notes alas.

### Transport for Cairo - Mapping Informality
Former McGill classmate of mine led a project in Cairo to map the informal transit network and create a public GTFS feed from it. Similar projects in Nairobi and Dhaka have gotten some press. Nobody took notes

### Transit Wiki

### Other sessions with notes
The organizers did not promote the crowdsourced notes angle well enough.
Here they are, in no particular order
 - [Governing Transport for City Regions](https://docs.google.com/document/d/1t5haQkqjxzI9iVHsvfyuX9sg0rjQuxNcQSxBvwnZWEI/edit)
 - [Why Isn't Congestion Pricing More Popular in NYC?](https://docs.google.com/document/d/1I1GvsMzZusnaL1O84Sc4sSY1lFyA6aH6-XrUoK0B7f0/edit)
 - [Welcome to AV Hell](https://docs.google.com/document/d/1dXG3oWcmRKapyGtjDivwcFPNXohIinFyQ31VgL5m1lo/edit)
 - [Use the Fun Theory to Nudge Behavior Change](https://docs.google.com/document/d/1mQVwCentyHsQ58_-etgmMXxlhvd7dtMyTC-7SW6TrpU/edit#heading=h.ew067dnigcmf)

