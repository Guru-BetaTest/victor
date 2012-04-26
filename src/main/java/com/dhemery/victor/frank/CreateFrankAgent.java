package com.dhemery.victor.frank;

import java.util.Properties;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    /**
     * The value of {@link #DEFAULT_FRANK_HOST} if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of {@link #DEFAULT_FRANK_PORT} if the user does not supply a value.
     */
    public static final long DEFAULT_FRANK_PORT = 37265L;

    /**
     * Specifies the name of the host on which the Frank server listens for requests.
     * Do not include a scheme (e.g. "http://") at the start of this value.
     */
    public static final String FRANK_HOST_PROPERTY = "victor.frank.host";

    /**
     * Specifies the port on which the Frank server listens for requests.
     */
    public static final String FRANK_PORT_PROPERTY = "victor.frank.port";

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default {@link #DEFAULT_FRANK_HOST host}
     * and {@link #DEFAULT_FRANK_PORT port}.
     * @return the Frank agent.
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return forFrankServerUrl(DEFAULT_FRANK_HOST, DEFAULT_FRANK_PORT);
    }

    /**
     * Creates a Frank agent that interacts with the Frank server
     * at the given URL.
     * @param url the URL at which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent forFrankServerUrl(String url) {
        return new FrankAgent(url);
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at the given HTTP host and port.
     * The host name must not include the HTTP scheme (e.g. "http://").
     * @param host the name of the Frank server's host.
     * @param port the port on which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent forFrankServerUrl(String host, Long port) {
        return forFrankServerUrl(makeUrl(host, port));
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at a URL designated by property values.
     * See the field descriptions further information.
     *
     * @param properties properties that specify the URL at which the Frank server listens.
     * @return the Frank agent.
     */
    public static FrankAgent fromProperties(Properties properties) {
        return forFrankServerUrl(makeUrl(hostProperty(properties), portProperty(properties)));
    }

    private static String hostProperty(Properties properties) {
        return property(properties, FRANK_HOST_PROPERTY);
    }

    private static Long portProperty(Properties properties) {
        return longProperty(properties, FRANK_PORT_PROPERTY);
    }

    private static String makeUrl(String host, Long port) {
        return String.format("http://%s:%s", host, port);
    }

    private static Long longProperty(Properties properties, String propertyName) {
        return Long.parseLong(property(properties, propertyName));
    }

    private static String property(Properties properties, String propertyName) {
        if(!properties.stringPropertyNames().contains(propertyName)) {
            throw new RuntimeException("Missing value for property " + propertyName);
        }
        return properties.getProperty(propertyName);
    }
}