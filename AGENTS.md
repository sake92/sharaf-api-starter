# Agent Instructions

## Setup and commands

- Use JDK 17 or newer, sbt 2, and PostgreSQL 17. See `README.md` for local configuration and Compose setup.
- Docker must be available for the Testcontainers integration suite.

| Task | Command |
|------|---------|
| Start local API and database | `docker compose up --build` |
| Compile API | `sbt api/compile` |
| Run HTTP-to-PostgreSQL tests | `sbt integrationTests/test` |
| Apply local migrations | `sbt api/flywayMigrate` |
| Regenerate database rows and DAOs | `sbt api/squeryGenerate` |
| Regenerate server contracts and client | `sbt generateContracts` |
| Format Scala and sbt files | `sbt scalafmtAll` |

## Conventions

- `openapi/petclinic.yaml` is the only API contract. Change it before regenerating `modules/api` and `modules/client` sources.
- Database schema changes belong in `modules/api/src/main/resources/db/migration`; regenerate database rows and DAOs against PostgreSQL after table or column changes.
- Generated models, client code, database rows, and DAOs are committed. Review the diff after generation; the OpenAPI generator also touches controller scaffolding.
- Keep controller code focused on HTTP validation, status codes, and mapping to API models. Put owner joins and transactions in `modules/api/src/main/scala/com/example/petclinic/db/daos/OwnersRepo.scala`.
- Owner deletion uses `DELETE ... RETURNING`; the `pets.owner_id` foreign key restricts deletion when pets exist. Translate only that constraint violation to HTTP 409.
- Use Squery's `sql` fragments with generated DAO `findAllWhere` for simple predicates. Use `Option[Row]` for left-joined rows and `allColsWithPrefix` for nested `SqlReadRow` decoding.
- Return `ApiProblem.response` for expected HTTP errors, including missing owners. Reserve `NotFoundException` for cases where a direct response is impractical; `modules/api/src/main/scala/com/example/petclinic/main/main.scala` maps it to the same structured error response.
- Exercise API behavior through the generated client and real PostgreSQL in `modules/integration-tests`; Flyway applies the production migrations there.

## References

| Need | File |
|------|------|
| Local development and configuration | `README.md` |
| Canonical API contract | `openapi/petclinic.yaml` |
| Build modules and generator settings | `build.sbt` |
