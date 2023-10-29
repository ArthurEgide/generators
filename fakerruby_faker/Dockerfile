FROM ruby:3.2.1-alpine

WORKDIR /usr/generator

RUN apk update

COPY . /usr/generator

RUN gem install 'faker'

ENTRYPOINT [ "sh" ]

CMD [ "run" ]