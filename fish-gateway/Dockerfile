ARG JAVA_VERSION=11.0.8

# Runner image
FROM openjdk:${JAVA_VERSION}-jdk
MAINTAINER Devifish <devifish@outlook.com>

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone

ARG APPLICATION_NAME=fish-gateway
ARG APPLICATION_VERSION=1.0.0

# Runner environment
ENV JAVA_OPTS="-Xms128m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom" \
    SPRING_OPTS="-Dspring.profiles.active=prod" \
    APPLICATION_JAR_FILE="${APPLICATION_NAME}-${APPLICATION_VERSION}.jar"

WORKDIR /usr/local/${APPLICATION_NAME}
COPY build/libs/${APPLICATION_JAR_FILE} ./

EXPOSE 10000
ENTRYPOINT java ${JAVA_OPTS} ${SPRING_OPTS} -jar ${APPLICATION_JAR_FILE}