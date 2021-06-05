package com.microservicedemo.usersservice.repo;

import com.microservicedemo.usersservice.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author sravantatikonda
 */
@Repository
public interface UserRepository extends IBaseRepository<User, Integer>,
    JpaSpecificationExecutor<User> {

}
