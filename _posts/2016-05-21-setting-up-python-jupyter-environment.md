---
layout: post
section-type: post
title: "Setting up the Python and Jupyter Notebook Environment"
published: true
date: 2016-05-21 16:55:00 -0500
categories: ['python-data-science','tutorial']
author: Raphael Dumas
summary: "Python 3 installation + Jupyter notebook for Ubuntu 16.04 "
tags: [ 'python']
thumbnail: download  
---

Install the essentials with 

```bash
sudo apt-get install build-essential python3-dev python3 python3-pip
```

I'm going to recommend setting up a virtual environment in which to install python packages using `pip`, which is python's package manager (like `apt-get`). The virtual environment is like a sandbox in which your Python version is somewhat insulated from the OS. The reason for this comes from web development, where updates might break things (they shouldn't, but you never know), and I figured this would be a better practice for all Python package management, more details from [here](http://askubuntu.com/a/409595/334823)

>I've made these arguments before but I'll give you the notes:  
 - System updates break everything  
 - Installing apt packages can overwrite pip-installed things  
 - Version conflicts  
 - Distribution upgrades are unpredictable chaos. Seriously. I've lost hair to these.  
 
>I would strongly advocate using virtualenv. It's a massive pain in the wherever to get going but once you've got it set up you have a complete Python environment under your complete control. This does mean more work (you'll have to check things for updates and pip doesn't really help there yet) but you don't have to worry about what Ubuntu's doing.

[Set up virtual environment](https://askubuntu.com/questions/244641/how-to-set-up-and-use-a-virtual-python-environment-in-ubuntu)

{% highlight bash %}
sudo pip3 install virtualenv  
sudo pip3 install virtualenvwrapper  
mkdir python3-courses  
virtualenv python3-courses/  
{% endhighlight %}

**Note:** If you have multiple versions of `virtualenv` installed, you may have to use `virtualenv3-x` instead.

```bash
cd python3-courses/
source bin/activate
python --version
```

This should print `Python 3.X.X`. Now install scientific packages `pip install -U numpy scipy sklearn pandas`. In order to install `matplotlib` I had to install the following external dependency with `sudo apt-get install libfreetype6*`and then `pip install -U matplotlib`.

Also graphviz [has to be installed](https://stackoverflow.com/questions/27666846/pydot-invocationexception-graphvizs-executables-not-found) as an Ubuntu package. `sudo apt-get install graphviz`


Then install the iPython Notebook (renamed to [Jupyter](https://jupyter.readthedocs.io/en/latest/install.html#id4)): `pip install jupyter`. Then fire up a notebook with `jupyter notebook`, this should open a new notebook up in the browser. 

