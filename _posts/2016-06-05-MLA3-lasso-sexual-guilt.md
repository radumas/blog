---
layout: post
section-type: post
title: "ML Assignment 3: LASSO-ing the Sexual Guilt Pixie"
published: true
date: 2016-06-05 20:00:00 -0500
categories: ['python-data-science','tutorial']
author: Raphael Duma2
summary: "My 3nd assignment in Wesleyan's Machine Learning course: lasso regression"
tags: [ 'python', 'machinelearning']
thumbnail: venus-mars  
---

## Assignment 3

Continuing to work with the Add Health dataset from the previous two assignments, I decided to examine what factors would predict the degree to which someone would agree with "If you had sexual intercourse, afterward, you would feel guilty". Lasso regression was performed to determine which of the 19 quantitative or binary predictors would be important. 

The data were randomly split into a 70% training set (N=1696) and a 30% test set (N=727). The least angle regression algorithm with k=10 fold cross validation was used to estimate the lasso regression model in the training set, and the model was validated using the test set. The change in the cross validation average (mean) squared error at each step was used to identify the best subset of predictor variables. 

!['MSE']({{ site.baseurl }}img/2016-06-05/mse.png)

Of the 19 variables tested, 16 were retained in the selected model. The top 5 most important coefficients appear in the table below. Now this doesn't determine whether any of these variables are correlated with religious strength. But it does show that guilt comes from a loss of respect from peers, upsetting the mother, and the social stigma around accidental pregnancy. 

|    Coefficient | Var | Question|
|--------------------------|
| 0.410922|      H1MO2  | If you had sexual intercourse, your partner would lose respect for you |
|0.266673  |    H1MO4  |If you had sexual intercourse, it would upset {NAME OF MOTHER}  |
|0.196177   |  H1MO10  | If you got {someone} pregnant, it would be embarrassing|
|-0.085100   |   H1MO1  |If you had sexual intercourse, your friends would respect you more |
|-0.079051    |  H1MO5| If you had sexual intercourse, it would give you a great deal of physical pleasure|

The training data R-square was 0.406 and the test data R-square was 0.461 which implies that the model explains a good amount of variation in sexual guilt while not being overfitted. 

## Code

{% highlight python %}

%matplotlib inline
import pandas as pd
import numpy as np
import matplotlib.pylab as plt
from sklearn.cross_validation import train_test_split
from sklearn.linear_model import LassoLarsCV
import os

#Load the dataset
columns = ["H1MO1","H1MO2","H1MO3","H1MO4","H1MO5","H1MO8","H1MO9","H1MO10","H1MO11","H1MO13","H1MO14","H1RE4","H1RE6","H1BC8","H1BC7","BIO_SEX","H1PF2","H1PF3","H1PF5","H1PF25"]

AH_data = pd.read_csv("../../data/addhealth_pds.csv", usecols=columns)

data_clean = AH_data.dropna()

#Removing refused, legitimate skip, don't know, and NA from variables.
for column in columns:
    data_clean = data_clean[~data_clean[column].isin([6,7,8,9])]

# Data Management
recode1 = {1:1, 2:0}
data_clean['MALE']= data_clean['BIO_SEX'].map(recode1)

#select predictor variables and target variable as separate data sets  
predvar= data_clean[["H1MO1","H1MO2","H1MO4","H1MO5","H1MO8","H1MO9","H1MO10","H1MO11","H1MO13","H1MO14","H1RE4","H1RE6","H1BC8","H1BC7","MALE","H1PF2","H1PF3","H1PF5","H1PF25"]]

target = data_clean.H1MO3
 
# standardize predictors to have mean=0 and sd=1
predictors=predvar.copy()
from sklearn import preprocessing

for column in predvar.columns:
    predictors[column]=preprocessing.scale(predictors[column].astype('float64'))

# split data into train and test sets
pred_train, pred_test, tar_train, tar_test = train_test_split(predictors, target, 
                                                              test_size=.3, random_state=123)

# specify the lasso regression model
model=LassoLarsCV(cv=10, precompute=False).fit(pred_train,tar_train)

# print variable names and regression coefficients
var_imp = pd.DataFrame(data = {'predictors':list(predictors.columns.values),'coefficients':model.coef_})
var_imp['sort'] = var_imp.coefficients.abs()
                   
print(var_imp.sort_values(by='sort', ascending=False))

# plot mean square error for each fold
m_log_alphascv = -np.log10(model.cv_alphas_)
plt.figure()
plt.plot(m_log_alphascv, model.cv_mse_path_, ':')
plt.plot(m_log_alphascv, model.cv_mse_path_.mean(axis=-1), 'k',
         label='Average across the folds', linewidth=2)
plt.axvline(-np.log10(model.alpha_), linestyle='--', color='k',
            label='alpha CV')
plt.legend()
plt.xlabel('-log(alpha)')
plt.ylabel('Mean squared error')
plt.title('Mean squared error on each fold')

# MSE from training and test data
from sklearn.metrics import mean_squared_error
train_error = mean_squared_error(tar_train, model.predict(pred_train))
test_error = mean_squared_error(tar_test, model.predict(pred_test))
print ('training data MSE')
print(train_error)
print ('test data MSE')
print(test_error)

# R-square from training and test data
rsquared_train=model.score(pred_train,tar_train)
rsquared_test=model.score(pred_test,tar_test)
print ('training data R-square')
print(rsquared_train)
print ('test data R-square')
print(rsquared_test)

{% endhighlight %}

## Notes 

LASSO (least absolute shrinkage and selection operator) regression is a shrinkage & variable selection method for linear regression.

### Why Lasso?

Better prediction accuracy if small number of observations relative to number of predictors
Can increase model interpretability.        Simpler model that selects only most important predictors

### Limitations
1. Selection of variables is 100% statistically driven, rather than intuition or human-knowledge
2. If predictors predictors are correlated, one will be arbitrarily selected
3. Estimating p-values is not straightforward
4. Different methods & software can produce different results
5. No guarantee that selected model is not overfitted, nor that it's the "best model"