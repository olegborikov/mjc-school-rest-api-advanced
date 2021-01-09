package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID))
                .name(rs.getString(ColumnName.GIFT_CERTIFICATE_NAME))
                .description(rs.getString(ColumnName.DESCRIPTION))
                .price(BigDecimal.valueOf(rs.getDouble(ColumnName.PRICE)))
                .duration(rs.getInt(ColumnName.DURATION))
                .createDate(rs.getObject(ColumnName.CREATE_DATE, LocalDateTime.class))
                .lastUpdateDate(rs.getObject(ColumnName.LAST_UPDATE_DATE, LocalDateTime.class))
                .build();
    }
}
