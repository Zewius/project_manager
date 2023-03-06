# Проектный менеджер / Project manager
REST API приложение, написанное на Java. Реализовано по техническому заданию компании **Advance Engineering**.

Использовался следующий стек технологий:
1. Java 17;
2. Spring Framework 6 (включая Web MVC, Data JPA и Security) + Spring Boot 3;
3. СУБД H2;
4. Maven.

## Функционал
В данном проекте реализован следующий функционал:
- Аутентификация и авторизация: 
  - Аутентификация происходит с помощью метода Basic Authentication.
  - Пользователи могут иметь одну из ролей - **ADMIN**, **TECHNICIAN**, **MANAGER**.
- Структура проекта:
  - Иерархическая структура проектов и подпроектов (в проекте может быть несколько подпроектов). 
  - Создавать, редактировать, удалять может пользователь с ролью **ADMIN**.
  - Получить структуру проекта может любой аутентифицированный пользователь.
- Структура задач:
  - Задачи можно создать для любого уровня проекта / подпроекта. 
  - Задачи делятся на два типа: для менеджера (**MANAGER**), для технического специалиста (**TECHNICIAN**). 
  - Задача содержит следующую информацию:
    - название; 
    - информацию о пользователе, который создал задачу;
    - дату создания;
    - статус (NEW, PROGRESS, DONE); 
    - дату изменения статуса; 
    - дополнительную информацию;
  - Пользователи с ролью **MANAGER** и **TECHNICIAN** могут создать задачу для любого уровня проекта\подпроекта,
  изменить статус задачи, удалить свою задачу, посмотреть все задачи **своего роля**. 
  - Любой администратор может редактировать и удалять любые задачи, но не может создавать.

Конечные точки API и информация о доступе к ним представлена в 
[Postman коллекции](https://app.getpostman.com/run-collection/22087998-29bbf59e-ca7e-431f-9509-77eefa9ca56d?action=collection%2Ffork&collection-url=entityId%3D22087998-29bbf59e-ca7e-431f-9509-77eefa9ca56d%26entityType%3Dcollection%26workspaceId%3D54409794-b520-48c7-b237-7b0caf18fc24#?env%5BAdmin%20Environment%5D=W3sia2V5IjoidXNlcm5hbWUiLCJ2YWx1ZSI6ImFkbWluIiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiJhZG1pbiIsInNlc3Npb25JbmRleCI6MH0seyJrZXkiOiJwYXNzd29yZCIsInZhbHVlIjoiYWRtaW4iLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6ImFkbWluIiwic2Vzc2lvbkluZGV4IjoxfV0=).
Не забудьте выбрать среду перед использованием.

SQL-скрипт с данными для тестирования находятся в `src/main/java/resources/data.sql`.