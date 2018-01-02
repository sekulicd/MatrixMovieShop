#!/bin/bash
cd ${0%/*}
set -eu

docker build --rm -t movie_inventory .
docker run --rm --name movieinventory -p 8002:8080 movie_inventory