FROM openjdk:8u191-jre-alpine3.8
RUN apk add curl jq
WORKDIR /usr/share/mouritech
ADD target/selenium-docker.jar selenium-docker.jar
ADD target/selenium-docker-tests.jar selenium-docker-tests.jar
ADD target/libs libs
ADD book-flight-module.xml book-flight-module.xml
ADD search-module.xml search-module.xml
ADD healthcheck.sh healthcheck.sh
ENTRYPOINT sh healthcheck.sh

