FROM openjdk:11-jre

ENV WORKDIR=/certificate
ENV JAR_NAME=rest-api-advanced.jar
WORKDIR ${WORKDIR}

RUN mkdir -p /opt/artquiz/administration
COPY build/libs/${JAR_NAME} /certificate/${JAR_NAME}

ENTRYPOINT java -jar $JAR_NAME