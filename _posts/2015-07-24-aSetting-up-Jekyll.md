---
layout: post
section-type: post
title: "Setting Up Jekyll Theme"
tags: [ 'jekyll' ]
---

I got this theme from [Panos Sakkos](https://github.com/PanosSakkos/personal-jekyll-theme) by adding it as a submodule. Note that the `https` url [is required](https://help.github.com/articles/using-submodules-with-pages/)
```
git submodule add https://github.com/PanosSakkos/personal-jekyll-theme.git 
```
Which clones the repository into the `personal-jekyll-theme/` folder. [Unfortunately](http://stackoverflow.com/questions/13364511/can-i-add-a-git-submodule-to-the-existing-projects-root-where-files-are-not-jo) one cannot add a submodule to the root directory of a project. So I added symlinks to the folders in `personal-jekyll-theme/` to the elements I didn't think I was going to update, and then copied the things I thought I would (the `.html`in the root folder, the `_posts` directory...).

And then making sure my computer is properly configured to play nicely with the [gh-pages version of jekyll](https://help.github.com/articles/using-jekyll-with-pages/).

I got rid of the comments sections and the post categories for now...