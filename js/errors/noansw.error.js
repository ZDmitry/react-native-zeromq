import { BaseClass } from "../base-class";

export class ZMQNoAnswerError extends BaseClass(Error) {
  constructor() {
    super('ZeroMQ error: no answer');
    this.name = this.constructor.name;
  }
}
