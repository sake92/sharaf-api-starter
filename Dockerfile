FROM eclipse-temurin:17-jdk-alpine AS build

ARG SBT_VERSION=2.0.6
RUN wget -q "https://repo1.maven.org/maven2/org/scala-sbt/sbt-launch/${SBT_VERSION}/sbt-launch-${SBT_VERSION}.jar" \
    -O /usr/local/lib/sbt-launch.jar

WORKDIR /workspace
COPY project project
COPY build.sbt .
RUN java -jar /usr/local/lib/sbt-launch.jar update

# API and database sources are generated locally and committed. Image builds
# compile those checked-in sources but do not regenerate them.
COPY api api
RUN java -jar /usr/local/lib/sbt-launch.jar "api / pack"

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S petclinic && adduser -S -G petclinic petclinic
WORKDIR /app
COPY --from=build --chown=petclinic:petclinic /workspace/api/target/pack /app

USER petclinic
EXPOSE 8080
ENTRYPOINT ["java", "-cp", "/app/lib/*", "com.example.petclinic.main.apiMain"]
