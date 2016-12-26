package org.zeromq.rnzeromq;

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

import zmq.ZMQ;
import zmq.Ctx;
import zmq.SocketBase;

class ReactNativeZeroMQ extends ReactContextBaseJavaModule {

    private Ctx        _zmqCtx = null;
    private SocketBase _dealer = null;

    ReactNativeZeroMQ(final ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private Boolean _init(final ReadableMap config) throws Exception {
        Boolean success;

        if (_zmqCtx == null) {
            _zmqCtx = ZMQ.init(1);
            SocketBase dealer = ZMQ.socket(_zmqCtx, ZMQ.ZMQ_DEALER);
            if (dealer != null) {
                String devIdentifier = "android.os.Build." + ReactNativeUtils.getDeviceName() + " " + ReactNativeUtils.getIPAddress(true);
                ZMQ.setSocketOption(dealer, ZMQ.ZMQ_IDENTITY, devIdentifier);
                success = ZMQ.connect(dealer, config.getString("server"));
                if (success) {
                    _dealer = dealer;
                    return true;
                }
            }
        }

        return false;
    }

    private Boolean _destroy() throws Exception {
        if (_dealer != null) {
            ZMQ.close(_dealer);
            _dealer = null;
        }

        if (_zmqCtx != null) {
            ZMQ.term(_zmqCtx);
            _zmqCtx = null;
        }

        return true;
    }

    @Override
    public String getName() {
        return "ReactNativeZeroMQAndroid";
    }

    @ReactMethod
    @SuppressWarnings("unused")
    public void start(final ReadableMap config, Callback callback) {
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
