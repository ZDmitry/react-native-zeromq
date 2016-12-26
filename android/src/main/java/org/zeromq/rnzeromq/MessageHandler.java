package org.zeromq.rnzeromq;

import android.os.Handler;
import android.os.Message;

class MessageHandler extends Handler {

    private final EventEmitter emitter = EventEmitter.getInstance();

    @Override
    public void handleMessage(Message msg) {
        emitter.emit("zeromq.recvd", (String)msg.obj);
    }
}
