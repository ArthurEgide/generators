FROM openjdk:8

WORKDIR /usr/generator

RUN apt update -y && apt upgrade -y

RUN apt install -y git curl vim postgresql

# COPY ./ /usr/generator/

# RUN bash build.sh

ENTRYPOINT [ "bash" ]

# CMD [ "run" ]