FROM dsdshcym/ruby-base:latest
MAINTAINER Yiming Chen <dsdshcym@gmail.com>

RUN apk update && apk upgrade && apk add ruby-json

RUN mkdir /usr/app
WORKDIR /usr/app

COPY Gemfile /usr/app
COPY Gemfile.lock /usr/app
RUN bundle install

COPY . /usr/app

CMD ruby -I ./lib/SAS ./bin/sas.rb
