package org.zeromq.rnzeromq;

import android.os.AsyncTask;
import android.os.Handler;

import org.zeromq.ZMQ;


class ZeroMQTask extends AsyncTask<String, Void, String> {

    private final String  _serverAddress;
    private       Handler _threadHandler;

    ZeroMQTask(final String serverAddress, final Handler handler) {
        _serverAddress = serverAddress;
        _threadHandler = handler;

        if (_threadHandler == null) {
            _threadHandler = new MessageHandler();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        socket.connect(_serverAddress);

        socket.send(params[0].getBytes(), 0);
        String result = new String(socket.recv(0));

        socket.close();
        context.term();

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        _threadHandler.sendMessage(_threadHandler.obtainMessage(0, result));
    }
}
