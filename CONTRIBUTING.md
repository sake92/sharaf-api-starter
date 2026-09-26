# Contributing

## Change the database schema

Use Flyway migrations as the source of truth for every schema change. Do not
alter the database manually and then try to recreate that change in a migration.

Before generating database code, ensure a disposable PostgreSQL database is
available at the API build's configured JDBC URL. The default is
`jdbc:postgresql://localhost:5432/petclinic` with the `petclinic` user and
password.

1. Add the next versioned SQL migration under
   `modules/api/src/main/resources/db/migration`.
2. Apply all migrations to the local database:

   ```shell
   sbt api/flywayMigrate
   ```

3. Regenerate the database rows and DAOs from that migrated database:

   ```shell
   sbt api/squeryGenerate
   ```

4. Review and commit the migration and generated source changes together.
5. Verify the change with the relevant compile or integration-test command.

The sbt Flyway task prepares a local database so that Squery can generate code.

While a migration is private and only applied to a disposable local database,
you can reset that database and amend the migration. Once it has been shared or
deployed, do not edit or delete it. Add a later migration to correct the schema
instead.
