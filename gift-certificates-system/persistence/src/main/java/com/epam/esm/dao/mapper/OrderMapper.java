package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getLong(ColumnName.ORDER_ID))
                .price(rs.getBigDecimal(ColumnName.ORDER_PRICE))
                .createDate(rs.getObject(ColumnName.ORDER_CREATE_DATE, LocalDateTime.class))
                .user(User.builder()
                        .id(rs.getLong(ColumnName.USER_ID_FK))
                        .build())
                .giftCertificate(GiftCertificate.builder()
                        .id(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID_FK))
                        .build())
                .build();
    }
}
