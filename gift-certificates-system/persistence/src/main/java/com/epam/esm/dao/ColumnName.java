package com.epam.esm.dao;

import lombok.experimental.UtilityClass;

/**
 * Class {@code ColumnName} contains names of table columns in database.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@UtilityClass
public class ColumnName {

    /**
     * Column names of gift_certificate table.
     */
    public final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    public final String GIFT_CERTIFICATE_NAME = "gift_certificate_name";
    public final String DESCRIPTION = "description";
    public final String GIFT_CERTIFICATE_PRICE = "gift_certificate_price";
    public final String DURATION = "duration";
    public final String GIFT_CERTIFICATE_CREATE_DATE = "gift_certificate_create_date";
    public final String LAST_UPDATE_DATE = "last_update_date";

    /**
     * Column names of tag table.
     */
    public final String TAG_ID = "tag_id";
    public final String TAG_NAME = "tag_name";

    /**
     * Column names of user table.
     */
    public final String USER_ID = "user_id";
    public final String USER_NAME = "user_name";
}
