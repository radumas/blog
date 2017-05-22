---
layout: post
section-type: post
title: "Working with AWS in ChromeOS: Initial Thoughts"
published: true
date: 2017-05-22 16:00:00 -0500
author: Raphael Dumas
summary: "Initial thoughts and links for setting up ChromeOS to do work on a project hosted in AWS"
tags: [ 'chromeos','aws','postgresql']
thumbnail: line-chart  
---

*Note: this is likely going to be edited further once I get more things working*

After starting my job I realized more and more that I don't use my main laptop that much. It's 15", bulky and powerful. Since the [only project](https://github.com/CivicTechTO/ttc_subway_times) I'm working on outside of work is hosted entirely on AWS (including the database), I wondered if I really needed a laptop powerful enough to host the database for the data. So I decided to take purchase a [higher-end Chromebook](https://www.asus.com/us/2-in-1-PCs/ASUS-Chromebook-Flip-C302CA/) and see whether I can make it work in ChromeOS. 

## SSH 
ChromeOS has a very basic terminal you can access with `ctrl + alt + t` however the `ssh` command has been disabled and you're suggested to install the apparently official [`ssh`](https://chrome.google.com/webstore/detail/secure-shell/pnhechapfaindjhompbnflcldabbghjo?utm_source=chrome-app-launcher-search) app.
I generated a new `.pem` file for the Amazon EC2 in the AWS dashboard, downloaded it, and tried to make [`ssh` work with it](http://www.mattburns.co.uk/blog/2012/11/15/connecting-to-ec2-from-chromes-secure-shell-using-only-a-pem-file/). One gigantic hiccough is you need the [corresponding public key to the private key](https://chromium.googlesource.com/apps/libapps/+/master/nassh/doc/FAQ.md#Can-I-connect-using-a-public-key-pair-or-certificate). Why does this ssh app doesn't enable the functionality to generate a public key from the `.pem`? I dunno. Why doesn't AWS let you download the public key for a key-pair? Big mystery.  The comments on this [aforementioned blog post](http://www.mattburns.co.uk/blog/2012/11/15/connecting-to-ec2-from-chromes-secure-shell-using-only-a-pem-file/) are mostly garbage. The one thing that would be effective would be to upload the `.pem` file to this python anywhere site, but you just uploaded your private key to some free site.... goodbye security!   
So I need to either [enable crouton]() or copy the public key from another computer. **Stalemate.**

## PostgreSQL
After changing to the [`Beta` channel](https://www.reddit.com/r/chromeos/comments/63vh3s/asus_chromebook_c302_had_android_app_support_and/dfxkli4/?st=j30m5kk8&sh=bc51a6f7) of ChromeOS releases I was able to install the Google Play Store and therefore access Android apps at resolutions they never thought possible. There seem to be a [number](https://play.google.com/store/search?q=sql+client&c=apps) of [promising sql clients](https://play.google.com/store/apps/details?id=de.mxapplications.sqlclient) in Android (not so much in ChromeOS). 

Alternatively, the future is webapps, and PGAdmin4, the new update to the popular PostgreSQL client/editor, can run on [a server as a webapp](https://www.pgadmin.org/docs/pgadmin4/1.x/server_deployment.html). I'm also considering deploying [JupyterHub](https://jupyterhub.readthedocs.io/en/latest/) on the EC2 so I can create Jupyter Notebooks *in the cloud*. However all this requires serving these web pages on HTTPS otherwise my security will be righteously compromised. I started reading [this post](https://www.mitchcanter.com/lets-encrypt-ssl-amazon-aws/) about adding a Let's Encrypt certificate to an AWS EC2. 

I tried that first one "SQL Client" but password authentication failed, so I'm not sure what's up the passwords I stored in my password manager. As [per above](#ssh) I wasn't able to SSH into the EC2, so I couldn't start any setup of PGAdmin4 as a webserver. Therefore, we've reached another **stalemate.**

## Crouton, developer mode etc. etc.
I haven't found a Git client yet that seems decent in either Android or ChromeOS. But it sounds like what all the cool kids are doing is either enabling Developer Mode or running some Linux flavour through the Crouton project.  Either automatically wipes your local storage, and I realize I have a grand total of 1 day on this computer, but I didn't 100% want to commit to that just yet. I think I'll ultimately install Linux on an SD card so I can keep my local storage as "Vanilla" as possible. Some links relative to this:
 - [Git and Node in a Chromebook, Without Crouton and Linux](http://techintranslation.com/git-python-and-node-on-a-toshiba-chromebook-without-crouton-or-linux/)  
 - [Chromeos-developer-setup](http://afaqurk.github.io/chromeos-developer-setup/)  (so many options!)
 - [How To Install [Crouton] To External Drive (Crouton Wiki)](https://github.com/dnschneid/crouton/wiki/How-To-Install-To-External-Drive)

## Other useful goodies 

Exploring the Chrome Store I discovered two really cool webapps: 
- [StackEdit](https://stackedit.io/), which I used to write this post. It's an awesome side-by-side markdown editor that works in the browser, let's you publish to a variety of places, and has bonuses I really like such as: automate Table of Contents generation, and various flow diagram generation.
- [DevDocs.io](https://DevDocs.io), all the open source documentation in one place! No more Google searches to out of date documentation, you set the version of PostgreSQL and Python that you're using and it searches those docs (and can do so offline!) 