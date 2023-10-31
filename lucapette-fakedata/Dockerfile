FROM node:18.18-alpine

WORKDIR /usr/generator

RUN apk update && apk upgrade && \
    apk add --no-cache bash git openssh postgresql curl uuidgen


COPY . .

RUN chmod 777 fakedata

EXPOSE 8080

ENTRYPOINT [ "bash" ]

CMD [ "run" ]