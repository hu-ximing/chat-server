# Chat Server

## Overview

Backend server for a chat app.

## Frontend

- https://github.com/Zheng-August/html
- https://github.com/LJC0414/GhostChat

## Prerequisites

- MySQL 8.0 server

- JRE 17

### Configuring database

Login to MySQL as root

```mysql
CREATE DATABASE ghost_chat;
CREATE USER 'ghost_chat_user'@'localhost' IDENTIFIED BY '1234';
GRANT ALL ON ghost_chat.* TO 'ghost_chat_user'@'localhost';
```

## Running the application

Before running the application, please make sure your system environment meets the prerequisites described above and have the database configured.

cd into project root

```shell
./mvnw spring-boot:run
```

It can take a while when you run it for the first time, since maven would resolve and download dependencies.

Alternatively, run the program using jar file:

```shell
java -jar chat-server-xxx.jar
```

## Building from source

```shell
./mvnw install
```

Packaged jar file will be located under `chat-server/target/`.

## Deploying behind Nginx reverse proxy

Spring Boot application default listens on port 8080, and in this application, all APIs have url starting with `/api`.

In this case, we want all requests to `http://server/api/` to be forwarded to the same machine but a different port `http://server:8080/api/`.

1. Edit the `/etc/nginx/nginx.conf` file and add the following settings to the server block that should provide the reverse proxy:
   
   ```nginx
   location /api/ {
      proxy_pass http://localhost:8080;
      proxy_set_header Host               $host;
      proxy_set_header X-Real-IP          $remote_addr;
      proxy_set_header X-Forwarded-For    $proxy_add_x_forwarded_for;
      proxy_set_header X-Forwarded-Host   $host;
      proxy_set_header X-Forwarded-Server $host;
      proxy_set_header X-Forwarded-Port   $server_port;
      proxy_set_header X-Forwarded-Proto  $scheme;
   }
   ```

2. If nginx is running on RHEL based distributions, set the `httpd_can_network_connect` SELinux boolean parameter to `1` to configure that SELinux allows NGINX to forward traffic:
   
   [Learn more](https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/9/html/deploying_web_servers_and_reverse_proxies/setting-up-and-configuring-nginx_deploying-web-servers-and-reverse-proxies#configuring-nginx-as-a-reverse-proxy-for-the-http-traffic_setting-up-and-configuring-nginx)
   
   ```shell
   setsebool -P httpd_can_network_connect 1
   ```

3. Restart the `nginx` service:
   
   ```shell
   systemctl restart nginx
   ```

## Rest API

Run the program with the frontend and reverse proxy set up. Visit `http://server/api` to read Rest API documentation.
