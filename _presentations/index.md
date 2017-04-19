---
layout: page
title: Presentations
excerpt_separator: ""
---

<div class="articles" markdown="1">
{% for presentation in site.presentations reversed %}
{% unless presentation.title == 'Presentations' %}
**[{{ presentation.title }}]({{ site.baseurl }}{{ presentation.url }})**  
<small class="hidden-s"><i>{{ presentation.desc }}</i></small>  
<small class="hidden-xs">{{ presentation.date | date: "%B %-d, %Y" }}</small>  
<br />
{% endunless %}
{% endfor %}
</div>