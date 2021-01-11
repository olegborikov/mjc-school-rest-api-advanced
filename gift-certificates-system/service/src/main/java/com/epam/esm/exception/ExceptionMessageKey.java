package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessageKey {// TODO: 11.01.2021 documentation
    public final String GIFT_CERTIFICATE_NOT_FOUND_BY_ID = "giftCertificate.notFoundById";
    public final String INCORRECT_GIFT_CERTIFICATE_ID = "giftCertificate.incorrectId";
    public final String INCORRECT_GIFT_CERTIFICATE_NAME = "giftCertificate.incorrectName";
    public final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION = "giftCertificate.incorrectDescription";
    public final String INCORRECT_GIFT_CERTIFICATE_PRICE = "giftCertificate.incorrectPrice";
    public final String INCORRECT_GIFT_CERTIFICATE_DURATION = "giftCertificate.incorrectDuration";

    public final String TAG_NOT_FOUND_BY_ID = "tag.notFoundById";
    public final String INCORRECT_TAG_ID = "tag.incorrectId";
    public final String INCORRECT_TAG_NAME = "tag.incorrectName";
}
