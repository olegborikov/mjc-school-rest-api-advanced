package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParameters;

import java.util.List;

public interface GiftCertificateDao extends CrudDao<GiftCertificate> {
    List<GiftCertificate> findByQueryParameters(GiftCertificateQueryParameters giftCertificateQueryParameters);
}
