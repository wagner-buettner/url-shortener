FROM openjdk:17-slim
EXPOSE 8080

ADD build/libs/url-shortener-0.0.1-SNAPSHOT.jar /url-shortener.jar

CMD ["java", "-jar", "/url-shortener.jar"]
