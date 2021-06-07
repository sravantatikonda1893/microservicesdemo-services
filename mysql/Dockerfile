FROM mysql:5.7.20

# Custom initialization scripts
COPY create_db.sh /docker-entrypoint-initdb.d/20-create_db.sh

RUN chmod +x /docker-entrypoint-initdb.d/20-create_db.sh