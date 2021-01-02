package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;

public interface TagDao extends CrudDao<Tag>{
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
