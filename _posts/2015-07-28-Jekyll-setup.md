---
layout: post
section-type: post
title: "Jekyll Setup"
summaries: "Some non-obvious things when setting up Jekyll in gh-pages"
date: 2015-07-28 14:00:00
categories: jekyll
author: Raphael Dumas
tags: [ 'jekyll', 'css' ]
thumbnail: jekyll  
---

There is one rather important thing missing from the otherwise excellent jekyll gh-pages tutorial on [github](https://help.github.com/articles/using-jekyll-with-pages/).

You could __almost__ fork any jekyll gh-pages theme and it would run out of the box, but for two things:  
1. You have to push an initial commit to the gh-pages branch in order to get github to build the page.  
2. For a project page, (as opposed to a user page), if you want the css stylings to appear (a big part of the appeal of jekyll) [you have to edit](http://jekyllrb.com/docs/github-pages/#project-page-url-structure) the `baseurl` variable in `_config.yml` to be the project's name, ex: `baseurl: "/projectname"`. Note the leading `/` but no trailing one.