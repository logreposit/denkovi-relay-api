FROM openjdk:8-jre-alpine

ENV WIREMOCK_VERSION 2.24.0

# grab wiremock standalone jar
RUN mkdir -p /var/wiremock/lib/ \
  && wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/$WIREMOCK_VERSION/wiremock-standalone-$WIREMOCK_VERSION.jar \
    -O /var/wiremock/lib/wiremock-standalone.jar

WORKDIR /home/wiremock

VOLUME /home/wiremock
EXPOSE 8080 8443

HEALTHCHECK --interval=1s --timeout=3s --retries=10 \
  CMD wget http://localhost:8080/__admin/mappings -q -O - > /dev/null 2>&1

ENTRYPOINT ["java","-jar","/var/wiremock/lib/wiremock-standalone.jar"]


