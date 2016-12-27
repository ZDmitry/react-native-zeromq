package org.zeromq.rnzeromq;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
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

    void emit(final String eventName, final String message) {
        if (context.hasActiveCatalystInstance() && this.context.hasCurrentActivity()) {
            WritableMap retVal = Arguments.createMap();
            retVal.putString("result", message);

            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, retVal);
        }
    }

    static void setup(ReactContext reactContext) {
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
