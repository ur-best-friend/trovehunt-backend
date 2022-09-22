# Trovehunt (backend server)
### Trovehunt is a self-hosted [geocaching](https://wikipedia.org/wiki/Geocaching?lang=en) web app that can be easily integrated in larger social and corporate platforms

## Setup
1) Copy environment variables from .env.example to .env file, specify all required variables
2) Commands for running: 
    - dev: `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up`
    - default (prod/staging): `docker-compose up`
3) Go to https://trovehunt.localhost and https://trovehunt.localhost/login, accept both self-signed SSL certificates

**Swagger api docs are served at https://trovehunt.localhost/swagger-ui.html**

**The UI for monitoring and managing Kafka is hosted at http://localhost:8080**

## Features:
- [x] ~~Containerization with docker-compose~~
- [x] ~~Add Kafka support~~
- Tests:
    - [x] ~~Repositories~~
    - [ ] Controllers, Service layer
    - [ ] Integration (Kafka, Geocoder, etc)
- [ ] OAuth2 server support
- [ ] Set up Github Actions CI/CD pipeline
- [ ] Low priority: add sample microservice for viewing user badges, spending rewards and registering new users