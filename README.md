This project has been developed within the course:
"20200812. GeekBrains. Java team development".

Result: 
online store application that is integrated with CRM by Spring REST API.

#Problems which were solved and technologies used
Required that:
 - CRM received all events created at online-stores which could be several;
 - Events generate for a new order creating or changing order status as well;
 - CRM sends back a time of accepting event, updated entities like a new order status etc.
 
#Technologies:
 - GUI: Java String Boot(Core, MVC, Data, Security), WebSocket, Thymeleaf;
 - Database: MySql;
 - CRM integration: Java String REST API;
 - My own solution - all responses from online-store is an instance of OutEntity object;
 - OutEntity is a template entity just for serializing/deserializing json-object with the recursion using;
 - The recursion using is my own idea in order to make converting process code simple, clearer and 
    more flexible for adding new entities to the project;
 - Testing: JUnit.

#Installation and trying
No need to install anything to try this app. Just come to 
https://gb-spring-amin-ishop-heroku.herokuapp.com/ 
and use it like a normal online store.

Please note that all features available to user after authorisation only.
For more details of how it works please get in the presentation below.

#Presentation, code and other links
About the course - https://geekbrains.ru/lessons/75327

MS Powerpoint presentation about the project is available on 
https://drive.google.com/file/d/1p0yeu0XdvMrElp9mtxNhsKIyu-NarWG9/view?usp=sharing

Video presentation - on ...(will be available later)

Code of the project is available on github.com - 
https://github.com/iourilitv/gb-spring-amin-ishop-heroku/tree/release

#Developer
Litvinenko Yury.

Professional profile: Java Back-End developer.

telegram: @yuryli

Please be free to contact me about this project or a new one!

