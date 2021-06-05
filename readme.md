To build:
    - mvn clean package docker:build
    - mvn clean package docker:push

To run Containers:
    - docker compose up -d

To run a users JDBC Source connector:
    - curl -sX POST http://localhost:8084/connectors -d @users-sync-source.json --header "Content-Type: application/json"

To run a orders File Source connector:
    - curl -sX POST http://localhost:8083/connectors -d @orders-connect-file-pulse-xml.json --header "Content-Type: application/json"

To run a products File Source connector:
    - curl -sX POST http://localhost:8083/connectors -d @products-connect-file-pulse-xml.json --header "Content-Type: application/json"

To run a users Mongo Sink connector:
    - curl -sX POST http://localhost:8085/connectors -d @products-connect-mongo-sink.json --header "Content-Type: application/json"

To run a orders ES Sink connector:
    - curl -sX POST http://localhost:8086/connectors -d @elasticsearch-orders-sink.json --header "Content-Type: application/json"

To run a users ES Sink connector:
    - curl -sX POST http://localhost:8086/connectors -d @elasticsearch-users-sink.json --header "Content-Type: application/json"

To get all connectors:
    - http://localhost:8084/connectors

To get status of source users connector:
    - http://localhost:8084/connectors/user-recs-jdbc-connector/status

To delete products source connector:
    - curl -X DELETE localhost:8083/connectors/products-source-connector