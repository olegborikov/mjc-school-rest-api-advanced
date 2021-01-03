package com.epam.esm.dao.impl;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String ADD = "INSERT INTO tag (tag_name) VALUES (?)";
    private static final String FIND_ALL = "SELECT tag_id, tag_name FROM tag";
    private static final String FIND_BY_ID = "SELECT tag_id, tag_name FROM tag WHERE tag_id = ?";
    private static final String REMOVE = "DELETE FROM tag WHERE tag_id = ?";
    private JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper = new TagMapper();

    @Autowired
    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        boolean isAdded = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            return statement;
        }, keyHolder) > 0;
        Number key = keyHolder.getKey();
        if (key != null) {
            tag.setId(key.longValue());
        }
        return isAdded;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, tagMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, rs -> {
            if (rs.next()) {
                Tag tag = Tag.builder()
                        .id(rs.getLong(ColumnName.TAG_ID))
                        .name(rs.getString(ColumnName.TAG_NAME))
                        .build();
                return Optional.of(tag);
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public boolean update(Tag tag) {
        throw new UnsupportedOperationException("Method update is unavailable for TagDaoImpl");
    }

    @Override
    public boolean remove(long id) {
        return jdbcTemplate.update(REMOVE, id) > 0;
    }
}
