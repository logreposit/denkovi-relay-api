FROM openjdk:12

MAINTAINER Dominic Miglar <dominic.miglar@netunix.at>

WORKDIR /opt/logreposit/ta-cmi-reader-service

COPY run.sh    /opt/logreposit/ta-cmi-reader-service/run.sh
COPY app.jar   /opt/logreposit/ta-cmi-reader-service/app.jar
RUN  chmod 755 /opt/logreposit/ta-cmi-reader-service/run.sh

ENTRYPOINT ["/opt/logreposit/ta-cmi-reader-service/run.sh"]
