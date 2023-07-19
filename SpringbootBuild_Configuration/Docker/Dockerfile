## Eclipse Temurin project provides code and processes that support the building of runtime binaries and associated technologies.
# First stage: extract the dependencies, build the fat JAR, and unpack it.
FROM eclipse-temurin:20-jdk-alpine as builder
WORKDIR /opt/app/fourbench-services
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY ./src ./src
RUN ./mvnw clean install

# Second stage: copy the extracted dependencies to the final image
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /opt/app/fourbench-services
EXPOSE 8087
COPY --from=builder /opt/app/fourbench-services/target/*.jar /opt/app/fourbench-services/schoolmanagement-service.jar
ENTRYPOINT ["java", "-jar", "/opt/app/fourbench-services/schoolmanagement-service.jar" ]