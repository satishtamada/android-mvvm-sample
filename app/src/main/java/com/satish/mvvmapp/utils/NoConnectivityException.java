package com.satish.mvvmapp.utils;

import android.content.Context;

import com.satish.mvvmapp.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    private Context context;

    public NoConnectivityException(Context context) {
        this.context = context;
    }

    @Override
    public String getMessage() {
        return context.getString(R.string.error_no_internet_connection);
    }
}
