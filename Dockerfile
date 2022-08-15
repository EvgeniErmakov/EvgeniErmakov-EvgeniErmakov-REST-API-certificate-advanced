FROM openjdk:11-jre

ENV ADMINISTRATION_WORKDIR=/certificate
ENV JAR_BUILD_NAME=rest-api-advanced.jar
ENV JAR_NAME=rest-api-advanced.jar
WORKDIR ${ADMINISTRATION_WORKDIR}

RUN mkdir -p /opt/artquiz/administration
COPY build/libs/${JAR_BUILD_NAME} /certificate/${JAR_NAME}

ENTRYPOINT java -jar $JAR_NAME