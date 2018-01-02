#!/bin/bash
cd ${0%/*}
set -eu

docker build --rm -t bonus_club .
docker run --rm --name bonusclub -p 8001:8080 bonus_club