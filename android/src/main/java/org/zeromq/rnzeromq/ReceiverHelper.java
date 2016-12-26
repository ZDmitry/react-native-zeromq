package org.zeromq.rnzeromq;

import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.LifecycleEventListener;


class ReceiverHelper implements LifecycleEventListener {

    private static ReceiverHelper instance = null;
    private Context context;
    private Intent  intent;

    private ReceiverHelper(Context context) {
        this.context = context;
    }

    static synchronized ReceiverHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ReceiverHelper(context);
        }
        return instance;
    }

    @Override
    public void onHostResume() {
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
    }
}
