package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID));
        giftCertificate.setName(rs.getString(ColumnName.GIFT_CERTIFICATE_NAME));
        giftCertificate.setDescription(rs.getString(ColumnName.DESCRIPTION));
        giftCertificate.setPrice(BigDecimal.valueOf(rs.getDouble(ColumnName.PRICE)));
        giftCertificate.setDuration(rs.getInt(ColumnName.DURATION));
        giftCertificate.setCreateDate(LocalDateTime.of(rs.getDate(ColumnName.CREATE_DATE).toLocalDate(),
                rs.getTime(ColumnName.CREATE_DATE).toLocalTime()));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(rs.getDate(ColumnName.LAST_UPDATE_DATE).toLocalDate(),
                rs.getTime(ColumnName.LAST_UPDATE_DATE).toLocalTime()));
        return giftCertificate;
    }
}
