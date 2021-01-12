package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class {@code GiftCertificateMapper} used by {@link JdbcTemplate}
 * for mapping rows of a {@link java.sql.ResultSet} on {@link GiftCertificate}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {

    /**
     * Method map each row of data in the ResultSet.
     * It is supposed to map values of the current row on {@link GiftCertificate}.
     *
     * @param rs     the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting column values
     */
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
