package com.satish.mvvmapp.utils;

public class NoConnectivityEvent {

    public static NoConnectivityEvent instance() {
        return new NoConnectivityEvent();
    }

    private NoConnectivityEvent() {

    }
}
