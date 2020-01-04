#!/bin/bash
set -eu

docker build . -t logreposit/wiremock

docker run --rm -p 8080:8080 -v "$(pwd)/mappings":/home/wiremock/mappings --name logreposit-wiremock logreposit/wiremock

