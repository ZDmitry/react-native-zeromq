import { BaseClass } from "../base-class";

export class ZMQError extends BaseClass(Error) {
  constructor(details, object) {
    super('ZeroMQ internal error');

    this.name    = this.constructor.name;
    this.details = details;
    this.object  = object;
  }
}
