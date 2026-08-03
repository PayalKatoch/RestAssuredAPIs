FROM node:24-bullseye

# Install OpenJDK 21 and Maven
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-21-jdk \
    maven \
    unzip \
  && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV MAVEN_HOME=/usr/share/maven
ENV PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY testng.xml .

# Pre-download Maven dependencies and compile sources
RUN mvn -B -DskipTests package

CMD ["mvn", "-B", "clean", "test"]
