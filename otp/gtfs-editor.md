#Setting Up Conveyal's GTFS-Editor on a Ubuntu 14.04 Laptop
[Conveyal's](https://www.conveyal.com) [gtfs-editor](https://github.com/conveyal/gtfs-editor/) is a web based editor for a GTFS schedule. 

##Installation
Requires `PostgreSQL` and `PostGIS`, an open-source SQL database and the spatial extension to this respectively. Installation follows the [installation instructions](https://github.com/conveyal/gtfs-editor/blob/master/INSTALL.md).   

```shell
sudo apt-get install postgis postgresql pgadmin3
```
It also requires `java`  
```shell
sudo apt-get openjdk-7-jre
```
Install play framework 1.2.5 (gtfs-editor depends on v1.2.x, version 2.x+ is not compatable)

	wget http://downloads.typesafe.com/releases/play-1.2.5.zip


Unzip play framework

	unzip play-1.2.5.zip
    
Include play in the $PATH variable, this means you can use `play` by simply entering the command.
```shell
    export PATH=$PATH/path/to/playfolder/
```
and edit `~/.bashrc` to make the changes permanent, add the line above to the end of that file.