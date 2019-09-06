FROM openjdk:12

MAINTAINER Dominic Miglar <dominic.miglar@netunix.at>

WORKDIR /opt/logreposit/denkovi-relay-api

COPY run.sh    /opt/logreposit/denkovi-relay-api/run.sh
COPY app.jar   /opt/logreposit/denkovi-relay-api/app.jar
RUN  chmod 755 /opt/logreposit/denkovi-relay-api/run.sh

ENTRYPOINT ["/opt/logreposit/denkovi-relay-api/run.sh"]

