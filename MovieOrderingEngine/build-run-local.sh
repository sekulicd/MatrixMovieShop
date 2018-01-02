#!/bin/bash
cd ${0%/*}
set -eu

docker build --rm -t movie_order .
docker run --rm --name movieorder -p 8003:8080 movie_order