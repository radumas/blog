---
layout: categories
title: Categories
excerpt_separator: ""
---

<div class="content align-left" markdown="1">
{% for category in site.category %}
{% unless category.title == 'Categories' %}
## {{category.title}}
{: style="text-align:center;" }
{{ category.content }}

{% for post in site.posts %}
	{% if post.categories contains category.cat %}
&nbsp; &#8226; &nbsp; [{{ post.title }}]({{ post.url }})
<small class="hidden-xs">{{ post.date | date: "%B %-d, %Y" }}</small>  
{% endif %}
{% endfor %}
{% endunless %}
{% endfor %}
</div>