FROM openjdk:17-jdk-alpine
ARG JARFILE=app.jar
WORKDIR /app # 컨테이너 작업 디렉토리 설정
ADD . /app
COPY ${JARFILE} app.jar
CMD ["java", "-Dlogstash.url=logstash.elk.svc.cluster.local:5000", "-jar", "app.jar"]