---
layout: post
section-type: post
title: "How to Migrate your PostgreSQL Data Directory in Windows"
published: true
date: 2016-08-08 14:00:00 -0500
categories: ['tutorial']
author: Raphael Dumas
summary: "The default data directory for PostgreSQL in Windows is C/Program Files, but you might not want your database there"
tags: [ 'postgresql']
thumbnail: database  
---

# Migrating the data cluster from C:\ to E:\
*I'm new to working with large data in Windows, and ran into some unexpected difficulty when the PostgreSQL database ran out of space on my server's C drive. Here's how to move your PostgreSQL data directory*

Basing myself on [this dba.stackex answer](http://dba.stackexchange.com/a/28927) and this [wiki post](https://wiki.postgresql.org/wiki/Change_the_default_PGDATA_directory_on_Windows)

## 1. Stop the PostgreSQL service

Go to `Start > Services`. Scroll down to `postgresql-x64-9.5` and right-click `Stop`

## 2. Copy data

I copied the data with File Explorer from `C:\Program Files\PostgreSQL\9.5\data\` to `E:\pg_db\data`

## 3. ~~Modify postgresql.conf ~~ (probably not necessary if you move everything to the new location)

I edited the following lines

```
#------------------------------------------------------------------------------
# FILE LOCATIONS
#------------------------------------------------------------------------------

# The default values of these variables are driven from the -D command-line
# option or PGDATA environment variable, represented here as ConfigDir.

data_directory = 'E:\pg_db\data'		# use data in another directory
					# (change requires restart)
hba_file = 'C:\Program Files\PostgreSQL\9.5\data\pg_hba.conf'	# host-based authentication file
					# (change requires restart)
ident_file = 'C:\Program Files\PostgreSQL\9.5\data\pg_ident.conf'	# ident configuration file
					# (change requires restart)
```

## 4. Modify the command that starts PostgreSQL

If you Right Click to `Properties` on the PostgreSQL services in `Services`, you'll notice that the startup command is 

```"C:\Program Files\PostgreSQL\9.5\bin\pg_ctl.exe" runservice -N "postgresql-x64-9.5" -D "C:\Program Files\PostgreSQL\9.5\data" -w```

The `-D "C:\Program Files\PostgreSQL\9.5\data"` flag is indicating that the data directory is `C:\Program Files\PostgreSQL\9.5\data`

### **DO NOT EDIT THE REGISTRY**
_In any case, I didn't have permission on the server I was using_ and it seems to be a **Bad Idea**(tm) [see here](http://stackoverflow.com/a/24877051/4047679)
 
>Direct registry modification should be avoided (because you can't be sure what else Windows is changing when it modifies the path to exe, f.e.) unless you are absolutely sure what you do
 
Start the `cmd` prompt as an administrator by hitting the Windows button and typing `cmd` then right-clicking "Run as Administrator"

You can see the current configuration for PostgreSQL by typing `sc qc postgresql-x64-9.5 1000` and hitting Enter.

Modify the configuration to the following with the `sc` command

```
sc config postgresql-x64-9.5 binPath= "\"C:\Program Files\PostgreSQL\9.5\bin\pg_ctl.exe\" runservice -N \"postgresql-x64-9.5\" -D \"E:\pg_db\data\" -w"
```

## 5. Change permissions for the new data directory
For the new data-dictionary folder: Right-click on it and click `Properties`. Under the `Security` Tab click "`Edit...`" and then "`Add...`". Type  "`Network Service`" and then click "`Check Names`", make sure it has `Modify` and `Full Control` permissions and then click `OK`.
**Equally important** PostgreSQL needs to be able to "see" the data-directory (see my [ServerFault.StackEx question](http://serverfault.com/questions/793461/why-does-changing-data-directory-for-postgresql-9-5-in-server-2008-lead-to-dire?noredirect=1#comment1004149_793461)), i.e. it needs to have read access to the parent directories above it. So Right-click on the `pg_db` folder and under the `Security` Permissions add `Network Services` again, but this time it only needs `Read & Execute` as well as `List folder contents` permissions.

## 6. Restart the Service

Go back to the `Services` window (if it was still open, refresh it) and `Start` the PostgreSQL service. You should be able to connect to it again in PGAdmin
    
