#!/bin/bash
set -e

echo "Creating database: ${MYSQL_DATABASE}"

mysqladmin -u$MYSQL_USER -p$MYSQL_ROOT_PASSWORD create $MYSQL_DATABASE

echo "Creating schema..."

cat << EOF | mysql mydatabase
create table inventory
(
    product_id           INT AUTO_INCREMENT primary key,
    product_category     varchar(255),
    product_name         varchar(50),
    vendor_name          varchar(50),
    avail_quantity       INT,
    vendor_addrs         varchar(100),
    order_limit_qty      varchar(100),
    created_at           timestamp,
    updated_at           timestamp
);
EOF

#echo "Populating database..."
#psql -d ${DB_NAME} -a  -U${POSTGRES_USER} -f /data.sql