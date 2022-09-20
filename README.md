Improve Readme!

Run postgres manually with env variables `user = postgres`, `password = postgres`, `database = postgres`
```
docker run --rm --name db-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 -d postgres
```
Build and tag image:
```
docker build ./ -t <tag>
```

What be done better or improved:
- added better logging: e.g.MDC/Sleuth
- refactor code to handle better exceptions and validation etc.
- add OpenAPi
- Docker file and docker compose - on windows docker build isn't working, on linux yes, repair it
- add Wiremock and integration more tests
- add grafana and prometheus metrics
- add spring security - OAuth2
- add another domain with communication with currency (e.g. kafka)