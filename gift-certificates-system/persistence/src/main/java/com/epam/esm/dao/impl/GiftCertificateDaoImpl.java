package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
