package com.zeromq.rnzeromq;

import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;


public class EventEmitter {

    private static final String TAG = "RNZeroMQEventEmitter";

    private static EventEmitter INSTANCE = null;

    private ReactContext context;

    private EventEmitter(ReactContext reactContext) {
        this.context = reactContext;
        this.context.addLifecycleEventListener(ReceiverHelper.getInstance(context));
    }

    public void emit(String eventName, WritableMap message) {
        if (context.hasActiveCatalystInstance() && this.context.hasCurrentActivity()) {
            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, message);
        }
    }

    public static void setup(ReactContext reactContext) {
        if (EventEmitter.INSTANCE == null) {
            EventEmitter.INSTANCE = new EventEmitter(reactContext);
        } else {
            Log.w(TAG, "Event Emitter initialized more than once");
            if (EventEmitter.INSTANCE.context.getCatalystInstance().isDestroyed()) {
                EventEmitter.INSTANCE = new EventEmitter(reactContext);
            }
        }
    }

    public static EventEmitter getInstance() {
        return EventEmitter.INSTANCE;
    }
}
