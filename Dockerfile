FROM maven:3.9.6-eclipse-temurin-21

# Install Node 24 and required utilities
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    gnupg \
    unzip \
  && curl -fsSL https://deb.nodesource.com/setup_24.x | bash - \
  && apt-get install -y --no-install-recommends nodejs \
  && npm install -g allure-commandline@2.29.1 \
  && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/opt/java/openjdk
ENV MAVEN_HOME=/usr/share/maven
ENV PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY testng.xml .

# Pre-download Maven dependencies and compile sources
RUN mvn -B -DskipTests package

CMD ["mvn", "-B", "clean", "test"]
