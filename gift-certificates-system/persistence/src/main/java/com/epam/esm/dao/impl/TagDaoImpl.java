package com.epam.esm.dao.impl;

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
    private static final String FIND_BY_NAME = "SELECT tag_id, tag_name FROM tag WHERE tag_name LIKE ?";
    private static final String FIND_BY_GIFT_CERTIFICATE_ID = "SELECT tag_id, tag_name FROM tag "
            + "INNER JOIN gift_certificate_has_tag ON tag.tag_id = gift_certificate_has_tag.tag_id_fk WHERE "
            + "gift_certificate_has_tag.gift_certificate_id_fk = ?";
    private static final String REMOVE = "DELETE FROM tag WHERE tag_id = ?";
    private static final String REMOVE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag "
            + "WHERE tag_id_fk = ?";
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getName());
            return statement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            tag.setId(key.longValue());
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL, tagMapper);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, tagMapper).stream()
                .findFirst();
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("Method update is unavailable for TagDaoImpl");
    }

    @Override
    public void remove(long id) {
        jdbcTemplate.update(REMOVE, id);
    }

    @Override
    public void removeGiftCertificateHasTag(long id) {
        jdbcTemplate.update(REMOVE_GIFT_CERTIFICATE_HAS_TAG, id);
    }

    @Override
    public List<Tag> findByGiftCertificateId(long id) {
        return jdbcTemplate.query(FIND_BY_GIFT_CERTIFICATE_ID, new Object[]{id}, tagMapper);
    }

    @Override
    public Optional<Tag> isExists(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, new Object[]{name}, tagMapper).stream()
                .findFirst();
    }
}
