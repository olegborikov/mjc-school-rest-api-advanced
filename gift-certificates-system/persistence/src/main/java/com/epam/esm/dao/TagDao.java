package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends CrudDao<Tag> {
    List<Tag> findByGiftCertificateId(long id);

    Optional<Tag> isExists(String name);
}
