package com.example.sebastian.statki;

/**
 * Created by Sebastian on 2016-04-17.
 */
public abstract class RequestResult {
    public Request request;

    public RequestResult(Request r){
        request = r;
    }

    abstract public void onComplete();
}
