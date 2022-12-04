# Chat Server

## Overview

Backend server for a chat app.

## Frontend

- https://github.com/Zheng-August/html
- https://github.com/LJC0414/GhostChat

## Prerequisites

- MySQL community server

- JRE 17

## Setup and configuration

### Database

Login to MySQL as root

```mysql
CREATE DATABASE chat;
CREATE USER 'chatuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL ON chat.* TO 'chatuser'@'localhost';
```

### Run

cd into project root

```shell
./mvnw spring-boot:run
```

It could take a while when you run it for the first time, since it will resolve and download dependencies.



Alternatively, run the program using jar file

```shell
java -jar chat-server-xxx.jar --server.port=<port-number>
```

where `port-number` equals to the same port of the frontend webpage.

## Build from source

```shell
./mvnw clean install
```

Packaged jar file will be located under `chat-server/target/`.