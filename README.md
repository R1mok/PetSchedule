# PetSchedule
## О проекте
Данное приложение позволяет записывать информацию о питании питомцев, ставить напоминания и составлять расписание кормления. Доступны регистрация и аутентификация посредством JSON Web Token (JWT). Пользователь может создавать группу (или несколько групп), добавлять в неё питомцев и других пользователей. Каждый участник группы может получать уведомления о питомцах, находящихся в группе, и создавать свои.  
Написаны интеграционные тесты, проверяющие взаимодействие уровня бизнес-логики и уровня доступа к данным. Реализован обработчик ошибок с логированием Log4j2. Используется сборка maven, есть возможность запуска с помощью docker-compose.
## Используемые технологии
### Технологии Spring Framework
* Spring Boot
* Spring Web (RESTful сервис)
* Spring Data JPA + Hibernate
* Spring Security (Аутентификация и авторизация пользователя с использованием JWT-токенов)
### База данных
* PostgreSQL
### Дополнительные библиотеки и фреймворки
* Maven
* Docker и docker-compose
* Lombok
* Junit (Тестирование бизнес-логики)
* Swagger (OpenAPI 3)
