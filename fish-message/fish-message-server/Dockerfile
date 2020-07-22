ARG JAVA_VERSION=11.0.8

# Runner image
FROM openjdk:${JAVA_VERSION}-jdk
MAINTAINER Devifish <devifish@outlook.com>

ARG APPLICATION_NAME=fish-message-server
ARG APPLICATION_ACTIVE=prod

WORKDIR /server
COPY ${APPLICATION_NAME}.jar ./
EXPOSE 10200

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

# Runner environment
ENV JAVA_OPTS="-Xms128m -Xmx256m -Xss256k -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_OPTS="-Dspring.profiles.active=${APPLICATION_ACTIVE}"
ENV APPLICATION_JAR_FILE="${APPLICATION_NAME}.jar"
ENTRYPOINT java ${JAVA_OPTS} ${SPRING_OPTS} -jar ${APPLICATION_JAR_FILE}