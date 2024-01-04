# First Stage: Build
FROM eclipse-temurin:21-jdk-alpine as workout-journal-backend
WORKDIR /workspace/app
LABEL authors="Misiac"

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Second Stage: Runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=workout-journal-backend ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=workout-journal-backend ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=workout-journal-backend ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.misiac.workoutjournal.WorkoutJournalApplication"]