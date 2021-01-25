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
    public final String GIFT_CERTIFICATE_HAS_ID = "{giftCertificate.hasId}";
    public final String INCORRECT_GIFT_CERTIFICATE_ID = "{giftCertificate.incorrectId}";
    public final String INCORRECT_GIFT_CERTIFICATE_NAME = "{giftCertificate.incorrectName}";
    public final String INCORRECT_GIFT_CERTIFICATE_DESCRIPTION = "{giftCertificate.incorrectDescription}";
    public final String INCORRECT_GIFT_CERTIFICATE_PRICE = "{giftCertificate.incorrectPrice}";
    public final String INCORRECT_GIFT_CERTIFICATE_DURATION = "{giftCertificate.incorrectDuration}";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.Tag}.
     */
    public final String TAG_NOT_FOUND_BY_ID = "tag.notFoundById";
    public final String TAG_NOT_FOUND_BY_NAME = "tag.notFoundByName";
    public final String TAG_ALREADY_EXISTS = "tag.alreadyExists";
    public final String TAG_HAS_ID = "{tag.hasId}";
    public final String INCORRECT_TAG_ID = "{tag.incorrectId}";
    public final String INCORRECT_TAG_NAME = "{tag.incorrectName}";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.User}.
     */
    public final String USER_NOT_FOUND_BY_ID = "user.notFoundById";
    public final String INCORRECT_USER_ID = "{user.incorrectId}";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.entity.Order}.
     */
    public final String ORDER_NOT_FOUND_BY_ID = "order.notFoundById";
    public final String ORDER_HAS_ID = "{order.hasId}";
    public final String INCORRECT_ORDER_ID = "{order.incorrectId}";

    /**
     * Keys for exception messages associated with {@link com.epam.esm.util.Page}.
     */
    public final String INCORRECT_PAGE_NUMBER = "{page.incorrectNumber}";
    public final String INCORRECT_PAGE_SIZE = "{page.incorrectSize}";

    /**
     * Key for exception messages associated with internal server exceptions.
     */
    public final String INTERNAL_ERROR = "internalError";
    /**
     * Keys for exception messages associated with HttpMediaTypeNotSupportedException.
     */
    public final String INCORRECT_MEDIA_TYPE = "incorrectMediaType";
}
