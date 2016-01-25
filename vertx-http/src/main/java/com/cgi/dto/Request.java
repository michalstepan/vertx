package com.cgi.dto;

import io.vertx.core.MultiMap;

/**
 * Created by stepanm on 18.1.2016.
 */
public class Request {
    private String version;
    private String method;
    private String uri;
    private String path;
    private String query;
    private MultiMap headers;
    private byte[] body;
    private boolean successfullyParsed;

    public Request() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public MultiMap getHeaders() {
        return headers;
    }

    public void setHeaders(MultiMap headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isSuccessfullyParsed() {
        return successfullyParsed;
    }

    public void setSuccessfullyParsed(boolean successfullyParsed) {
        this.successfullyParsed = successfullyParsed;
    }
}
