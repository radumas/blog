---
layout: post
section-type: post
title: "Pulling Realtime Bus Positions from FeTranspor"
published: false
categories: Tutorial
author: Raphael Dumas
tags: [ 'data', 'bus']
thumbnail: cloud-download
---


## curl 
Including timestamp in filenames from [askubuntu](http://askubuntu.com/questions/94979/save-hourly-file-from-curl-response)

```bash 
curl http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/csv/onibus.cfm -o "data$(date +%F-%H:%M).txt"
```
