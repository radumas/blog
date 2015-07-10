#Setting Up Conveyal's GTFS-Editor on a Ubuntu 14.04 Laptop
[Conveyal's](https://www.conveyal.com) [gtfs-editor](https://github.com/conveyal/gtfs-editor/) is a web based editor for a GTFS schedule. I added it to the coppe-ltc git project as a submodule using, after creating a bit of a mess by simply cloning.
```
git submodule add https://github.com/conveyal/gtfs-editor.git
```


##Installation
Requires `PostgreSQL` and `PostGIS`, an open-source SQL database and the spatial extension to this respectively. Installation follows the [installation instructions](https://github.com/conveyal/gtfs-editor/blob/master/INSTALL.md).   

```shell
sudo apt-get install postgis postgresql pgadmin3 postgresql-9.3-postgis-2.1
```
It also requires `java`  
```shell
sudo apt-get openjdk-7-jre
```
Install play framework 1.2.5 (gtfs-editor depends on v1.2.x, version 2.x+ is not compatable)

	wget http://downloads.typesafe.com/releases/play-1.2.5.zip


Unzip play framework

	unzip play-1.2.5.zip
    
Include play in the $PATH variable, this means you can use `play` by simply entering the `play` command in terminal. Run the command below
```shell
    export PATH=$PATH/path/to/playfolder/
```
and edit `~/.bashrc` to make the changes permanent, by adding the line above to the end of that file. This will run the same command on boot.  
Configure gtfs-editor application.conf
```shell
    cp conf/application.conf.template conf/application.conf
```
Created a `gtfs-editor` user for the local postgresql database using `createuser --interactive` from the `postgres` user account (switch user using `sudo su postgres`). I put the following in the config
```bash
# To connect to a local PostgreSQL9 database, use:
 db.url=jdbc:postgresql://127.0.0.1/gtfs-editor
 db.driver=org.postgresql.Driver
 db.user=gtfs-editor
```
Install dependencies.
```
    play dependencies
```
I got warnings that 3 dependencies were missing, see [open issue](https://github.com/conveyal/gtfs-editor/issues/211)
```shell
        :: voldemort.store.compress#h2-lzf;1.0: not found

		:: java3d#vecmath;1.3.2: not found

		:: jgridshift#jgridshift;1.0: not found
```

