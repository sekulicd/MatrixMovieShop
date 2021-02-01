# MatrixMovieShop

MatrixMovieShop is a scalable solution which is designed as per CQRS / Event Sourcing architectural pattern.
The focus is on design and architecture which enables easy scalability and modularity.
Out of focus: database implementation (data stored in memory), writing unit tests.
It comprises four decoupled modules:
- MovieInventory - in charge of maintaining movie inventory
- MovieOrderingEngine -  in charge of renting and returning of movies
- PricingEngine - in charge of calculating and paying rent and return price
- BonusClub - in charge of maintaining customer bonuses

## Exposed API's:

### MovieInventory:
- addMovie - adds movie to Inventory
- getMovie - returns list of all movies in Inventory

### MovieOrderingEngine:
- rentMovie - places rent movie request (supports only renting of one movie)
- returnMovie - places return movie request
- getOrder - return order details

### PricingEngine
- getRentPrice - returns rent price for specific order
- payRentPrice - pay rent price for specific order
- getReturnPrice - returns return price for specific order
-  payReturnPrice - pay rent price for specific order
- BonusClub:
- getCustomerBonus - returns customer bonus

## Technology stack:
- Java SE 8/Java EE7
- Apache Kafka
- Docker
- Maven

## Build & run:
- Build modules:
    - `mvn clean install`
- Build & run kafka/zookeeper image
    - `cd kafka`
    - `docker-compose up -d`

- Build & run modules images
    - `cd MovieInventory`
    - `./build-run-local.sh`
    - `cd MovieOrderingEngine`
    - `./build-run-local.sh`
    - `cd PricingEngine`
    - `./build-run-local.sh`
    - `cd BonusClub`
    - `./build-run-local.sh`
- Optional: if necessery any for any module additional instance can be started, for example if ordering server MovieOrderingEngine has to big load new instance can be started
    - `cd MovieOrderingEngine`
    - `./build-run-local-additional-node.sh`

## Debugging:

Checking Kafka events:
- Navigate to bin folder of kafka installation

`./kafka-console-consumer.sh --bootstrap-server 192.168.99.100:9092  --topic movie_inventory --from-beginning`

`./kafka-console-consumer.sh --bootstrap-server 192.168.99.100:9092  --topic movie_orders --from-beginning`

`./kafka-console-consumer.sh --bootstrap-server 192.168.99.100:9092  --topic pricing_engine --from-beginning`

`./kafka-console-consumer.sh --bootstrap-server 192.168.99.100:9092  --topic bonus_topic --from-beginning`

## Testing application:

- Add movie to inventory

`curl -X POST -d '{"title":"LOTR3","movie_type":"OLD"}' http://192.168.99.100:8002/MovieInventory/casumo/inventory/add --header "Content-Type:application/json"`

- Check if movie is added

`curl http://192.168.99.100:8002/MovieInventory/casumo/inventory/movies`

- Rent movie

`curl -X POST -d '{"title":"LOTR3","rent_period":"2","customer_name":"dusan"}' http://192.168.99.100:8003/MovieOrderingEngine/casumo/ordering/rent/ --header "Content-Type:application/json"`

- GetPrice

`curl -X GET http://192.168.99.100:8004/PricingEngine/casumo/pricing/rentPrice/19033a5c-4b4e-46b1-8904-8f01a5ba4f0c`

- Pay Rent price

`curl -X POST -d '{"orderId":"19033a5c-4b4e-46b1-8904-8f01a5ba4f0c","rent_price":"30"}' http://192.168.99.100:8004/PricingEngine/casumo/pricing/payRentPrice/ --header "Content-Type:application/json"`

- Return movie

`curl -X POST -d '{"order_id":"19033a5c-4b4e-46b1-8904-8f01a5ba4f0c","return_date":"2018-01-20"}' http://192.168.99.100:8003/MovieOrderingEngine/casumo/ordering/return/ --header "Content-Type:application/json"`

- Get return price

`curl -X GET http://192.168.99.100:8004/PricingEngine/casumo/pricing/returnPrice/19033a5c-4b4e-46b1-8904-8f01a5ba4f0c`

- Pay return price

`curl -X POST -d '{"orderId":"19033a5c-4b4e-46b1-8904-8f01a5ba4f0c","return_price":"540"}' http://192.168.99.100:8004/PricingEngine/casumo/pricing/payReturnPrice/ --header "Content-Type:application/json"`

- Get Order Status

`curl -X GET http://192.168.99.100:8003/MovieOrderingEngine/casumo/ordering/19033a5c-4b4e-46b1-8904-8f01a5ba4f0c`

- Get Customer Bonus

`curl -X GET http://192.168.99.100:8001/BonusClub/casumo/bonusclub/customer/martina`
