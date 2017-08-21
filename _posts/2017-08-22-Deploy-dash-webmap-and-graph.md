---
layout: post
section-type: post
title: "Dash Plotly Map + Graph Part2: Linking Map & Graph and deploying to Heroku"
published: false
date: 2017-08-21 15:00:00 -0500
categories: ['tutorial']
author: Raphael Dumas
summary: "I try to make a demo interactive web map linked to a graph using plotly and the python web server Dash."
tags: [ 'dash','plotly', 'python','visualization']
thumbnail: map  
---

## Linking Map Data to Updating Graph Data


# Deploying to Heroku

## Transferring Data
It may be possible to store data to be served in flat files within the project folder (projects can be up to 500MB in size, including dependencies), this has not yet been tested. 

### Heroku Postgres

Data can also be stored in a PostgreSQL database (max 10k total rows in the free tier). [Importing data](https://devcenter.heroku.com/articles/heroku-postgres-import-export#import) is a surprising pain. It's untested whether `pg_restore` or `psql \COPY` work, you can use [`heroku pg:backups:restore`](https://devcenter.heroku.com/articles/heroku-postgres-import-export#import-to-heroku-postgres), but your dump file has to be hosted in the cloud somewhere... 

Raphael used the following procedure:
1. Dump the a restore DDL and insert scripts using `pg_dump -d bigdata -h host.ip -t schema.table -F p -O --inserts > dump.sql`
2. Restore to heroku using `psql postgres://heroku:postgres:5432/URI -f dump.sql`. (These two commands can probably be piped together).
3. For geographic data use `pgsql2shp -h host.ip bigdata schema.table`, this dumps a `shp` "file" in the working directory.
4. Upload geographic data by piping `shp2pgsql` to `psql` like `shp2pgsql -D -I -s 4326 -S table.shp schema.table | psql postgres://heroku:postgres:5432/URI`. Because geographic data previously uploaded to the RDS via `shp2pgsql` involves sequences... **Note:** some `int` data was converted to `double precision` and had to be converted back.
