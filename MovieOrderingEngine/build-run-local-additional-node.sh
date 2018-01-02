#!/bin/bash
cd ${0%/*}
set -eu

docker build --rm -t movie_order_additional_node .
docker run --rm --name movieorderadditionalnode -p 8005:8080 movie_order_additional_node