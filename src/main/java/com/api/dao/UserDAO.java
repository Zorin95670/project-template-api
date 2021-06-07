package com.api.dao;

import org.springframework.stereotype.Repository;

import com.api.model.entity.User;
import com.api.utils.predicate.filter.QueryFilter;

/**
 * DAO for user entity.
 */
@Repository("userDAO")
public class UserDAO extends CommonDAO<User, QueryFilter> {

    /**
     * Default constructor.
     */
    public UserDAO() {
        super(User.class);
    }

}
