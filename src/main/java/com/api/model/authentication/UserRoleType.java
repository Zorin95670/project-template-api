package com.api.model.authentication;

public final class UserRoleType {

    /**
     * Used for route with only valid connection with no roles.
     */
    public static final String ALL = "ALL";

    /**
     * Authorizations:
     * <p><b>Deployment:</b></p>
     * <ul>
     *  <li>create</li>
     *  <li>delete</li>
     *  <li>delete progress</li>
     * </ul>
     * <p><b>Project:</b></p>
     * <ul>
     *  <li>create</li>
     *  <li>delete</li>
     * </ul>
     * <p><b>User:</b></p>
     * <ul>
     *  <li>Enabled/Disabled default administrator</li>
     *  <li>Update default administrator password</li>
     * </ul>
     */
    public static final String ADMIN = "ADMIN";

    /**
     * Only for authorized project, authorizations:
     * <p><b>Deployment:</b></p>
     * <ul>
     *  <li>create</li>
     *  <li>delete</li>
     *  <li>delete progress</li>
     * </ul>
     * <p><b>Project:</b></p>
     * <ul>
     *  <li>create</li>
     *  <li>delete</li>
     * </ul>
     */
    public static final String PROJECT_MANAGER = "PROJECT_MANAGER";
}
