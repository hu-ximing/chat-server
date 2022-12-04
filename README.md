# Chat Server

## Overview

Backend server for a chat app.

## Frontend

- https://github.com/Zheng-August/html
- https://github.com/LJC0414/GhostChat

## Configuring properties

Some important configurations are listed below.

Edit `resources/application.properties` to change these properties.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chat
spring.datasource.username=chatuser
spring.datasource.password=1234
server.port=80
springdoc.swagger-ui.path=/api
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
```

## Prerequisites

- MySQL server

- JRE 17

### Configuring database

Login to MySQL as root

```mysql
CREATE DATABASE chat;
CREATE USER 'chatuser'@'localhost' IDENTIFIED BY '1234';
GRANT ALL ON chat.* TO 'chatuser'@'localhost';
```

## Running the application

cd into project root

```shell
./mvnw spring-boot:run
```

It can take a while when you run it for the first time, since maven would resolve and download dependencies.

Alternatively, run the program using jar file

```shell
java -jar chat-server-xxx.jar --server.port=<port-number>
```

where `port-number` equals to the same port of the frontend webpage.

## Building from source

```shell
./mvnw clean install
```

Packaged jar file will be located under `chat-server/target/`.