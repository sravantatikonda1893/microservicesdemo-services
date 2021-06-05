package com.microservicedemo.userssearchservice.dto.mapper;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class GeoPointConverter extends BidirectionalConverter<GeoPoint, GeoPoint> {

    @Override
    public GeoPoint convertTo(GeoPoint source, Type<GeoPoint> destinationType, MappingContext mappingContext) {
        if (source != null) {
            return new GeoPoint(source.getLat(), source.getLon());
        }

        return null;
    }

    @Override
    public GeoPoint convertFrom(GeoPoint source, Type<GeoPoint> destinationType, MappingContext mappingContext) {
        if (source != null) {
            return new GeoPoint(source.getLat(), source.getLon());
        }

        return null;
    }
}
