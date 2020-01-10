---
layout: post
section-type: post
title: "Multi-User Jupyterhub in a Virtual Environment"
published: false
date: 2019-12-29 20:00:00 -0500
categories: ['tutorial']
author: Raphael Dumas
summary: "How to set up JupyterHub on an Ubuntu server for multiple users while isolating Python packages in their own environment"
tags: [ 'python','jupyterhub','virtual-environments']
thumbnail: line-chart  
---

For my work at the Toronto Transportation Services' Big Data Innovation Team, we often do analyses in Jupyter Notebooks. Our system of individuals working independently on their notebooks wasn't working for us for two reasons:

- **They were hard to share.** Github often wouldn't render them properly, so someone would have to clone and open a notebook to view it
- **They were hard to collaborate on.** Everyone had their own Windows environment, and many of the packages we were using were hard to install.

What we hoped JupyterHub would solve would be 

1. making it easier to clone and sync repositories of analysis from Github; and,
2. use one Python for everyone to mitigate issues with people using different environments.

What follows is an adaptation of the [install instructions](https://github.com/jupyterhub/jupyterhub/wiki/Using-sudo-to-run-JupyterHub-without-root-privileges) to our particular environment: an internal cloud Ubuntu server accessed within the corporate network. An important note: this involves users authenticating using their Linux usernames and passwords to log into JupyterHub. JupyterHub then spawns login sessions for those Linux users, allowing them to run their notebooks under their home folders as well as spawn terminal shells. **This does mean that anyone that can break through JupyterHub's security now has login access to your server, so think hard about deploying this in an environment open to the wider internet.**

## Installing JupyterHub

Creating a `jupyterhub` user:

```bash
sudo adduser -r jhub

# Create jupyterhub group
sudo addgroup jupyterhub
sudo adduser radumas jupyterhub
# We want the jupyterhub user to be able to read the shadow passwords, so add it to the shadow group:
sudo adduser jhub shadow
# Create directory for JupyterHub
sudo mkdir /etc/jupyterhub
sudo chown jhub /etc/jupyterhub
# Giving jhub a home directory so pip will behave
sudo mkdir /home/jhub
sudo chown jhub /home/jhub
```

We will now install the python packages in a virtual environment so we don't
have to deal with potential collisions with system packages. This will also
allow any jupyter hub user to install and upgrade packages used by the
jupyterhub kernel.

```bash
# Change to jhub user
sudo su -l jhub
# Create the virtual environment in its own folder
cd /etc/jupyterhub
export PIPENV_VENV_IN_PROJECT="enabled"
pipenv --three

# Install JupyterHub in this virtual environment
pipenv install sudospawner
pipenv install jupyterhub
```

Used [this guide](https://github.com/sindresorhus/guides/blob/master/npm-global-without-sudo.md) to enable `jhub` user to do a user install of `configurable-http-proxy`. Then installed `notebook` with `sudo -H -u jhub python3 -m pip install --user notebook`.

Edited the `/etc/sudoers` file with `sudo visudo` and added the following lines:

```bash
# the command(s) the Hub can run on behalf of the above users without needing a password
# the exact path may differ, depending on how sudospawner was installed
Cmnd_Alias JUPYTER_CMD = /etc/jupyterhub/.venv/bin/sudospawner

# actually give the Hub user permission to run the above command on behalf
# of the above users without prompting for a password
%jupyterhub ALL=(jhub) /usr/bin/sudo
jhub ALL=(%jupyterhub) NOPASSWD:JUPYTER_CMD
```

Edited `/etc/jupyterhub/jupyterhub_config.py` to set up `radumas` as admin and the sudo spawner

```python
#  Defaults to an empty set, in which case no user has admin access.
c.Authenticator.admin_users = {'radumas'}

c.Authenticator.delete_invalid_users = True
c.JupyterHub.spawner_class='sudospawner.SudoSpawner'
```

Now jupyterhub should be able to be run from the `jhub` account with  `jupyterhub`. But! We still need to configure JupyterHub to behave nicely with NGINX so that our traffic will work.

## Configuring an NGINX Reverse-Proxy

[NGINX](https://nginx.org/en/docs) is a web server that will act as a layer between receiving requests from a client browser and the `jupyterhub` application. Installing nginx is beyond the scope of this tutorial. Using NGINX will allow JupyterHub to be accessed at `your_server.address/jupyterhub`, without needing to include a port number (helpful when your corporate firewall blocks ports). To avoid collisions on ports, manually setting ports **and** forcing JupyterHub to only listen to proxied traffic by setting the `bind_url` to localhost. The following is added to `/etc/jupyterhub/jupyterhub_config.py`

```python
c.JupyterHub.bind_url = 'http://127.0.0.1:8086/jupyter'
c.JupyterHub.hub_port = 8084
```

and then the configuration for NGINX is as below.

```bash

server {
  #Jupyter appears to send huge blobs between browser and server
  #to save files. Include the below parameter in the https server
  #configuration.
  client_max_body_size 60M;

}
# Default server configuration
#
map $http_upgrade $connection_upgrade {
    default upgrade;
    ''      close;
}

location /jupyter/ {
        rewrite /jupyter/(.*) /jupyter/$1 break;
        proxy_pass http://localhost:8086;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header Host $host;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

        # websocket headers
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection $connection_upgrade;
}

```

## Daemonizing

So that we don't have to manually restart JupyterHub when we use it, it should be made into a service... Riffing off of [these examples](https://github.com/jupyterhub/jupyterhub/wiki/Run-jupyterhub-as-a-system-service#centosfedorageneric-systemd), we created the below `/etc/systemd/system/jupyterhub.service`:

```bash
[Unit]
Description=Jupyterhub
After=syslog.target network.target

[Service]
User=jhub
EnvironmentFile=/etc/jupyterhub/.env
WorkingDirectory=/etc/jupyterhub
ExecStart=/etc/jupyterhub/.venv/bin/jupyterhub -f /etc/jupyterhub/jupyterhub_config.py

[Install]
WantedBy=multi-user.target
```

Because `systemd` starts up commands in a limited environment we have to specify
the necessary environment variables in a `.env` file. Note we have to use the
explicit path to the `jupyterhub` command, which is within our virtual
environment. The `.env` file looks like:

```bash
PATH=/home/jhub/.local/bin:/etc/jupyterhub/.venv/bin:/bin:/home/jhub/.local/lib/python3.5/site-packages/:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin
WORKON_HOME=/etc/jupyterhub
PIPENV_PIPFILE=/etc/jupyterhub/Pipfile
PYTHONPATH="/etc/jupyterhub/.venv/lib/python3.5/site-packages/:/home/jhub/.local/bin:/usr/lib/python35.zip:/usr/lib/python3.5:/usr/lib/python3.5/plat-x86_64-linux-gnu:/usr/lib/python3.5/lib-dynload:/home/airflow/.local/lib/python3.5/site-packages:/usr/local/lib/python3.5/dist-packages:/usr/lib/python3/dist-packages"
```

Note the setting of the `PYTHONPATH` variable. Otherwise Python will default to
loading packages from the system, which is not what we want.

And then `sudo systemctl daemon-reload` to reload configurations and `sudo systemctl start jupyterhub` to start the service and `sudo systemctl status jupyterhub` to check on its status.

## Installing packages

The JupyterHub kernel is set up so that all users use the same kernel, thus
everyone uses the same environment and packages. The virtual environment lives
at `/etc/jupyterhub/.venv/`.

Anyone can install packages while using the kernel by using `!pipenv install`
from within a jupyter cell.

In order to allow people to upgrade packages others installed, it was necessary
to [upgrade some Linux file
permissions](https://unix.stackexchange.com/questions/1314/how-to-set-default-file-permissions-for-all-folders-files-in-a-directory)
on the `site-packages` directory of the virtual environment:

```bash
#Set default group for new files in directory to be directory group
sudo chmod g+s .
#Set default permissions for group to be read write xecute
sudo setfacl -d -m g::rwx .
#Recursively set the group of already installed packages
sudo chgrp jupyterhub -R *
#Recursively allow group to write to already installed packages
sudo chmod g+r -R *
```

## Upgrading JupyterHub

Following [this article](https://jupyterhub.readthedocs.io/en/stable/admin/upgrading.html) on upgrading JupyterHub:

1. Login as `jhub`.
2. Go to `/etc/jupyterhub/` and backup `jupyterhub_config.py` and `jupyterhub.sqlite`.
3. As the superuser, shut down the JupyterHub service: `sudo systemctl stop jupyterhub`.
4. Upgrade JupyterHub's environment. First, activate the JupyterHub environment with
   `source /etc/jupyterhub/.venv/bin/activate`, then launch `pipenv shell`. Check within
   the Pipenv file that `jupyterhub`, `jupyterlab`, `jupyterlab-sql`, etc., are set to
   update to their latest versions, then run `pipenv update`. Confirm the upgrade using
   `pip freeze | grep jupyterhub`.
5. Upgrade any other package using the above.
6. Go back to `/etc/jupyterhub/`, then run `jupyterhub upgrade-db` to refresh the SQLite database. If upgrading jupyterlab and its extensions, you'll also need to run `pipenv shell` then `jupyter-lab build`.
7. Restart JupyterHub using `sudo systemctl start jupyterhub` or
   `restart-jhub`, an alias of the first command that any `jupyterhub` group
   user can run.
