FROM node:18.18-alpine

RUN apk add curl

WORKDIR /usr/generator
COPY ./ /usr/generator
RUN npm install

ENTRYPOINT [ "/bin/sh" ]