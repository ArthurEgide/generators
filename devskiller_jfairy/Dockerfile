FROM openjdk:8

WORKDIR /usr/generator

RUN apt install && apt upgrade && \
  apt install -y git curl maven

WORKDIR /usr/generator/jfairy


ENTRYPOINT [ "bash" ]