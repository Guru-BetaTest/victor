package com.dhemery.victor.frank;

import com.dhemery.victor.http.HttpRequest;

public interface FranklyListener {
    void willSendRequest(HttpRequest request);
    <T> void receivedResponse(HttpRequest request, T response);
}
