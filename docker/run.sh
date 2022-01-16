#!/bin/sh

echo "Starting application ..."
exec java -Djava.security.egd=file:/dev/./urandom -jar /opt/logreposit/denkovi-relay-api/app.jar
