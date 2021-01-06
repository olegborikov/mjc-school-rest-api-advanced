package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao extends CrudDao<Tag> {
    List<Tag> findByGiftCertificateId(long id);

    void removeFromCrossTable(long id);
}
