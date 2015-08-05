---
layout: post
section-type: post
title: "Updating a Leaflet map Made with QGIS2web"
summary: "Editing the code for a map created with the qgis2web plugin in order to improve the visuals."
date: 2015-07-31 16:00:00
categories: Mapping
author: Raphael Dumas
tags: [ 'QGIS', 'leaflet', 'QGIS2web' ]
thumbnail: map-marker  
---

In a [previous post]({{ site.baseurl }}{% post_url 2015-07-24-qgisweb %}) I created a Leaflet webmap using QGIS and the qgis2web plugin. I had a list of modifications that I wanted to perform on the map after creating the code using that plugin. 

 
> Some further things I would like to work on with this map:  
> 1. Displaying the legend in two columns  
> 2. Disabling scroll zoom [without clicking on the map](http://gis.stackexchange.com/questions/111887/leaflet-mouse-wheel-zoom-only-after-click-on-map)  
> 3. Restricting zoom levels  
> 4. I want to figure out how to display a table of the data as well, and then maybe do some linking and brushing to highlight, for example, the stations with the oldest average population.  

# Disabling Scroll Zoom 

Previously, if scrolling down in the middle of the page, you would get the behaviour in the map below:

<div class = "leaflet-map">
    <iframe src="{{ site.baseurl }}/maps/transit_working_age/index.html" height="675" width="900" frameborder="0" allowfullscreen>&nbsp; </iframe>
</div>

This can be disabled, as per this [gis.stackexchange post](http://gis.stackexchange.com/questions/111887/leaflet-mouse-wheel-zoom-only-after-click-on-map) by replacing the definition of the map variable:

{% highlight javascript %}
var map = L.map('map', {
            zoomControl:true, maxZoom:28, minZoom:1
        }).fitBounds([[-23.0310490725,-43.7439095692],[-22.4740929065,-42.9533150116]]);
{% endhighlight %}

with:  
{% highlight javascript %}
var map = L.map('map', {
            zoomControl:true, maxZoom:28, minZoom:1, scrollWheelZoom: false
        }).fitBounds([[-23.0310490725,-43.7439095692],[-22.4740929065,-42.9533150116]]);
map.once('focus', function() { map.scrollWheelZoom.enable(); });
{% endhighlight %}  

Tada! You now have to click within the map to activate scroll wheel zooming.  

<div class = "leaflet-map">
    <iframe src="{{ site.baseurl }}/maps/transit_working_age/index2.html" height="675" width="900" frameborder="0" allowfullscreen>&nbsp; </iframe>
</div>

# Restricting Zoom Level to Layer Extent

To prevent the map from being zoomed out beyond the extents of the layer, I added the following:
{% highlight javascript %}
        map.options.minZoom = map.getZoom();
{% endhighlight %}

# Editing the Legend

The legend appears to be contained within the `L.control.layers()` function. In order to remove the basemaps radio button, I passed it an empty variable:
{% highlight javascript %}
    L.control.layers({},overlayMaps).addTo(map);
{% endhighlight %}  

The order of the layers not being respected when turned on and off from the legend is a frustrating issue, one that still doesn't seem to be [necessarily resolved](https://github.com/tomchadwin/qgis2web/issues/96).
