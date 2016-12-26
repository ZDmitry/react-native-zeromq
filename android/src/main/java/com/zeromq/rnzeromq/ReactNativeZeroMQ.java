package com.zeromq.rnzeromq;

import java.util.HashMap;
import java.lang.String;
import java.lang.Boolean;
import java.util.Iterator;
import java.util.Map;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

// ZeroMQ imports ...


class ReactNativeZeroMQ extends ReactContextBaseJavaModule {

    ReactNativeZeroMQ(final ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private Boolean _init(final String jsonConfig) throws Exception {
        return true;
    }

    private Boolean _destroy() throws Exception {
        return true;
    }

    @Override
    public String getName() {
        return "ReactNativeZeroMQAndroid";
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void start(final String config, Callback callback) {
        (new ReactTask(callback) {
            @Override
            Object run() throws Exception {
                ReactNativeZeroMQ.this._init(config);
                return this._successResult(true);
            }
        }).start();
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void destroy(Callback callback) {
        (new ReactTask(callback) {
            @Override
            Object run() throws Exception {
                Boolean success = ReactNativeZeroMQ.this._destroy();
                return this._successResult(success);
            }
        }).start();
    }

}
