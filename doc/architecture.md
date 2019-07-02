# Architecture: 

## Basic Architecture:

![Architecture basic](doc/00_architecture.pdf)
 
Cosmic river is a lightweight distributed application Server/Client model, made to be composable. 
 
The server component will listen on events of GitHub API3 and send them to the message broker of your choice.
 
The clients will then consume this events and react with a custom handler. The handler can be written in any programming language.
( You will need only to respect the specification and env. variables, see specification.md)
