# TO DO RESTFUL API

## Используемые технологии

* JAVA 17
* PostgreSQL 12
* Spring Boot 3.3
* Maven

## Иструкция развертывания

### 1. Создание базы данных

Coздаем базу данных **todo** в pgAdmin или вводим следующую команду в psql

`CREATE DATABASE todo WITH OWNER postgres;`


### 2. Компиляция

Переходим в корневую папку проекта и в командной строке запускаем команды:

`mvn clean package`

`java -jar target/todo-0.0.1-SNAPSHOT.jar`

### 3. Документация

В браузере переходим:

[Swagger http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)




