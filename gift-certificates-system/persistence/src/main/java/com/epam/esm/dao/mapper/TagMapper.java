package com.epam.esm.dao.mapper;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class {@code TagMapper} used by {@link JdbcTemplate} for mapping
 * rows of a {@link java.sql.ResultSet} on {@link Tag}.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Component
public class TagMapper implements RowMapper<Tag> {

    /**
     * Method map each row of data in the ResultSet.
     * It is supposed to map values of the current row on {@link Tag}.
     *
     * @param rs     the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting column values
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag.builder()
                .id(rs.getLong(ColumnName.TAG_ID))
                .name(rs.getString(ColumnName.TAG_NAME))
                .build();
    }
}
