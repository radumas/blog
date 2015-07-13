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

I createad a `gtfs-editor` database and user, and had to give the user a password and enter than in the [config](#configuration). Enable PostGIS on the `gtfs-editor` database by running `CREATE EXTENSION PostGIS`. 
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
and then edit `~/.bashrc` to make the changes permanent, by adding the line above to the end of that file. This will run the same command on boot.  
###Dependencies
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
These were *mostly* fixed by making the `repositories` section of `con/dependencies.yml` to
```shell
repositories:
    - typesafe:
        type: iBiblio
        root: "http://repo.typesafe.com/typesafe/releases/"
        contains:
            - com.typesafe.akka -> *
    - akka:
        type: iBiblio
        root: "http://repo.akka.io/releases/"
        contains:
            - voldemort.store.compress -> *

    - osgeo:
        type: iBiblio
        root: "http://download.osgeo.org/webdav/geotools/"
        contains:
            - org.geotools -> *
            - java3d -> *
            - javax.media -> *
            - jgridshift -> *

    - conveyal:
        type: iBiblio
        root: "http://maven.conveyal.com/"
        contains:
            - org.opentripplanner -> *
```
###Configuration
Copy the template and then open application.conf
```shell
    cp conf/application.conf.template conf/application.conf
```
 I put the following in the config
```shell
# To connect to a local PostgreSQL9 database, use:
 db.url=jdbc:postgresql://127.0.0.1:5432/gtfs-editor
 db.driver=org.postgresql.Driver
 db.user=gtfs-editor
 db.pass=PASSWORD
```

```
###Run
Hit `play run` and navigate to `http://localhost:9000`
