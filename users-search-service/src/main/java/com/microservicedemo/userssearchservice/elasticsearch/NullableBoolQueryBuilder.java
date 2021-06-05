package com.microservicedemo.userssearchservice.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class NullableBoolQueryBuilder extends BoolQueryBuilder {

    public static NullableBoolQueryBuilder nullableBoolQuery() {
        return new NullableBoolQueryBuilder();
    }

    public NullableBoolQueryBuilder nullableMust(QueryBuilder queryBuilder) {
        if (queryBuilder == null) {
            return this;
        }

        return (NullableBoolQueryBuilder) super.must(queryBuilder);
    }
}
