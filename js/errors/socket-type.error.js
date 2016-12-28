import { BaseClass } from "../base-class";

export class ZMQSocketTypeError extends BaseClass(Error) {
  constructor() {
    super('ZeroMQ error: invalid socket type');
    this.name = this.constructor.name;
  }
}
