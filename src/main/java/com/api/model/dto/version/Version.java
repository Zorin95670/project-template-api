package com.api.model.dto.version;

/**
 * Version of project class.
 */
public class Version {

    /**
     * Version number.
     */
    private String versionNumber;

    /**
     * Default constructor.
     *
     * @param version
     *            Version number.
     */
    public Version(final String version) {
        this.setVersion(version);
    }

    /**
     * Get version number.
     *
     * @return Version number.
     */
    public final String getVersion() {
        return this.versionNumber;
    }

    /**
     * Set version number.
     *
     * @param version
     *            Version number.
     */
    public final void setVersion(final String version) {
        this.versionNumber = version;
    }
}
