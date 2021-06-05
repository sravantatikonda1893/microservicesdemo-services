package com.microservicedemo.userssearchservice.dto.mapper;

import com.microservicedemo.userssearchservice.document.UserDocument;
import com.microservicedemo.userssearchservice.dto.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author sravantatikonda
 */
@Component
@RequiredArgsConstructor
public class UserProfileMapper {

  private final MapperFacade mapperFacade;

  public Page<User> mapAsPage(final Pageable pageable, final Page<UserDocument> page) {
    final List<User> userProfileRecommendations =
        mapperFacade.mapAsList(page.getContent(), User.class);

    return new PageImpl<>(userProfileRecommendations, pageable, page.getTotalElements());
  }
}
