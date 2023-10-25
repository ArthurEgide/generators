FROM openjdk:8

WORKDIR /usr/generator

RUN apt update -y && apt upgrade -y

RUN apt install -y git curl maven

WORKDIR /usr/generator/jfairy


ENTRYPOINT [ "bash" ]