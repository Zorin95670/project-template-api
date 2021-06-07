package com.api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Classe that contains all configuration properties in application.properties.
 */
@Component
public class Configuration {

    /**
     * Project's version.
     */
    @Value("${api.version}")
    private String version;

    /**
     * Default value for API max result size.
     */
    @Value("${api.default.count}")
    private int defaultApiCount;

    /**
     * Maximum value for API max result size.
     */
    @Value("${api.maximum.count}")
    private int maximumApiCount;

    /**
     * Maximum retry count for RetryExecutor.
     */
    @Value("${retry.executor.max.try:3}")
    private int maxTryLimit;

    /**
     * Time in milliseconds to wait before each retry in RetryExecutor.
     */
    @Value("${retry.executor.time.wait:100}")
    private int delayBeforeRetry;

    /**
     * Get project's version.
     *
     * @return the project's version.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Get the default API max result size.
     *
     * @return The default API max result size.
     */
    public int getDefaultApiCount() {
        return this.defaultApiCount;
    }

    /**
     * Get the maximum API max result size.
     *
     * @return the maximum API max result size.
     */
    public int getMaximumApiCount() {
        return this.maximumApiCount;
    }

    /**
     * Maximum retry count for RetryExecutor.
     *
     * @return Retry count.
     */
    public int getMaximumTryLimit() {
        return maxTryLimit;
    }

    /**
     * Number of milliseconds to wait before each retry in RetryExecutor.
     *
     * @return Number of milliseconds.
     */
    public int getDelayBeforeRetry() {
        return delayBeforeRetry;
    }
}
