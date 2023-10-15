FROM node:18.18-alpine

WORKDIR /generator

COPY package.json .
COPY . .

RUN npm install

ENTRYPOINT [ "/bin/sh" ]