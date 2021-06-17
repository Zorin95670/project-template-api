package com.api.dao;

import org.springframework.stereotype.Repository;

import com.api.model.dto.count.CountDTO;

/**
 * Count DAO, to count object in database.
 */
@Repository("countDAO")
public class CountDAO extends CommonDAO<Object[], CountDTO> {

    /**
     * Default constructor.
     */
    public CountDAO() {
        super(null);
    }
}
