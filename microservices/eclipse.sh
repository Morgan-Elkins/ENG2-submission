#!/bin/bash

pushd video-microservice
./gradlew eclipse
popd

pushd trending-microservice
./gradlew eclipse
popd

pushd subscription-microservice
./gradlew eclipse
popd

pushd client/video-cli
./gradlew eclipse
popd

pushd client/trending-cli
./gradlew eclipse
popd

pushd client/subscription-cli
./gradlew eclipse
popd