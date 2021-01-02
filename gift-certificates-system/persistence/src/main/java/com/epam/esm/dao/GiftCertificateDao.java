package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
