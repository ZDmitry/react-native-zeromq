
export class ZMQSocket {

  _bridge = null;
  _uuid   = "";

  constructor(bridge, uuid) {
    this._bridge = bridge;
    this._uuid   = uuid;
  }

  destroy() {
    return new Promise((resolve, reject) => {
      this._bridge.destroy(this._uuid, answ => {
        if (answ.error) {
          reject(answ.error);
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
        if (answ.error) {
          reject(answ.error);
          return;
        }

        resolve(answ.result);
      });
    });
  }

  connect(addr) {
    return new Promise((resolve, reject) => {
      this._bridge.socketConnect(this._uuid, addr, answ => {
        if (answ.error) {
          reject(answ.error);
          return;
        }

        resolve(answ.result);
      });
    });
  }

  close() {
    return new Promise((resolve, reject) => {
      this._bridge.socketClose(this._uuid, answ => {
        if (answ.error) {
          reject(answ.error);
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
        if (answ.error) {
          reject(answ.error);
          return;
        }

        resolve(answ.result);
      });
    });
  }

  send(body, flags) {
    return new Promise((resolve, reject) => {
      this._bridge.socketSend(this._uuid, body, flags, answ => {
        if (answ.error) {
          reject(answ.error);
          return;
        }

        resolve(answ.result);
      });
    });
  }

  recv(flags) {
    return new Promise((resolve, reject) => {
      this._bridge.socketRecv(this._uuid, flags, answ => {
        if (answ.error) {
          reject(answ.error);
          return;
        }

        resolve(answ.result);
      });
    });
  }

}