import { BaseClass } from "../base-class";

export class ZMQError extends BaseClass(Error) {
  constructor(details, object) {
    super('ZeroMQ internal error');

    this.name    = "ZMQError";
    this.details = details;
    this.object  = object;

    if (details.code === "ERNINT") {
      this.details = details.message;
      this.object  = details.details;
    }
  }
}
