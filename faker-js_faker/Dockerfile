FROM node:18.18-alpine

WORKDIR /generator

RUN apk add curl

COPY package.json .
COPY . .

RUN npm install

ENTRYPOINT [ "/bin/sh" ]