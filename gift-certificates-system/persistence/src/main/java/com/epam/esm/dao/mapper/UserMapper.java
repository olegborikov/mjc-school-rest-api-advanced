package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong(ColumnName.USER_ID))
                .name(rs.getString(ColumnName.USER_NAME))
                .build();
    }
}
