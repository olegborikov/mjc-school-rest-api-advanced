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
        return GiftCertificate.builder()
                .id(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID))
                .name(rs.getString(ColumnName.GIFT_CERTIFICATE_NAME))
                .description(rs.getString(ColumnName.DESCRIPTION))
                .price(BigDecimal.valueOf(rs.getDouble(ColumnName.PRICE)))
                .duration(rs.getInt(ColumnName.DURATION))
                .createDate(LocalDateTime.of(rs.getDate(ColumnName.CREATE_DATE).toLocalDate(),
                        rs.getTime(ColumnName.CREATE_DATE).toLocalTime()))
                .lastUpdateDate(LocalDateTime.of(rs.getDate(ColumnName.LAST_UPDATE_DATE).toLocalDate(),
                        rs.getTime(ColumnName.LAST_UPDATE_DATE).toLocalTime()))
                .build();
    }
}
