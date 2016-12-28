package org.zeromq.rnzeromq;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;


class ReactException extends Exception {

    private WritableMap  _details;

    ReactException(final String code, final String message) {
        super(message);

        _details = Arguments.createMap();
        _details.putString("code", code);
    }

    ReactException(final String code, final String message, WritableMap details) {
        super(message);
        _details = details;

        if (!_details.hasKey("code")) {
            _details.putString("code", code);
        }
    }

    String getCode() {
        return _details.getString("code");
    }

    WritableMap getDetails() {
        return _details;
    }
}
