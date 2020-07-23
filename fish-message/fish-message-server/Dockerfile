ARG JAVA_VERSION=11.0.8

# Runner image
FROM openjdk:${JAVA_VERSION}-jdk
MAINTAINER Devifish <devifish@outlook.com>

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' >/etc/timezone

ARG APPLICATION_NAME=fish-message-server
WORKDIR /usr/local/${APPLICATION_NAME}
COPY target/${APPLICATION_NAME}.jar ./

# Runner environment
ENV JAVA_OPTS="-Xms128m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom" \
    SPRING_OPTS="-Dspring.profiles.active=prod" \
    APPLICATION_JAR_FILE="${APPLICATION_NAME}.jar"

EXPOSE 10200
ENTRYPOINT java ${JAVA_OPTS} ${SPRING_OPTS} -jar ${APPLICATION_JAR_FILE}