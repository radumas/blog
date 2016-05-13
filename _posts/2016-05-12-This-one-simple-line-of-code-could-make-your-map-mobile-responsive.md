---
layout: post
section-type: post
title: "This one simple line of code could make your map mobile-responsive"
published: true
date: 2016-05-12 16:55:00 -0500
categories: ['bcu','tutorial']
author: Raphael Dumas
summary: "I spent a while trying to understand how to program to make a webpage mobile-responsive, turns out the solution was easier than I thought"
tags: [ 'bikeways4everybody', 'leaflet','mobile-responsive']
thumbnail: mobile  
---

*Because making Bikeways mobile responsive ended up being easier than I thought, I decided to write a post so that someone else might (maybe) save themselves a lot of searching.*

## Desktop Launch

I launched the [Bikeways for Everybody map]({% post_url 2016-02-22-Draw-where-you-want-better-bike-infra %}) in February with the design having been primarily aimed at a desktop browser. I rather erroneously thought that, through the magic of javascript, all of the plugins I used would look nice when I loaded the map on my phone.  
!['Example screenshot on Nexus']({{ site.baseurl }}/img/2016-05-12/nexus_screenshot.png)

I was wrong.  

So I focussed on responding to feedback on the desktop experience while attending Code for Boston hacknights hoping to meet someone who was an expert in designing websites/maps for mobile. However, as I used it, biked around, and thought of different constituencies to promote the map to, I realized how valuable mobile would be. 

## A Pixel is not Actually a Pixel

It was only this past Tuesday that I finally got the chance to look at this problem. I had gotten pointed to [A Pixel is not a Pixel](http://www.quirksmode.org/blog/archives/2010/04/a_pixel_is_not.html) a number of times, but had been confused by the fact that my phone's screen has a better resolution than my laptop's. I kept hearing a suggestion to use a `<meta name="viewport" ...>` tag, but didn't want to push a change to the map to test it on my phone. What's confusing about that article is that handheld device browsers apparently state a `device-width` in pixels that aren't real pixels. So a `1080p` display might actually be `320 px` wide. 

## How did I eventually figure this out? 

Well I started styling the icons in the top left in `em`s, which represents the pixels in an `m` character in the browser's font and started testing the look on my phone. I couldn't manage to pan around or zoom in on the map though on my phone, so I looked into what I could do to enable that and [found this on stackexchange](http://stackoverflow.com/a/21245567/4047679):


```
//This however does work for me:
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
```

There was that `<meta name="viewport" ...>` tag again. So I decided to test that out. Fortunately I had separated out the Bikeways project into two live sites: one on my personal github page, and the other on an account I created for Bike Union, so that in the future someone could modify the Union's instance of Bikeways without *necessarily* needing me. Anyways, I inserted that one line of code into the `header` on the test site and... Things Just Worked&#8482;  (mostly).

!['Things Working']({{ site.baseurl }}/img/2016-05-12/nexus_mobile_screenshot.jpeg)

I'm now on to learning how to improve the UX on mobile. For example, the handy tooltips don't appear anymore on mouse hover, because there's no mouse. If you want to help in this phase of coding, check out [these issues](https://github.com/radumas/bikeways4everybody/milestones/High%20DPI-responsive)

### Lessons learned:
1. If a solution is one line of code, Just Do It&#8482;
2. Having two separate live sites, one real, one for testing, helped make the above point easier
3. A pixel is apparently not a pixel on mobile

### Some Further Resources
 - [This leaflet tutorial](http://leafletjs.com/examples/mobile.html) to make a map for mobile. 
