package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(ColumnName.TAG_ID));
        tag.setName(rs.getString(ColumnName.TAG_NAME));
        return tag;
    }
}
