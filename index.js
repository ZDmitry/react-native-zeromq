import {
  DeviceEventEmitter,
  NativeAppEventEmitter,
  NativeModules,
  Platform
} from 'react-native';

const ReactNativeZeroMQAndroid = NativeModules.ReactNativeZeroMQAndroid;

let bridge = null;
let notification_listeners = [];

let call_notification_listeners = function (notification) {
  var i = notification_listeners.length - 1;

  for (i; i >= 0; i--) {
    notification_listeners[i](notification);
  }
};

switch (Platform.OS) {
  case 'android':
    bridge = ReactNativeZeroMQAndroid;
    DeviceEventEmitter.addListener('redis.event', (notification) => {
      notification = (notification && notification.result);
      call_notification_listeners(notification);
    });
    break;
}


export class ZeroMQ {

  static start(config) {
    return new Promise((resolve, reject) => {
      bridge.start(JSON.stringify(config || '{}'), answ => {
        if (answ.error) {
          reject(answ.error);
          return;
        }
        resolve(answ.result);
      });
    });
  }

  static destroy() {
    return new Promise((resolve, reject) => {
      bridge.destroy(answ => {
        if (answ.error) {
          reject(answ.error);
          return;
        }
        resolve(answ.result);
      });
    });
  }

  static onNotification(callback) {
    notification_listeners.push(callback);
  }
}
