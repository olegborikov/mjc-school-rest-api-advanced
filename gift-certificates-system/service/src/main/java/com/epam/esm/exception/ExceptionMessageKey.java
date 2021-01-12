package com.epam.esm.exception;

import lombok.experimental.UtilityClass;

/**
 * Class {@code ExceptionMessageKey} presents keys by which messages will be taken from properties files.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class ExceptionMessageKey {

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.GiftCertificate}.
     */
    public final String GIFT_CERTIFICATE_NOT_FOUND_BY_ID = "giftCertificate.notFoundById";
    public final String INCORRECT_GIFT_CERTIFICATE_ID = "giftCertificate.incorrectId";
    public final String INCORRECT_GIFT_CERTIFICATE_NAME = "giftCertificate.incorrectName";
    public final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION = "giftCertificate.incorrectDescription";
    public final String INCORRECT_GIFT_CERTIFICATE_PRICE = "giftCertificate.incorrectPrice";
    public final String INCORRECT_GIFT_CERTIFICATE_DURATION = "giftCertificate.incorrectDuration";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.Tag}.
     */
    public final String TAG_NOT_FOUND_BY_ID = "tag.notFoundById";
    public final String INCORRECT_TAG_ID = "tag.incorrectId";
    public final String INCORRECT_TAG_NAME = "tag.incorrectName";

    /**
     * Key for exception messages associated with internal server exceptions.
     */
    public final String INTERNAL_ERROR = "internalError";
    /**
     * Keys for exception messages associated with HttpMediaTypeNotSupportedException.
     */
    public final String INCORRECT_MEDIA_TYPE = "incorrectMediaType";
}
