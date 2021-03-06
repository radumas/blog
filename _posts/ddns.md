---
layout: post
section-type: post
title: "Setting up Dynamic DNS"
date: 2015-07-28 16:06:00
published: false
categories: Tutorials
author: Raphael Dumas
tags: [ 'digital ocean', 'server']
thumbnail: map-marker  
---


Because Digital Ocean bills hourly for running a VPS, and because I didn't really need the webserver to be active at night. I wanted to start putting it to sleep when I wasn't working on it. However this can be a bit of a pain with ssh, since at every new creation Digital Ocean gives the droplet a new IP.

I learned about `ddclient` on [AskUbuntu](http://askubuntu.com/questions/72349/how-to-run-an-ssh-server-without-a-static-ip-address/72353#72353), which is simple to install with `sudo apt-get install ddclient`. But wait before calling that command, since it will bring you automatically to a config. Finding a service that would both register a free domain and provide a free DDNS service wasn't easy. In particular some of those have pretty poor documentation or require installation of a propriety daemon. I eventually found [changeip](https://changeip.com) which is [supported by `ddclient`](http://sourceforge.net/p/ddclient/wiki/protocols/#changeip). I chose a hokey but free domain, and edited the config according to the changeip "protocol". (**note**: `ddclient` complained the `changeip` protocol didn't exist, but communication still worked and I could then ssh happily by calling
```
ssh user@hokey.dns
```