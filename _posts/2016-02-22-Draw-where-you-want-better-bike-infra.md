---
layout: post
section-type: post
title: "Draw Where you Want Better Bike Infrastructure"
published: true
date: 2016-02-22 16:00:00 -0600
categories: 'bcu'
author: Raphael Dumas
summary: "The Boston Cyclists Union wanted to solicit input where people wanted better biking infrastructe, so I made a crowdsourcing map."
tags: [ 'bikeways4everybody', 'crowdsourcing', 'webmap','leaflet','cartodb']
thumbnail: pencil  
---

_**Note** this post was originally published on [LinkedIn](https://www.linkedin.com/pulse/where-do-you-want-better-biking-infrastructure-boston-raphael-dumas?trk=prof-post) and is here as a [retcon](http://tvtropes.org/pmwiki/pmwiki.php/Main/Retcon)_

As part of my internship with the [Boston Cyclists Union](http://bostoncyclistsunion.org/), I developed an [online crowdsourcing map](http://boston-cyclists-union.github.io/bikeways4everybody) where people who bike and folks who would like to bike can view existing infrastructure and draw in where they would like to see improvements. The front-end is [Leaflet](http://leafletjs.com/) & Javascript with [Mapbox Directions](https://www.mapbox.com/developers/api/directions/) for route drawing and hosted on Github. The backend is [CartoDB](http://cartodb.com/) which is an awesome augmentation of PostGIS/PostgreSQL that gives you a lot for free.

!['Route drawing gif'](https://raw.githubusercontent.com/radumas/bikeways4everybody/gh-pages/img/draw_route.gif)

Since the map was published on the BCU's blog last Wednesday, we've received 350 submissions from 77 distinct individuals.

<blockquote class="twitter-tweet tw-align-center" data-lang="en"><p lang="en" dir="ltr">What 108 submitted points for better <a href="https://twitter.com/hashtag/biking?src=hash">#biking</a> infrastructure look like w <a href="https://twitter.com/cartoDB">@cartoDB</a> <a href="https://twitter.com/hashtag/torquejs?src=hash">#torquejs</a> <a href="https://t.co/xdqdof3kvk">https://t.co/xdqdof3kvk</a> <a href="https://t.co/5iVXpANKy9">pic.twitter.com/5iVXpANKy9</a></p>&mdash; Raphael Dumas (@DumasRaphael) <a href="https://twitter.com/DumasRaphael/status/700786206867480576">February 19, 2016</a></blockquote>
<script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>

As we get more data, I'll be analyzing submissions using CartoDB's PostGIS tools to find which routes have the most submissions.

Want to participate? You can draw your own routes in here.

Want to bring this tool to your area? [Get in touch with me](http://radumas.info/#contact). The code is open-source on [github](https://github.com/radumas/bikeways4everybody) and everything is freely hosted.
