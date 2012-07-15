package com.dhemery.victor.http;

import com.dhemery.victor.io.Connection;
import com.dhemery.victor.io.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnection implements Connection {
    public static final int READ_TIMEOUT = 30000;
    private final HttpURLConnection connection;

    public HttpConnection(String url) {
        connection = connectTo(makeUrl(url));
    }

    @Override
    public void write(String body) {
        try {
            connection.getOutputStream().write(body.getBytes());
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot write request body %s to %s", body, connection), cause);
        }
    }

    @Override
    public Response response() {
        Response response = responseFrom(connection);
        disconnectFrom(connection);
        return response;
    }

    static URL makeUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException cause) {
            throw new HttpException(String.format("Cannot create URL %s", url), cause);
        }
    }

    HttpURLConnection connectTo(URL url) {
        HttpURLConnection connection = openHttpConnection(url);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(true);
        connectTo(connection);
        return connection;
    }

    HttpURLConnection openHttpConnection(URL url) {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot open connection to %s", url), cause);
        }
    }

    void connectTo(URLConnection connection) {
        try {
            connection.connect();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot connect to %s", connection.getURL()), cause);
        }
    }

    Response responseFrom(HttpURLConnection connection) {
        return new HttpResponse(responseMessageFrom(connection), responseBodyFrom(connection));
    }

    String responseMessageFrom(HttpURLConnection connection) {
        try {
            return connection.getResponseMessage();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read response from %s", connection.getURL()), cause);
        }
    }

    String responseBodyFrom(HttpURLConnection connection) {
        return new HttpResponseBodyReader(connection).read();
    }

    void disconnectFrom(HttpURLConnection connection) {
        connection.disconnect();
    }
}