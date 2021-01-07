package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.GiftCertificateQueryCreator;
import com.epam.esm.util.GiftCertificateQueryParameters;
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
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String ADD = "INSERT INTO gift_certificate (gift_certificate_name, description, price, "
            + "duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT gift_certificate_id, gift_certificate_name, description, price, "
            + "duration, create_date, last_update_date FROM gift_certificate";
    private static final String FIND_BY_ID = "SELECT gift_certificate_id, gift_certificate_name, description, price, "
            + "duration, create_date, last_update_date FROM gift_certificate WHERE gift_certificate_id = ?";
    private static final String UPDATE = "UPDATE gift_certificate SET "
            + "gift_certificate_name = ?, description = ?, price = ?, duration = ?, create_date = ?, "
            + "last_update_date = ? WHERE gift_certificate_id = ?";
    private static final String REMOVE = "DELETE FROM gift_certificate WHERE gift_certificate_id = ?";
    private static final String REMOVE_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag "
            + "WHERE gift_certificate_id_fk = ?";
    private static final String FIND_BY_QUERY_PARAMETERS = "SELECT gift_certificate_id, gift_certificate_name, "
            + "description, price, duration, create_date, last_update_date FROM gift_certificate "
            + "LEFT JOIN gift_certificate_has_tag ON gift_certificate.gift_certificate_id = gift_certificate_id_fk "
            + "LEFT JOIN tag ON gift_certificate_has_tag.tag_id_fk = tag_id";
    private static final String ADD_GIFT_CERTIFICATE_HAS_TAG = "INSERT INTO gift_certificate_has_tag "
            + "(gift_certificate_id_fk, tag_id_fk) VALUES (?, ?)";
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, giftCertificate.getName());
            statement.setString(2, giftCertificate.getDescription());
            statement.setBigDecimal(3, giftCertificate.getPrice());
            statement.setInt(4, giftCertificate.getDuration());
            statement.setObject(5, giftCertificate.getCreateDate());
            statement.setObject(6, giftCertificate.getLastUpdateDate());
            return statement;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            giftCertificate.setId(key.longValue());
        }
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL, giftCertificateMapper);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, giftCertificateMapper).stream()
                .findFirst();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        return giftCertificate;
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
    public List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters) {
        String condition = GiftCertificateQueryCreator.createQuery(giftCertificateQueryParameters);
        return jdbcTemplate.query(FIND_BY_QUERY_PARAMETERS + condition, giftCertificateMapper);
    }

    @Override
    public void addGiftCertificateHasTag(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        tags.forEach(tag -> jdbcTemplate.update(ADD_GIFT_CERTIFICATE_HAS_TAG, giftCertificate.getId(), tag.getId()));
    }
}
