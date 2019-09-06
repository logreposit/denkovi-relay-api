#!/bin/bash -x

set -eu

current_directory="$( cd "$(dirname "$0")" ; pwd -P )"

cd "${current_directory}/.."

git_describe_output="$(git describe)"

echo "Setting project.version to git describe output ${git_describe_output} in pom.xml ..."
mvn versions:set -DnewVersion="${git_describe_output}"

project_name="$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)"
project_version="$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)"

echo "Determined project.artifactId ${project_name} and project.version ${project_version}"

mvn clean package

echo "Copying final jar to shared build directory ..."

cp -v "target/${project_name}.jar" /srv/shared/app.jar

rm -f /srv/shared/project_name.txt
rm -f /srv/shared/project_version.txt

echo "${project_name}" > /srv/shared/project_name.txt
echo "${project_version}" > /srv/shared/project_version.txt
