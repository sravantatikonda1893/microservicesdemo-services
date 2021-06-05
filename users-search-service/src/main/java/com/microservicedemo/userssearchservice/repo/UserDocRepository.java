package com.microservicedemo.userssearchservice.repo;

import com.microservicedemo.userssearchservice.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sravantatikonda
 */
@Repository
public interface UserDocRepository extends ElasticsearchRepository<UserDocument, Integer> {

}
