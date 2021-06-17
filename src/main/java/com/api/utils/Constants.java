package com.api.utils;

/**
 * All constants values.
 */
public final class Constants {

    /**
     * Private constructor. Do not call it.
     */
    private Constants() {
        throw new UnsupportedOperationException();
    }

    /**
     * Maximum length of string in database.
     */
    public static final int MAXIMUM_DB_STRING = 255;

    /**
     * Long length of string in database.
     */
    public static final int LONG_DB_STRING = 200;

    /**
     * Default length of string in database.
     */
    public static final int DEFAULT_DB_STRING = 100;

    /**
     * Short length of string in database.
     */
    public static final int SHORT_DB_STRING = 50;

    /**
     * "field" string key.
     */
    public static final String FIELD_KEY = "field";

    /**
     * Name of entry id field.
     */
    public static final String FIELD_ENTRY_ID = "entryId";

    /**
     * Name of progressRule field.
     */
    public static final String FIELD_PROGRESS_RULE = "progressRule";

    /**
     * Name of priority field.
     */
    public static final String FIELD_PRIORITY = "priority";

    /**
     * Name of comment field.
     */
    public static final String FIELD_COMMENT = "content";

    /**
     * Name of Activiti status field.
     */
    public static final String FIELD_ACTIVITI_STATUS = "Activiti status";

    /**
     * Name of status field.
     */
    public static final String FIELD_STATUS = "status";

    /**
     * Name of cleared field.
     */
    public static final String FIELD_CLEARED = "cleared";

    /**
     * Constant representing the status code 500.
     */
    public static final int ERROR_500 = 500;

    /**
     * Maximum progress value.
     */
    public static final int MAXIMUM_PROGRESS_VALUE = 100;

    /**
     * Websockes ping value.
     */
    public static final String WEBSOCKET_PING_VALUE = "ping";

    /**
     * Platform entry ID field name.
     */
    public static final String PLATFORM_ENTRY_ID_NAME = "platformEntryId";

    /**
     * Triple placeholder URI.
     */
    public static final String TRIPLE_PLACEHOLDER_URI = "%s%s/%s";

    /**
     * Runtime executions endpoint on Activiti.
     */
    public static final String RUNTIME_EXECUTIONS_ENDPOINT = "runtime/executions";

    /**
     * PUT activiti logging placeholder.
     */
    public static final String PUT_ACTIVITI_LOGGING = "PUT on Activiti with uri {} and body {} got status {}";

    /**
     * Cancel platform signal sent to Activiti.
     */
    public static final String CANCEL_PLATFORM_SIGNAL = "cancel_platform";

    /**
     * Platform name replacement regex for cancel variable.
     */
    public static final String PLATFORM_NAME_REPLACEMENT_REGEX = "[ -]+";
}
