FROM php:7.0-cli-alpine


WORKDIR /usr/generator

RUN apk update -y
RUN apk add git

RUN git clone https://github.com/fzaninotto/Faker.git /usr/generator
RUN ls -lart

# Installing composer
RUN curl -sS https://getcomposer.org/installer -o composer-setup.php
RUN php composer-setup.php --install-dir=/usr/local/bin --filename=composer
RUN rm -rf composer-setup.php

COPY . /usr/generator


RUN composer install --ignore-platform-reqs

ENTRYPOINT [ "sh" ]

CMD [ "run" ]