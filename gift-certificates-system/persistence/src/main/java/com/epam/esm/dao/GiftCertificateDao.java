package com.epam.esm.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public interface GiftCertificateDao {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
