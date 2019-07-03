#! /usr/bin/ruby

require 'json'

# through env var read info about repo
file = File.open(ENV['CRIVER_EVENTS_REPO'])
criver_data = JSON.load file

# do some operations with the data..
puts criver_data[0]
puts criver_data[0]["type"]
