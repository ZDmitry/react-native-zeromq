package org.zeromq.rnzeromq;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.lang.String;
import java.lang.Boolean;
import java.util.Iterator;
import java.util.Map;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import android.os.AsyncTask;


class ReactNativeZeroMQ extends ReactContextBaseJavaModule {

    private String serverAddress = "";
    private Thread zeroMQThread;

    ReactNativeZeroMQ(final ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private Boolean _init(final ReadableMap config) {
        if (zeroMQThread == null) {
            serverAddress = config.getString("server");
            zeroMQThread  = new Thread(new ZeroMQServer(serverAddress));
            zeroMQThread.start();
            return true;
        }
        return false;
    }

    private String _getDeviceIdentifier() {
        return ("android.os.Build." + ReactNativeUtils.getDeviceName() + " " + ReactNativeUtils.getIPAddress(true));
    }

    private Boolean _disconnect() throws Exception {
        if (zeroMQThread != null) {
            zeroMQThread.interrupt();
            zeroMQThread = null;
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "ReactNativeZeroMQAndroid";
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void connect(final ReadableMap config, final Callback callback) {
        (new ReactTask(callback) {
            @Override
            Object run() throws Exception {
                Boolean success = ReactNativeZeroMQ.this._init(config);
                return this._successResult(success);
            }
        }).start();
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void sendMessage(final String message, final Callback callback) {
        if (serverAddress.length() > 0) {
            new ZeroMQTask(serverAddress, new Handler() {
                @Override
                public void handleMessage(final Message msg) {
                    (new ReactTask(callback) {
                        @Override
                        Object run() throws Exception {
                            return msg.obj;
                        }
                    }).start();
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
        }
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void disconnect(final Callback callback) {
        (new ReactTask(callback) {
            @Override
            Object run() throws Exception {
                Boolean success = ReactNativeZeroMQ.this._disconnect();
                return this._successResult(success);
            }
        }).start();
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void getDeviceIdentifier(final Callback callback) {
        (new ReactTask(callback) {
            @Override
            Object run() throws Exception {
                return ReactNativeZeroMQ.this._getDeviceIdentifier();
            }
        }).start();
    }
}
