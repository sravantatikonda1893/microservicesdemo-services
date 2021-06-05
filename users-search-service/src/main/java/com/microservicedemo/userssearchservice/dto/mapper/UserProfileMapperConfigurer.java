package com.microservicedemo.userssearchservice.dto.mapper;

import com.addicts.dating.recommendations.document.UserProfileDocument;
import com.addicts.dating.recommendations.dto.UserProfileRecommendation;

import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;

@Component
public class UserProfileMapperConfigurer implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter("geoPointConverter", new GeoPointConverter());

        mapperFactory.classMap(UserProfileDocument.class, UserProfileRecommendation.class)
                .fieldMap("location", "location").converter("geoPointConverter").add()
                .byDefault()
                .register();
    }
}