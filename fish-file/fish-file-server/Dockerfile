ARG JDK_VERSION=11
ARG APPLICATION_NAME=fish-file-server
ARG APPLICATION_ACTIVE=prod

#Runner image
FROM openjdk:${JDK_VERSION}-jdk
MAINTAINER Devifish <devifish@outlook.com>

WORKDIR /server
ADD ${APPLICATION_NAME}.jar ./
EXPOSE 20000

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENV JAVA_OPTS="-Xms128m -Xmx256m -Xss256k -XX:+UseConcMarkSweepGC -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_OPTS="-Dspring.profiles.active=${APPLICATION_ACTIVE}"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS $SPRING_OPTS -jar ${APPLICATION_NAME}.jar"]