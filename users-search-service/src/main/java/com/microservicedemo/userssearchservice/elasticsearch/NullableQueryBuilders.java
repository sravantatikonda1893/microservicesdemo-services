package com.microservicedemo.userssearchservice.elasticsearch;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;

import java.util.Collection;

public class NullableQueryBuilders {

    public static TermsQueryBuilder nullableTermsQuery(String name, Collection<?> values) {
        if (values == null) {
            return null;
        }

        return new TermsQueryBuilder(name, values);
    }

    public static MatchQueryBuilder nullableMatchQuery(String name, Object text) {
        if (text == null) {
            return null;
        }

        return new MatchQueryBuilder(name, text);
    }
}
