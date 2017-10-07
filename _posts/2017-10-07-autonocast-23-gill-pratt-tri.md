---
layout: post
section-type: post
title: "Autonocast #23:  Dr. Gill Pratt, Toyota Research Institute"
published: true
date: 2017-10-07 12:15:00 -0500
categories: ['podcast-summary']
author: Raphael Dumas
summary: "Toyota Research Institute CEO Dr. Gill Pratt talks to Autonocast about their research into autonomous vehicles, 'guardian angel', and redundancy."
tags: [ 'autonomous-vehicles', 'parallel-autonomy','redundancy','v2x']
thumbnail: map  
---
[**Autonocast episode #23**](http://www.autonocast.com/blog/23-gill-pratt-interview)
In this episode of the Autonocast, they interview Dr. Gill Pratt, CEO of the Toyota Research Institute (TRI). TRI focuses on how automation can improve many aspects of our lives, beyond merely automation in cars, though this episode focuses specifically on the AV work of TRI.

## Things to note:
### Guardian Angel Automation
TRI distinguishes between series and parallel (guardian) automation. While their development work involves both types. Dr. Pratt's stated focus is on the latter, which differentiates TRI from just about every other AV tech creator. Instead of fully taking over driving duties in a limited but increasing set of circumstances, "Guardian Angel" automation is more like driving with an expert who can grab the wheel and brakes if you get into a jam. 

The hosts try to bring up this distinction in automation between Boeing and Airbus, where Boeing is allegedly more about enhancing the pilot's abilities and Airbus is trying more to design a plane that can fly itself. Airbus's approach led to the tragic crash of a flight from Rio to Paris where the autopilot handed control of the plane back to the co-pilots, but they didn't understand what the systems were telling them. 

Human factors in both forms of automation is crucially important. Dr. Pratt references how to design the experience of the car taking control from the human in an emergency: should the wheel start turning itself? Or should the car's AI direct the wheels and signal to the driver that the steering wheel has been temporarily decoupled from the steering system.

One of the benefits of this approach, I believe we're made to understand, that machines have tremendous difficulty in  learning the social interactions of driving. Guardian Angel automation maintains the human's position in the human elements of driving while enhancing safety through the use of sensors and AI. Kind of like how backup cameras and blind-spot checkers are already improving safety.

### Redundancy
#### In Sensors
There was also an discussion about redundancy: in sensors, in computing, in communications (possibly?). This is still apparently a largely unexplored field: as manufacturers have yet to settle on what sensor set is necessary for full automation. It will be an interesting policy discussion of what redundancy will governments require in vehicles. Should the consequence of something failing in an AV that it pulls to the shoulder, it shuts down, and it calls roadside assistance for you? How can it do so safely, depending on the nature of the failure?
#### With V2X
Regarding Vehicle to Infrastructure/Vehicle to Anything (V2I/V2X) communication (and redundancy), Dr. Pratt argued that cars need to work fine without V2X, because any communications system is bound to fail at some point: traffic lights stop working all the time, and power outages shouldn't result in all cars ceasing to move. 

## My thoughts
### Risk Compensation in Guardian Angel AI
While the hosts pressed the Boeing vs. Airbus style of automation, they really failed in pressing Dr. Pratt on whether there are rebound effect for Guardian Angel crash avoidance technologies. Sure they can "be proven safe" in a lab, but do they lead to risk compensation? More on this, unfortunately, in the next episode.
### The Need for Redundancy in V2X  
The point Dr. Pratt was making about the necessity of redundancy in V2X tech and automation seems to undermine the point of developing it. How degraded an experience is acceptable without V2X? How often would critical elements of it fail? For example, [the signal-less traffic intersection](http://senseable.mit.edu/light-traffic/) proposed by MIT's Senseable Cities Lab **only works** with some degree of communication. But can AVs really drive through such an intersection at 50 km/hr (35 mph) if they have to account for some element of the intersection not communicating properly with the vehicles?
