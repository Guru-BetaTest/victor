package com.dhemery.victor.frankly;

import com.dhemery.victor.frank.FranklyAgent;
import com.dhemery.victor.io.Endpoint;

import java.util.List;

/**
 * A Frankly agent that communicates with a Frank server through an {@link Endpoint}.
 */
public class EndpointFranklyAgent implements FranklyAgent {
    private final Endpoint endpoint;

    public EndpointFranklyAgent(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String appExec(String name, Object...arguments) {
        AppExecRequest request = new AppExecRequest(new Operation(name, arguments));
        MessageResponse response = request.sendTo(endpoint);
        return response.results().get(0);
    }

    @Override
    public List<String> map(String engine, String query, String name, Object...arguments) {
        MapRequest request = new MapRequest(engine, query, new Operation(name, arguments));
        MessageResponse response = request.sendTo(endpoint);
        return response.results();
    }

    @Override
    public String orientation() {
        OrientationRequest request = new OrientationRequest();
        OrientationResponse response = request.sendTo(endpoint);
        return response.orientation();
    }

    @Override
    public boolean ping() {
        PingRequest request = new PingRequest();
        PingResponse response = request.sendTo(endpoint);
        return response.responded();
    }

    @Override
    public void typeIntoKeyboard(String text) {
        TypeIntoKeyboardRequestBody request = new TypeIntoKeyboardRequestBody(text);
        request.sendTo(endpoint);
    }
}
