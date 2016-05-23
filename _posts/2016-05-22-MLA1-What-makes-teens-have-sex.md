---
layout: post
section-type: post
title: "ML Assignment 1: What makes teens have sex?"
published: true
date: 2016-05-22 22:00:00 -0500
categories: ['python-data-science','tutorial']
author: Raphael Dumas
summary: "My first assignment in Wesleyan's Machine Learning course: exploring decision trees"
tags: [ 'python', 'machinelearning']
thumbnail: venus-mars  
---

I was browsing the questionnaire topics and noticed that one was entitled, *Motivations to Engage in Risky Behaviors*. After opening that `PDF` I then realized that the questionnaire considered "sexual intercourse before marriage" as a risky behaviour, but I decided to explore further anyways. Yes, the questionnaire was very heteronormative.

!['My tree']({{ site.baseurl}}/img/2016-05-22/index2.png)

Apologies about the size of the tree, you can right-click to view it in its own page and zoom and pan around. I trimmed it down to just 3 variables that seemed the most important following analysis of previous trees. The variables included were to what degree the respondent would feel guilty if they had sex (H1MO3), to what degree their mother would be upset if they had sex (H1M04), and to what degree they would "embarrass themselves" if they were/they got someone pregnant (H1MO10). The respondents are roughly evenly split between not having had sex (0) or having had it (1) (H1CO1).

For those who agree or strongly agree they would feel guilty if they had sex, 72% hadn't had sex. Further branching by those who agree, or strongly agree they would be embarrassed by an accidental pregnancy 78% hadn't had sex. And then of those who strongly agreed that their mothers would be upset, 84% hadn't had sex.

Those who were uncertain about feeling guilty were roughly evenly split in who had had sex. For those who felt guilt-free about sex, 71% had had sex. Of these who were ambiguous or agreed they would be embarrassed in case of accidental pregnancy, 65% had had sex. On the other hand, 81% of those who wouldn't feel embarrassed by accidental pregnancy had had sex.

The model classified 66% of the sample correctly, correctly identifying 64.5% of those who had not had sex, and 67.7% of those who had.

The code to produce this model is as follows:

{% highlight python %}
from pandas import Series, DataFrame
import pandas as pd
import numpy as np
import os
import matplotlib.pylab as plt
from sklearn.cross_validation import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import classification_report
import sklearn.metrics

columns = ["H1CO1","H1MO1","H1MO2","H1MO3","H1MO4","H1MO5","H1MO8","H1MO9","H1MO10","H1MO11","H1MO13","BIO_SEX"]

AH_data = pd.read_csv("../../data/addhealth_pds.csv", usecols=columns)
#Dropping those who haven't had sex
filter_answers = [6,8,9]

data_filter1 = AH_data.dropna()[~AH_data['H1CO1'].isin(filter_answers)]
#And those who were <15, had married, or were missing mothers
data_clean = data_filter1[~data_filter1['H1MO4'].isin([7])]

predictors2 = data_clean[["H1MO3","H1MO4","H1MO10"]]
targets = data_clean.H1CO1

pred_train, pred_test, tar_train, tar_test  =   train_test_split(predictors2, targets, test_size=.4)
classifier=classifier.fit(pred_train,tar_train)
predictions=classifier.predict(pred_test)
sklearn.metrics.confusion_matrix(tar_test,predictions)

sklearn.metrics.accuracy_score(tar_test, predictions)

out = StringIO()
tree.export_graphviz(classifier, out_file=out)
graph=pydotplus.graph_from_dot_data(out.getvalue())
Image(graph.create_png())
{% endhighlight %}