FROM python:3.10-alpine3.18

RUN mkdir /usr/generator
WORKDIR /usr/generator/

COPY . /usr/generator/

RUN pip install Faker requests

ENTRYPOINT [ "sh" ]

CMD [ "run" ]