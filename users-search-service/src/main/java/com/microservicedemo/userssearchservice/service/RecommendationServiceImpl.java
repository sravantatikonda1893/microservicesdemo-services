package com.microservicedemo.userssearchservice.service;

import com.addicts.dating.recommendations.document.UserProfileDocument;
import com.addicts.dating.recommendations.dto.UserPreferencesDto;
import com.addicts.dating.recommendations.dto.UserProfileDto;
import com.addicts.dating.recommendations.repo.UserProfileDocRepository;
import com.addicts.dating.recommendations.security.JWTAuthenticationToken;
import com.addicts.dating.recommendations.service.RecommendationService;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

import static com.addicts.dating.recommendations.elasticsearch.NullableBoolQueryBuilder.nullableBoolQuery;
import static com.addicts.dating.recommendations.elasticsearch.NullableQueryBuilders.nullableMatchQuery;
import static com.addicts.dating.recommendations.elasticsearch.NullableQueryBuilders.nullableTermsQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.geoDistanceQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;
import static org.elasticsearch.script.ScriptType.INLINE;
import static org.elasticsearch.search.sort.SortBuilders.fieldSort;
import static org.elasticsearch.search.sort.SortBuilders.geoDistanceSort;
import static org.elasticsearch.search.sort.SortOrder.ASC;
import static org.elasticsearch.search.sort.SortOrder.DESC;

/**
 * @author sravantatikonda
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private final UserProfileDocRepository userProfileDocRepository;

    private final MapperFacade mapperFacade;

    @Override
    public UserProfileDocument save(final UserProfileDto userProfileDto) {
        final UserProfileDocument userProfileDocument = mapperFacade.map(userProfileDto, UserProfileDocument.class);
        userProfileDocument.setLocation(new GeoPoint(userProfileDto.getLat(), userProfileDto.getLon()));
        log.info("Creating (updating) index: {}", userProfileDocument.getUserId());

        return userProfileDocRepository.save(userProfileDocument);
    }

    @Override
    public void delete(int userId) {
        log.info("Deleting profile. UserId: {}", userId);
        userProfileDocRepository.deleteById(userId);
    }

    @Override
    public Page<UserProfileDocument> getRecommendations(final UserPreferencesDto userPreferencesDto,
                                                        final int radius, final String unit, final Pageable pageable,
                                                        final JWTAuthenticationToken user) {
        final DistanceUnit distanceUnit = DistanceUnit.fromString(unit);
        final BoolQueryBuilder recommendationsQueryBuilder = buildQuery(userPreferencesDto, radius, distanceUnit, user);
        final SortBuilder locationSortBuilder = buildLocationSort(userPreferencesDto, distanceUnit);
        final SortBuilder activitySortBuilder = buildActivitySort();
        final ScriptField distanceField = buildDistanceField(userPreferencesDto, distanceUnit);

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(recommendationsQueryBuilder)
                .withSourceFilter(new FetchSourceFilter(new String[]{"*"}, null))
                .withScriptField(distanceField)
                .withPageable(pageable)
                .withSort(locationSortBuilder)
                .withSort(activitySortBuilder)
                .build();

        return userProfileDocRepository.search(searchQuery);
    }

    private BoolQueryBuilder buildQuery(final UserPreferencesDto userPreferencesDto, final int radius,
                                        final DistanceUnit distanceUnit, final JWTAuthenticationToken user) {
        final BoolQueryBuilder userExclusionQueryBuilder = boolQuery()
                .mustNot(matchQuery("userId", user.getCredentials().getUserId()));
        final BoolQueryBuilder preferencesQueryBuilder = nullableBoolQuery()

                .nullableMust(nullableTermsQuery("diet", userPreferencesDto.getDietPrefers()))
                .nullableMust(nullableTermsQuery("sexualOrientation", userPreferencesDto.getSexOrientationPrefs()))
                .nullableMust(nullableMatchQuery("wantChildren", userPreferencesDto.getWantChildrenPref()))
                .nullableMust(nullableMatchQuery("gender", userPreferencesDto.getGenderPref()))

                .must(rangeQuery("height")
                        .from(userPreferencesDto.getMinHeightPref())
                        .to(userPreferencesDto.getMaxHeightPref()))
                .must(rangeQuery("age")
                        .from(userPreferencesDto.getMinAgePref())
                        .to(userPreferencesDto.getMaxAgePref()));

        final GeoDistanceQueryBuilder geoDistanceQueryBuilder = geoDistanceQuery("location")
                .point(userPreferencesDto.getLat(), userPreferencesDto.getLon())
                .distance(radius, distanceUnit);

        return boolQuery()
                .must(userExclusionQueryBuilder)
                .must(preferencesQueryBuilder)
                .must(geoDistanceQueryBuilder);
    }

    private SortBuilder buildLocationSort(final UserPreferencesDto userPreferencesDto, final DistanceUnit distanceUnit) {

        return geoDistanceSort("location",
                userPreferencesDto.getLat(), userPreferencesDto.getLon())
                .unit(distanceUnit)
                .order(ASC);
    }

    private SortBuilder buildActivitySort() {

        return fieldSort("lastLogin").order(DESC);
    }

    private ScriptField buildDistanceField(final UserPreferencesDto userPreferencesDto, final DistanceUnit distanceUnit) {
        final double distanceScaleFromMeters = 1.0 / distanceUnit.toMeters(1.0);

        final Map<String, Object> locationParams = new HashMap<>();
        locationParams.put("latitude", userPreferencesDto.getLat());
        locationParams.put("longitude", userPreferencesDto.getLon());
        locationParams.put("scaleFromMeters", distanceScaleFromMeters);

        final String script = "double distance = doc['location'].arcDistance(params.latitude, params.longitude); " +
                "double calculatedDistance = distance * params.scaleFromMeters; " +
                "calculatedDistance";

        return new ScriptField("distance",
                new Script(INLINE, DEFAULT_SCRIPT_LANG, script, locationParams));
    }
}
