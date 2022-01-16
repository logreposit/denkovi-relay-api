#!/bin/sh

echo "Starting application ..."
exec java --add-opens java.base/java.lang=ALL-UNNAMED -Djava.security.egd=file:/dev/./urandom -jar /opt/logreposit/denkovi-relay-api/app.jar
