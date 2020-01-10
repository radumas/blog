---
layout: post
section-type: post
title: "How to Use Default Keyword Arguments to Develop Iteratively in Python"
published: false
date: 2019-12-29 9:30:00 -0500
categories: ['tutorial']
author: Raphael Dumas
summary: "How to iteratively develop an interactive dashboard using Dash"
tags: [ 'dash','plotly', 'python','visualization']
thumbnail: line-chart  
---

_I kind of sat on this post for 2 years... but here it is at last!_

Before I learned about Agile--or iterative and incremental project management--I learned about [the Spiral Model](https://en.wikipedia.org/wiki/Spiral_model). While the focus of Agile is on larger chunks of work, you should also apply similar principles of incrementalism with frequent testing to day-to-day tasks. Benefits:

1. **Reduced risk**: Testing each addition means you don't end up with a bunch of code and a number of bugs/one real puzzler
2. **Increased reward**: You see the results of your coding faster and more frequently. Yay for immediate satisfaction. 

## Dash

For this tutorial I'll be using Dash to illustrate the concept of development. Dash is a Python visualization library that merges the [Plotly.js web visualization frontend](https://plot.ly/javascript/) with [Flask](http://flask.pocoo.org/), a popular lightweight Python webserver, as a back-end. It allows you to create complex interactive data visualizations entirely in Python which you can then host on platforms like Heroku.
 
Aly wrote [this great post](https://dev.to/alysivji/interactive-web-based-dashboards-in-python-5hf) explaining what Dash is and how to structure your Dash code using the Model-View-Controller paradigm. I'm going to reuse his code to explain how to can develop your own dashboards cautiously and iteratively. 

### Setup

1. You'll need a working version of [Python](https://www.python.org/downloads/), it must be at least 3.6. 
2. Pipenv is an incredible tool for managing Python virtual environments, it prevents packages from one project from colliding with those of another. [Get it](https://docs.pipenv.org/)
3. Create a project folder. Download the [`requirements.txt`]( https://raw.githubusercontent.com/alysivji/historical-results-dashboard/master/requirements.txt) and the [`soccer-stats.db`](https://github.com/alysivji/historical-results-dashboard/raw/master/soccer-stats.db) [SQLite](https://sqlite.org/) database file into that folder.
4. Create a virtual environment and install the necessary packages by opening a terminal in your project folder and running `pipenv install`.
5. Activate your virtual environment with `pipenv shell`

### ARGS!
This is the "standard" Python function signature:

```python
def my_func(arg1, arg2, *args, **kwargs):
    #...
```
 
 - `arg1` and `arg2` are _positional arguments_
 - `args` is a tuple of arbitrary length for extra parameters passed
 - `kwargs` is a dictionary of _keyword arguments_, passed like so `my_func(foo='bar', param='baz')`

Not every function has this complete form. Many have a mix of positional and keyword arguments. The use of `*args` is for functions that should accept an arbitrary number of arguments, like `sum()`. Some further rules for argument types:

1. Python treats positional arguments as **mandatory** and keyword arguments as _optional_
2. Positional arguments must be **before** keyword ones.  

From the [Python documentation](https://docs.python.org/dev/tutorial/controlflow.html#more-on-defining-functions), you can define _default values_ for keyword arguments. E.g.:
```python
def say_happy_birthday(name='Jane Doe', age=1):
    #...
```

Common practice for keyword-only arguments is to define them as `None`, since the function is expecting a value to be passed. But some functions might have a parameter that is only rarely changed or passed, and these should arguments should be defined with a **sensible default**. A warning:

>**Important warning:** The default value is evaluated only once. This makes a difference when the default is a mutable object such as a list, dictionary, or instances of most classes.   

### Let's Get Started

We're trying to recreate the interactive dashboard Aly wrote about in his blog post. It lets you choose visualize historical soccer match data. You can select a division, a team, and a season, and the results will look like:

!['Mockup of the dashboard with a Results header, the three interactive components: Division, Season, Team, a table of results, a table summarizing Wins etc. and a chart of points as the season progresses'](https://alysivji.github.io/images/11-20/11_dash_app_layout.png)

We'll create the table first, since that's easiest. Below is our template from which we'll build on. Note that `fetch_data` takes extra keyword arguments in `params` , which it passes as a variable to `params` in `pd.read_sql()`. This is to prevent [SQL Injection](https://stackoverflow.com/questions/332365/how-does-the-sql-injection-from-the-bobby-tables-xkcd-comic-work) which could enable malicious pranksters to delete your data or insert fake data.

```python
# standard library
import os

# dash libs
import dash
from dash.dependencies import Input, Output
import dash_core_components as dcc
import dash_html_components as html
import plotly.figure_factory as ff
import plotly.graph_objs as go

# pydata stack
import pandas as pd
from sqlalchemy import create_engine

# set params
conn = create_engine(os.environ['DB_URI'])


###########################
# Data Manipulation / Model
###########################

def fetch_data(q, **params):
    '''Fetch data from database for given query and params
    Having the database connection handle query parameters instead of 
    doing string manipulation in Python.
    '''

    result = pd.read_sql(
        sql=q,
        con=conn,
        params=params
    )
    return result


#########################
# Dashboard Layout / View
#########################

# Set up Dashboard and create layout
app = dash.Dash()
app.css.append_css({
    "external_url": "https://codepen.io/chriddyp/pen/bWLwgP.css"
})

app.layout = html.Div([

    # Page Header
    html.Div([
        html.H1('Project Header')
    ]),

])


#############################################
# Interaction Between Components / Controller
#############################################



# start Flask server
if __name__ == '__main__':
    app.run_server(
        debug=True,
        host='0.0.0.0',
        port=8050
    )
```

### Building the table

#### Model

In order to construct the table, we need to fetch match results. The following function gets match results for a given division, season, and team. It defaults to the English Premier League's (EPL) 2016-2017 season for Manchester United.  Note the use of parametrization in the `results_query` with `:division`, `:season`, and `:team` to be filled by the variables of the same names in `fetch_data`.

```python
def get_match_results(division='EPL', season='2016-2017', team='Man United'):
    '''Returns match results for the selected prompts. 
	Defaults to Manchester United, in the EPL for the 2016-2017 season'''

    results_query = '''
        SELECT date, team, opponent, goals, goals_opp, result, points
        FROM results
        WHERE division= :division
        AND season= :season
        AND team= :team
        ORDER BY date ASC
        '''
    match_results = fetch_data(results_query,
                               division=division,
                               season=season,
                               team=team)
    return match_results
```

Put this code in your model section.

#### View

The following `generate_table` function generates ten rows from the provided dataframe

```python
def generate_table(dataframe, max_rows=10):
    '''Given dataframe, return template generated using Dash components
    '''
    header = [html.Tr([html.Th(col) for col in dataframe.columns])]
    body = [html.Tr([
                     html.Td(dataframe.iloc[i][col]) for col in dataframe.columns
                    ]) for i in range(min(len(dataframe), max_rows))]
    return header + body
```

Since we aren't adding any controllers yet, we'll want to load the table in the initial `app.layout`. The new `app.layout` becomes:

```python
app.layout = html.Div([

    # Page Header
    html.Div([
        html.H1('Soccer Results Viewer')
    ]),
    # Match Results Grid
    html.Div([

        # Match Results Table
        html.Div(
            html.Table(id='match-results',
		               children=generate_table(get_match_results(), max_rows=50)),
            className='six columns'
        )])
    ])
```

Note the line generating the initial table: `children=generate_table(get_match_results())`. The output of the callback we will eventually create targets the `match-results` table's `children` property. But here we call `generate_table()` and the dataframe we pass it is the default result for `get_match_results()`, the match results for Manchester United in the English Premier League for 2016-2017:

Put those two code blocks in your **View**, be sure to replace the `app.layout` with the new layout.

If we run this with `python app.py` we get the following:

![]({{ site.baseurl }}/img/2017-12-30/1_table_generated.png)

### Adding another display

Next we'll add the graph showing the accumulation of points over the season.

#### Model

We'll add the `draw_season_points_graph()` function to the data model, which ingests a match results dataframe created by the `get_match_results()` function defined above, calculates the cumulative sum of the points over time, and then creates a plotly Figure of the points summary over time. 

```python
def draw_season_points_graph(results=None):
    if results is None:
	    return []
    dates = results['date']
    points = results['points'].cumsum()

    figure = go.Figure(
        data=[
            go.Scatter(x=dates, y=points, mode='lines+markers')
        ],
        layout=go.Layout(
            title='Points Accumulation',
            showlegend=False
        )
    )

    return figure
```

#### View 

To the `app.layout` we'll add another six column div to the right and place the graph there, and again call our model function to generate a graph with our default team, season, and division.
```python
# Season Graph
        html.Div([
            # graph
            dcc.Graph(id='season-graph',
		              figure=draw_season_points_graph(get_match_results()))
            # style={},

        ], className='six columns')
```

![]({{ site.baseurl }}/img/2017-12-30/2_point_accumulation.png)

_Sweet instant gratification._

### Adding Interactivity 1: Selecting a team

While there are a couple different ways to approach tackling enabling selection of division, team, season, I've decided to first implement team selection for the 2016-2017 season of the English Premier League.

#### Model

The query to get teams uses the `DISTINCT` operator to only return unique team names.

```python
def get_teams(division='EPL', season='2016-2017'):
    '''Returns all teams playing in the division in the season'''

    teams_query = '''
        SELECT DISTINCT team
        FROM results
        WHERE division= :division
        AND season= :season
        '''
    
    teams = fetch_data(teams_query,
					   division=division,
					   season=season)
    teams = list(teams['team'].sort_values(ascending=True))
    return teams
```

#### View

For ease of code reuse I'm going to add the following helper function that generates options for a dropdown list from a Python list of options:

```python
def generate_options(options_list):
    '''Generate a list of {'label': option, 'value': option} from a list of options
    '''
    return [{'label': option, 'value': option} for option in options_list]
```

And we'll use this function to generate the default options for our first dropdown menu:

```python 
app.layout = html.Div([

    # Page Header
    html.Div([
        html.H1('Soccer Results Viewer')
    ]),

    # Dropdown Grid
    html.Div([
        html.Div([
            # Select Team Dropdown
            html.Div([
                html.Div('Select Team', className='three columns'),
                html.Div(dcc.Dropdown(id='team-selector',
		                 options=generate_options(get_teams()),
		                 value='Man United'),
                         className='nine columns')
            ])
        ], className='six columns'), 
        #Empty Div
        html.Div(className='six columns'),
        ], className='twelve columns'),
        #and so on...
```

You can even test now to see if the Dropdown displays correctly the team options. Now to add a callback.

#### Controller

The Controller portion mediates between user interaction, to get data from the Model, and send it back to the View. In Dash this is done through [**Callback Functions**](https://plot.ly/dash/getting-started-part-2). A basic callback function: 

 1. listens for one or more number of inputs, which it takes as arguments to a function
 2. communicates those inputs to the model, typically to filter data based on new inputs (like our example),
 3. and then sends the new output to **one component** to update that component's property.

So **each component** needs its own callback function, and only one callback function (it's a feature of Dash). For the results table:

```python
# Load Match results
@app.callback(
    Output(component_id='match-results', component_property='children'),
    [
        Input(component_id='team-selector', component_property='value')
    ]
)
def load_match_results(team):
    results = get_match_results(team=team)
    return generate_table(results, max_rows=50)
```

This `load_match_results()` function's signature will get expanded as we add more `Input`s, but for now we just pass the value from the `team-selector` to our `get_match_results()` function as a keyword argument. We don't need to change the `get_match_results()` function, because it's already filtering based on the three variables we'll be using in this tutorial: division, team, and season, but for now it's only using _team_ as an input, the division and season are defaults. 

For the results graph:

```python
# Update Season Point Graph
@app.callback(
    Output(component_id='season-graph', component_property='figure'),
    [
        Input(component_id='team-selector', component_property='value')
    ]
)
def load_season_points_graph(team):
    results = get_match_results(team=team)
	figure = draw_season_points_graph(results)
    return figure
```
You'll notice both callback functions look very similar:

1. The `@app.callback()` decorator with the `team-selector` Input, and their respective output
2. Send the value from the Input to a function from the Model to get new data
3. Send the new data to a function in the View to generate a new figure or table
4. Pass the new figure/table to its respective component.

Run that for some more instant gratification.
 
### Adding Interactivity 2: Selecting a season

Now we'll add selecting a season.

#### Model

A slight modification to the `get_teams` function above, we'll make sure seasons are returned in descending order (`ascending=False`).

```python
def get_seasons(division='EPL', team='Man United'):
    '''Returns the seasons for the given division'''

    seasons_query ='''
        SELECT DISTINCT season
        FROM results
        WHERE division= :division
              AND team = :team
        '''
    
    seasons = fetch_data(seasons_query,
					     division=division,
					     team=team)
    seasons = list(seasons['season'].sort_values(ascending=False))
    return seasons
```

#### View

Add the `season-selector` Dropdown to `app.layout`, above the team one. Make sure your brackets are all well balanced.

```python
# Select Season Dropdown
            html.Div([
                html.Div('Select Season', className='three columns'),
                html.Div(dcc.Dropdown(id='season-selector',
						              options=generate_options(get_seasons()),
					                  value='2016-2017')),
                         className='nine columns')
            ]),
```
#### Controller

This part will be relatively simple, we just need to update the two previous callback functions to include the new selector

```python
# Load Match results
@app.callback(
    Output(component_id='match-results', component_property='children'),
    [
        Input(component_id='season-selector', component_property='value'),
        Input(component_id='team-selector', component_property='value')
    ]
)
def load_match_results(season, team):
    results = get_match_results(season=season,
	                            team=team)
    return generate_table(results, max_rows=50)

# Update Season Point Graph
@app.callback(
    Output(component_id='season-graph', component_property='figure'),
    [
        Input(component_id='season-selector', component_property='value'),
        Input(component_id='team-selector', component_property='value')
    ]
)
def load_season_points_graph(season, team):
    results = get_match_results(season=season,
	                            team=team)
	figure = draw_season_points_graph(results)
    return figure
```

And we'll add another function to update the teams selector based on whether they played in that season.

```python
# Load Teams into dropdown
@app.callback(
    Output(component_id='team-selector', component_property='options'),
    [
        Input(component_id='season-selector', component_property='value')
    ]
)
def populate_team_selector(season):
    teams = get_teams(season=season)
    return generate_options(teams)
```

Save, make sure the changes are reloaded, and then play around for some instant gratification.

### Adding Interactivity 3: Selecting a division

Finally the division selector. This one will again affect both the table and the graph, and also change the available options for the other two selectors. Since we've add the `division` keyword argument to our Model functions fetching team and season data, we don't need to futz with those when we add the division model. But we do need to make sure that the selected division gets passed to those functions in the Controller callback functions.

#### Model
Nothing you haven't seen before

```python
def get_divisions():
    '''Returns the list of divisions that are stored in the database'''

    division_query = '''
        SELECT DISTINCT division
        FROM results
        '''
    
    divisions = fetch_data(division_query)
    divisions = list(divisions['division'].sort_values(ascending=True))
    return divisions
```

#### View

Add this to the top of the `#Dropdown Grid` in the `app.layout`. 

```python
# Select Division Dropdown
            html.Div([
                html.Div('Select Division', className='three columns'),
                html.Div(dcc.Dropdown(id='division-selector',
                                      options=generate_options(get_divisions())),
                         className='nine columns')
            ]),
```

#### Controller

As before, add the `division-selector` as an input to the callback functions we've written above, and make sure to pass the `division` variable in those functions down to the respective Model function. 
Add the below callback function to populate the `season-selector` dropdown with the appropriate seasons for that division.

```python
# Load Seasons in Dropdown
@app.callback(
    Output(component_id='season-selector', component_property='options'),
    [
        Input(component_id='division-selector', component_property='value')
    ]
)
def populate_season_selector(division):
    seasons = get_seasons(division)
    return generate_options(seasons)
```

## Go Forth and Spiral Outwards

You should now have the skills to further tinker with this dashboard and, for example, add the season summary table. 