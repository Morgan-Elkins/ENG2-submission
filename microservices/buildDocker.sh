#!/bin/bash

# Build dockerfiles
pushd video-microservice
./gradlew dockerBuild
popd

pushd trending-microservice
./gradlew dockerBuild
popd

pushd subscription-microservice
./gradlew dockerBuild
popd

# Bring up each of the kafka clients
docker compose up -p microservices-prod kafka-0 kafka-1 kafka-2 -d

# Create topics
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-posted --replication-factor 3 --partitions 6
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-viewed --replication-factor 3 --partitions 6
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-liked --replication-factor 3 --partitions 6
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-disliked --replication-factor 3 --partitions 6
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-disliked --replication-factor 3 --partitions 6
# docker compose -p microservices-prod -f compose-prod.yml exec -e JMX_PORT= kafka-0 kafka-topics.sh --bootstrap-server kafka-0:9092 --create --topic video-posted-by-hour --replication-factor 3 --partitions 6

# Bring up microservices in one compose file
docker compose -p microservices-prod -f compose-prod.yml up -d