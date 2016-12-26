package org.zeromq.rnzeromq;

import android.os.Handler;

import org.zeromq.ZMQ;


class ZeroMQServer implements Runnable {

    private final String  _serverAddress;
    private final Handler _threadHandler = new MessageHandler();

    ZeroMQServer(final String serverAddr) {
        _serverAddress = serverAddr;
    }

    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket   socket = context.socket(ZMQ.REP);

        socket.bind(_serverAddress);
        while(!Thread.currentThread().isInterrupted()) {
            byte[] msg = socket.recv(0);
            if (msg.length > 0) {
                _threadHandler.sendMessage(_threadHandler.obtainMessage(0, new String(msg)));
                socket.send("", 0);
            }
        }

        socket.close();
        context.term();
    }
}
