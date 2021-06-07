package com.api.model.authentication;

public enum AuthenticationType {

    /**
     * Default authentication type, in local database.
     */
    LOCAL("LOCAL");

    /**
     * Authentication type value.
     */
    private String value;

    private AuthenticationType(final String value) {
        this.value = value;
    }

    /**
     * Get authentication type value.
     * 
     * @return Authentication type value.
     */
    public String getValue() {
        return value;
    }
}
