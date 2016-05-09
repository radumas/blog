---
layout: page
title: Categories
excerpt_separator: ""
---

<div class="articles" markdown="1">
{% for category in site.category %}
{% unless category.title == 'Categories' %}
## {{category.title}} 
{{ category.output }}

{% for post in site.posts %}
	{% if post.categories contains category.cat %}


 - [{{ post.title }}]({{ post.url }})
<small class="hidden-xs">{{ post.date | date: "%B %-d, %Y" }}</small>  
<br />
{% endif %}
{% endfor %}
{% endunless %}
{% endfor %}
</div>