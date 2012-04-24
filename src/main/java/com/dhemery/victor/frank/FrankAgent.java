package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.MessageResponseParser;
import com.dhemery.victor.frank.messages.OrientationResponse;
import com.dhemery.victor.frank.frankly.*;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An agent that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
public class FrankAgent implements IosViewAgent, IosApplicationAgent {
    public static final String DEFAULT_FRANK_SERVER_DOMAIN_NAME = "localhost";
    public static final Long DEFAULT_FRANK_SERVER_PORT = 37265L;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String frankServerUrl;
    private final Gson gson;


    /**
     * @param frankServerUrl The URL where the Frank server listens for requests.
     */
    public FrankAgent(String frankServerUrl) {
        this.frankServerUrl = frankServerUrl;
        gson = new GsonBuilder()
                .registerTypeAdapter(MessageResponse.class, new MessageResponseParser())
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Sends a GET request to the Frank server.
     * @return true if the Frank server responds to the request, otherwise false.
     */
    @Override
    public boolean isReady() {
        new PingRequest().sendTo(frankServerUrl);
        return true;
    }

    @Override
    public OrientationResponse orientation() {
        return sendRequest(new ApplicationOrientationRequest(), OrientationResponse.class);
    }

    @Override
    public MessageResponse sendApplicationMessage(Message message) {
        return sendMessageRequest(new ApplicationMessageRequest(message));
    }

    private MessageResponse sendMessageRequest(HttpRequest request) {
        return sendRequest(request, MessageResponse.class);
    }

    @Override
    public MessageResponse sendViewMessage(By query, Message message) {
        return sendMessageRequest(new ViewMessageRequest(query, message));
    }

    private <T> T sendRequest(HttpRequest request, Class<T> resultsClass) {
        HttpResponse response = request.sendTo(frankServerUrl);
        T results = gson.fromJson(response.body(), resultsClass);
        log.trace("Results from {} ==> {}", request, results);
        return results;
    }

    @Override
    public String toString() {
        return String.format("Frank client (%s)", frankServerUrl);
    }
}
