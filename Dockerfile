FROM ghcr.io/graalvm/native-image-community:25 AS build

ARG DEDER_VERSION=v0.23.0
ADD https://github.com/sake92/deder/releases/download/${DEDER_VERSION}/deder-client.jar /opt/deder-client.jar

WORKDIR /workspace
COPY deder.pkl .

# API and database sources are generated locally and committed. Image builds
# compile those checked-in sources but do not regenerate them.
COPY openapi openapi
COPY modules/api modules/api
ENV GRAALVM_HOME=${JAVA_HOME}
RUN java -jar /opt/deder-client.jar exec -t graalvmNativeImage -m api

FROM debian:bookworm-slim

WORKDIR /app
COPY --from=build /workspace/.deder/out/api/graalvmNativeImage/native-executable /app/petclinic-api

USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["/app/petclinic-api"]
