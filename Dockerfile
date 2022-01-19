FROM registry.haier.net/library/openjdk:8-sw66
RUN echo "Asia/Shanghai" > /etc/timezone
COPY ./micro-service-service/target/*.jar /srv/app.jar
WORKDIR /srv
CMD ["java", "-jar", "app.jar"]