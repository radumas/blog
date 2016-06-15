---
layout: post
section-type: post
title: "How to Use Heroku to Automate Updating of Your CartoDB Tables"
published: true
date: 2016-06-14 20:00:00 -0500
categories: ['bcu','tutorial']
author: Raphael Dumas
summary: "I spent a while trying to understand how to program to make a webpage mobile-responsive, turns out the solution was easier than I thought"
tags: [ 'bikeways4everybody', 'python','cartodb','heroku']
thumbnail: database  
---

Bikeways receives data from users, but this data must eventually be processed and aggregated in order to make [maps](https://bcu.cartodb.com/viz/4aa385ec-f83d-11e5-9dc1-0e3ff518bd15/public_map). After figuring out how to do that [processing](https://github.com/radumas/bikeways4everybody/tree/gh-pages/data-analysis#data-analysis), there were two possibilities for how to automate it (so I didn't have to periodically run a processing query):

1. Set a [Trigger](https://www.postgresql.org/docs/9.1/static/sql-createtrigger.html) that gets triggered everytime a new piece of data gets added in order to properly process each new element to the aggregate table.
2. ~~Schedule the bulk update using [pgAGent](https://www.pgadmin.org/include/doc.php?docset=1.4&docpage=pgagent.html)~~ (unavailable in CartoDB)
3. Schedule the bulk update using an external process.

I pondered #1 a fair bit. But I felt the data-processing sql was [complex enough](https://github.com/radumas/bikeways4everybody/tree/gh-pages/data-analysis#data-analysis), that atomizing the procedure so that it could run on individual inserts wasn't worth the development effort. Especially since, as of this writing, the raw input table has just 700 rows, which is trivial to process repeatedly in bulk (thanks CartoDB!). So I combined all the processing into [one query](https://github.com/radumas/bikeways4everybody/tree/gh-pages/data-analysis#automating-processing) that I exposed to the [CartoDB API](https://docs.cartodb.com/cartodb-platform/sql-api/making-calls/). If I had a linux server, I could use a [cron job ](https://en.wikipedia.org/wiki/Cron) that would run something like `wget https://username.cartodb.com/api/v2/sql?q=SELECT process_data()`. But I didn't. So I asked the friendly folks at [Code for Boston](http://www.codeforboston.org/) and was suggested to set up a script on Heroku that runs occasionally using the [Heroku Scheduler](https://devcenter.heroku.com/articles/scheduler).

Because I'm trying to improve my Python skills, I wrote the script to ping the API in Python:
```python
import requests
username = 'raphaeld'
url = 'https://' + username + '.cartodb.com/api/v2/sql'
sql = {'q':'SELECT process_data()'}
res = requests.get(url, data=sql)
print(res.text)
```
The script is broken up into its component parts for legibility. First the `url` is constructed, next the `sql` query, and the two are combined into an HTTP request that pings the CartoDB API and returns a response. Finally the response text is printed to stdout.

### Heroku set up
1. Get a Heroku account and provide your credit card details (don't worry, this should all be free), create a new app.

2. Install the [Heroku Toolbelt](https://toolbelt.heroku.com/)

3. Create process-data-call.py from the python code above in a new folder. **Don't forget to change your username ;)**

4. Login to heroku `heroku login` and then initialize the git repositroy with `git init` and add the heroku remote with `heroku git:remote -a process-data`

5. Add a `requirements.txt` file to the folder with the contents `requests==2.9.1`. This tells the Python package manager to include the `requests` library, which handles the HTTP requests

6. Add the file `Procfile` to the folder with the contents `web: python process-data-call.py`. [This tells Heroku](https://devcenter.heroku.com/articles/getting-started-with-python#define-a-procfile) what command to run at the root folder of your "application" when it runs it. 

7. Tell git to track all the files in your folder with `git add .`, commit them with `git commit -m "Initial commit` and then push them to heroku with `git push heroku master`

8. On the dashboard for your app, add the `Heroku scheduler` app and then create a new task with task `python process-data-call.py`. I set the schedule to be every day.

9. Monitor progress with [`heroku logs --tail`](https://devcenter.heroku.com/articles/getting-started-with-python#view-logs)