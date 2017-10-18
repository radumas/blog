---
layout: post
section-type: post
title: "Dash Plotly Map + Graph Part2: Linking Map & Graph and deploying to Heroku"
published: true
date: 2017-10-17 9:30:00 -0500
categories: ['tutorial']
author: Raphael Dumas
summary: "I continue trying to make a demo interactive web map linked to a graph using plotly and the python web server Dash."
tags: [ 'dash','plotly', 'python','visualization', 'heroku', 'postgresql']
thumbnail: map  
---

This is a follow-up post to [a previous post](blog/{% post_url 2017-08-10-codeday %}) where I started developing a Dash application where a user could select links on a map and that would select the data to display in a plot. That post was more or less written in a day, and I didn't get to linking the map and graph, so this post explains how I developed that, and then how I deployed [the app](https://mighty-savannah-97969.herokuapp.com/) to Heroku. 

# Linking Map Data to Updating Graph Data

In order to link the lines on the map to the data for the plot, we need to have a field that links the two together. The [Dash Getting Started Guide](https://plot.ly/dash/getting-started-part-2) shows how to use `@app.callback(Output(), [Input()])` to decorate functions in order to provide interactivity by linking different elements of the app. In order to determine what data a map provided in its callback function I set up something similar to this [basic interactivity example](https://plot.ly/dash/getting-started-part-2#basic-interactions):
```python
@app.callback(
    Output('click-data', 'children'),
    [Input('map', 'clickData')])
def display_click_data(clickData):
    return json.dumps(clickData, indent=2)
```
Which produced the following output:
```
{
  "points": [
    {
      "lat": 43.6464730064524,
      "curveNumber": 2,
      "lon": -79.4037543398975,
      "pointNumber": 28,
      "customdata": 'w'
    }
   ]
 } 
```
Initially I didn't know how `curveNumber` was generated, so I thought to use `customData`, which is a parameter you can set when creating the lines. However it led to some interesting bugfixing, because `customData` is designed to assign data to each **point** create on the `scatterMapbox` it assumes what is being passed to it is a list, which strings are in Python. I eventually figured out [a Pythonic workaround](https://github.com/plotly/plotly.py/issues/818), but wasn't pleased at the idea of duplicating an id for each point of every line. 

## Using curveNumber

Chris [explained the origin](https://community.plot.ly/t/linking-scattermapbox-lines-to-other-data-with-callbacks/5449/2?u=rad) of the `curveNumber` id, that it is an index created in order of line creation. So when iterating over the geometries to create lines, I create a crossover table:
```python
crossover_df = pandas.DataFrame(columns=['segment_id',
                                         'curveNumber',
                                         'segment_name'])

for curve_number, row in enumerate(map_data.itertuples()):
    geojson = json.loads(row.geojson)
    lats, lons = get_lat_lon(geojson)
    segments.append(go.Scattermapbox(
        ...
    ))
    crossover_df = crossover_df.append({'segment_id':row.segment_id,
                                        'curveNumber':curve_number,
                                        'segment_name':row.segment_name},
                                       ignore_index=True)
```
And then create the `update_graph()` callback function that uses the `curveNumber` to find the right `segment_id` and then filter the data dataframe by `segment_id`. This filtered dataframe is passed to the plotly Scatter graph. **Pitfall alert**, note that `data = [stuff]`, if the data passed to the figure is not an array, your graph will [**fail silently**](https://github.com/plotly/dash/issues/113)
```python
@app.callback(
    Output('travel-time-graph', 'figure'),
    [Input('bluetooth-map', 'clickData')])
def update_graph(segment):
    row = crossover_df[crossover_df['curveNumber']==segment['points'][0]['curveNumber']]
    segment_id = row.segment_id.iloc[0]
    filtered_data = weekday_avg[weekday_avg['segment_id'] ==  segment_id]
    title = crossover_df[crossover_df['segment_id']==segment_id]['segment_name'].iloc[0]
    data = [go.Scatter(x=filtered_data['Time'],
                   y=filtered_data['avg'],
                   mode='lines')]
    layout = dict(title = 'Average Weekday Travel Times <br>' +title,
                  xaxis = dict(title="Time of Day"),
                  yaxis = dict(title="Travel Time (s)"))
    return {'data':data, 'layout':layout}
```

# Deploying to Heroku

Heroku is a handy Platform as a Service provider, which allows you to run simple-to-complex python web-apps for free on their infrastructure. The [Dash Deployment Guide](https://plot.ly/dash/deployment) includes instructions on passing secret keys to the app. (this sentence is a month old and was left hanging, I'm still a little puzzled by what I was trying to communicate)

## Transferring Data
Since the data was hosted in a local database, we need to send it up to the cloud. It may be possible to store data to be served in flat files within the project folder (projects can be up to 500MB in size, including dependencies), this has not yet been tested. Instead I used Heroku Postgres:

### Heroku Postgres

Data can also be stored in a PostgreSQL database (max 10k total rows in the free tier). [Importing data](https://devcenter.heroku.com/articles/heroku-postgres-import-export#import) is a surprising pain. It's untested whether `pg_restore` or `psql \COPY` work, you can use [`heroku pg:backups:restore`](https://devcenter.heroku.com/articles/heroku-postgres-import-export#import-to-heroku-postgres), but your dump file has to be hosted in the cloud somewhere... 

I used the following procedure:
1. Dump the DDL and insert scripts using `pg_dump -d bigdata -h host.ip -t schema.table -F p -O --inserts > dump.sql`
2. Restore to heroku using `psql postgres://heroku:postgres:5432/URI -f dump.sql`. (These two commands can probably be piped together).
3. For geographic data use `pgsql2shp -h host.ip bigdata schema.table`, this dumps a `shp` "file" in the working directory.
4. Upload geographic data by piping `shp2pgsql` to `psql` like `shp2pgsql -D -I -s 4326 -S table.shp schema.table | psql postgres://heroku:postgres:5432/URI`. Because geographic data previously uploaded to the RDS via `shp2pgsql` involves sequences... **Note:** all `int` data was converted to `double precision` and had to be converted back.

### Connecting to Heroku Postgres
Heroku has a `DATABASE_URL` environment variable that plays nicely with `psycopg2`. I set up the following logic so that the application would connect to the database whether it was running locally or on Heroku, all without revealing credentials!

```python
from psycopg2 import connect

database_url = os.getenv("DATABASE_URL")
if database_url is not None:
    con = connect(database_url)
else:
    import configparser
    CONFIG = configparser.ConfigParser()
    CONFIG.read('../db.cfg')
    dbset = CONFIG['DBSETTINGS']
    con = connect(**dbset)
```


## Adding other necessary files

1. Create a `requirements.txt` file with `pip freeze > requirements.txt`. If you're not using a virtual environment, or if you virtualenv has gotten a little bloated, open this file up and delete every package that isn't listed in the imports of your python file.
2. Create a `Procfile` and put `web: gunicorn app:server` in it.

## Create a heroku app
1. Login to heroku and create a new app
2. Connecting that app to your Github repo is really the least painful technique. Do that if you can. 
3. If you want to expend more effort. Check out the [Getting Started with Python Instructions](https://devcenter.heroku.com/articles/getting-started-with-python#introduction) 

# Summary of Hiccoughs

1. `data = [data]` in Dash
2. transferring data to Heroku Postgres\
3. `int`s turn into `double`s when transforming postgis data data to shapefiles.
3. properly labelling lines in Dash. 

# Results

Check out the full page dashboard [here](https://mighty-savannah-97969.herokuapp.com)
