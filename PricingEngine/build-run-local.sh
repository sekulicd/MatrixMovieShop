#!/bin/bash
cd ${0%/*}
set -eu

docker build --rm -t pricing_engine .
docker run --rm --name pricingengine -p 8004:8080 pricing_engine