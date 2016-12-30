import { ZMQError, ZMQNoAnswerError } from './errors'

export class ZMQSocket {

  _bridge = null;
  _uuid   = "";

  constructor(bridge, uuid) {
    this._bridge = bridge;
    this._uuid   = uuid;
  }

  test() {
    return new Promise((resolve, reject) => {
      this._bridge.socketTest(answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  destroy() {
    return new Promise((resolve, reject) => {
      this._bridge.destroy(this._uuid, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        this._uuid = "";
        resolve(answ.result);
      });
    });
  }

  bind(addr) {
    return new Promise((resolve, reject) => {
      this._bridge.socketBind(this._uuid, addr, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  connect(addr) {
    return new Promise((resolve, reject) => {
      this._bridge.socketConnect(this._uuid, addr, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  close() {
    return new Promise((resolve, reject) => {
      this._bridge.socketClose(this._uuid, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        this._uuid = "";
        resolve(answ.result);
      });
    });
  }

  setIdentity(id) {
    return new Promise((resolve, reject) => {
      this._bridge.setSocketIdentity(this._uuid, id, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  send(body, flags) {
    flags = flags || 0;
    return new Promise((resolve, reject) => {
      this._bridge.socketSend(this._uuid, body, flags, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  recv(opts = {}) {
    let flags   = opts.flags || 0;
    let poolInt = opts.poolInterval || (-1);

    return new Promise((resolve, reject) => {
      this._bridge.socketRecv(this._uuid, flags, poolInt, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

  hasMore() {
    return new Promise((resolve, reject) => {
      this._bridge.socketHasMore(this._uuid, answ => {
        if (!answ) {
          reject(new ZMQNoAnswerError());
          return;
        }

        if (answ.error) {
          reject(new ZMQError(answ.error));
          return;
        }

        resolve(answ.result);
      });
    });
  }

}
