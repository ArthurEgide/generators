FROM openjdk:8

WORKDIR /usr/generator

RUN apt update -y && apt upgrade -y

RUN apt install -y git curl

COPY ./ /usr/generator/

# RUN bash build.sh

# ENTRYPOINT [ "bash" ]