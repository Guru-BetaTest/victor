package com.dhemery.victor.frank;

import java.util.Properties;

import static com.dhemery.victor.frank.FrankAgentConfigurationProperties.HOST;
import static com.dhemery.victor.frank.FrankAgentConfigurationProperties.PORT;

/**
 * Factory methods to create Frank agents.
 */
public class CreateFrankAgent {
    /**
     * The value of the {@link FrankAgentConfigurationProperties#HOST} property if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_HOST = "localhost";

    /**
     * The value of the {@link FrankAgentConfigurationProperties#PORT} property if the user does not supply a value.
     */
    public static final String DEFAULT_FRANK_PORT = "37265";

    /**
     * Create a Frank agent that interacts with a Frank server
     * at the default host and port.
     *
     * @return the Frank agent.
     * @see #DEFAULT_FRANK_HOST
     * @see #DEFAULT_FRANK_PORT
     */
    public static FrankAgent forDefaultFrankServerUrl() {
        return fromProperties(new Properties());
    }

    /**
     * Create a Frank agent that interacts with the Frank server
     * at the given URL.
     *
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
     *
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
     * See {@link FrankAgentConfigurationProperties} for definitions of properties.
     *
     * @param properties properties that specify the URL at which the Frank server listens.
     * @return the Frank agent.
     */
    // todo Make this take a map.
    public static FrankAgent fromProperties(Properties properties) {
        return forFrankServerUrl(makeUrl(hostProperty(properties), Long.parseLong(portProperty(properties))));
    }

    private static String hostProperty(Properties properties) {
        return properties.getProperty(HOST, DEFAULT_FRANK_HOST);
    }

    private static String portProperty(Properties properties) {
        return properties.getProperty(PORT, DEFAULT_FRANK_PORT);
    }

    private static String makeUrl(String host, Long port) {
        return String.format("http://%s:%s", host, port);
    }
}
