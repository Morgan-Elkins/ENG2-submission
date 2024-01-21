#!/bin/bash

pushd video-microservice
./gradlew dockerBuild
popd

pushd trending-microservice
./gradlew dockerBuild
popd

pushd subscription-microservice
./gradlew dockerBuild
popd

docker compose -p microservices-prod -f compose-prod.yml up -d