#!/bin/sh

set -eu

current_directory="$( cd "$(dirname "$0")" ; pwd -P )"

cd "${current_directory}/.."

cat /srv/shared/project_name.txt
cat /srv/shared/project_version.txt

project_name="$(cat /srv/shared/project_name.txt)"
project_version="$(cat /srv/shared/project_version.txt)"
docker_image_tag="logreposit/${project_name}:${project_version}"
docker_directory="${current_directory}/../docker"

echo "Building docker image ${docker_image_tag} ..."
cp /srv/shared/app.jar "${docker_directory}/app.jar"
cd "${docker_directory}"
docker build -t "${docker_image_tag}" .
echo "Successfully built image ${docker_image_tag}"

echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin

echo "Pushing docker image ${docker_image_tag} ..."
docker push "${docker_image_tag}"
echo "Successfully pushed ${docker_image_tag}"
