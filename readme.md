**To build:**

    - mvn clean package docker:build
    - mvn clean package docker:push

**To run Containers:**
    - docker compose up -d

**To run a users JDBC Source connector:**
    - curl -sX POST http://localhost:8084/connectors -d @users-sync-source.json --header "Content-Type: application/json"

**To run a orders File Source connector:**
    - curl -sX POST http://localhost:8083/connectors -d @orders-connect-file-pulse-xml.json --header "Content-Type: application/json"

**To run a products File Source connector:**
    - curl -sX POST http://localhost:8083/connectors -d @products-connect-file-pulse-xml.json --header "Content-Type: application/json"

**To run a users Mongo Sink connector:**
    - curl -sX POST http://localhost:8085/connectors -d @products-connect-mongo-sink.json --header "Content-Type: application/json"

**To run a orders ES Sink connector:**
    - curl -sX POST http://localhost:8086/connectors -d @elasticsearch-orders-sink.json --header "Content-Type: application/json"

**To run a users ES Sink connector:**
    - curl -sX POST http://localhost:8086/connectors -d @elasticsearch-users-sink.json --header "Content-Type: application/json"

**To get all connectors:**
    - http://localhost:8084/connectors

**To get status of source users connector:**
    - http://localhost:8084/connectors/user-recs-jdbc-connector/status

**To delete products source connector:**
    - curl -X DELETE localhost:8083/connectors/products-source-connector
    
**Containers**
  1. ZK
2. Kafka broker
3. SR
4. JDBC Connect
5. File pulse connect
6. MongoDB Connect: https://github.com/Mousavi310/mongo-connect
7. Mongodb
8. Postgres
9. Kafdrop UI
10. ES Connect
11. Kibana to view indices
12. ES Service
13. Redis
14. Sql server
15. mySql
16. Graylog
17. users-search, orders, users, inventory, shipping, product service containers  



**Services:**

1. Create a microservice(users-service), generates records to DB - Postgres
	1. Create an insert/update user API
	2. Create a Kafka connect to source into a topic "users". Supports sourcing of both new and existing records(immutable topic)
2. Create a microservice(orders-service) - mongo
	1. Generate dummy orders XML file
	2. Source "orders" into topics
	3. Sink "orders" into MongoDB
	4. An Order create API, saves to MongoDB, sends a notification to Kafka, which will be consumed by "Inventory Service"(deducts a product count with a productId), "Shipping"(sends an email that its shipped with tracking number) microservices.
	5. Create an Order cancel API, sends a notification to Kafka,  which will be consumed by "Inventory Service"(increments the product count with a productId), "Shipping"(sends an email that its shipping is cancelled) microservices.
	6. Find Order APIs with filtering on specific fields and pagination.
3. Create a microservice(users-search-service) - ESS
	1. With ES index sinking "users" topic into "users" index
	2. With ES index sinking "orders" topic into "orders" index
	3. Expose an API with filter parameters and pagination.
	4. Expose an API with filter parameters and pagination.
4. Create a microservice(Shipping-service) with Sql server as DB
5. Create a microservice(Product-service) with Redis cache as DB
	1. Load Products into XML file
	2. Sink Products into Redis using connect
	3. Create product
	4. update product
6. Create a microservice(Inventory-service) with Mysql as DB
7. Logs through some aggregation tool like graylog
8. Dockerize all services
9. Cluster Containers orchestration through Kubernetes and Helm
10. Use Cassandra
