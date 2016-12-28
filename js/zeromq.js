import Core from './core';
import { ZMQSocket } from './socket'


export class ZeroMQ {

  static SOCKET_TYPE = {
    REP:    Core.bridge.ZMQ_REP,
    REQ:    Core.bridge.ZMQ_REQ,

    XREP:   Core.bridge.ZMQ_XREP,
    XREQ:   Core.bridge.ZMQ_XREQ,

    PUB:    Core.bridge.ZMQ_PUB,
    SUB:    Core.bridge.ZMQ_SUB,

    XPUB:   Core.bridge.ZMQ_XPUB,
    XSUB:   Core.bridge.ZMQ_XSUB,

    DEALER: Core.bridge.ZMQ_DEALER,
    ROUTER: Core.bridge.ZMQ_ROUTER
  };

  static ZMQ_CHARSET  = Core.bridge.ZMQ_CHARSET;
  static ZMQ_DONTWAIT = Core.bridge.ZMQ_DONTWAIT;
  static ZMQ_SNDMORE  = Core.bridge.ZMQ_SNDMORE;

  // @TODO: add more ...

  static socket(socType) {
    return new Promise((resolve, reject) => {
      let _validSocTypes = Object.values(ZeroMQ.SOCKET_TYPE);
      if (!~_validSocTypes.indexOf(socType)) {
        resolve(null);
        return;
      }

      Core.bridge.socketCreate(socType, answ => {
        answ = answ || {error: new Error("ENOANSW")};

        if (answ.error) {
          reject(answ.error);
          return;
        }

        if (!answ.result) {
          resolve(null);
          return;
        }

        resolve(new ZMQSocket(Core.bridge, answ.result));
      });
    });
  }

  static getDeviceIdentifier() {
    return new Promise((resolve, reject) => {
      Core.bridge.getDeviceIdentifier(answ => {
        answ = answ || {error: new Error("ENOENT")};

        if (answ.error) {
          reject(answ.error);
          return;
        }
        resolve(answ.result);
      });
    });
  }

  static onNotification(callback) {
    Core.notificationListeners.push(callback);
  }
}
