//http://stackoverflow.com/questions/33870684/why-doesnt-instanceof-work-on-instances-of-error-subclasses-under-babel-node
export function BaseClass(cls) {
  let stack = '';

  function BaseClass() {
    let _proto = cls.apply(this, arguments);

    if (this instanceof Error) {
      this.message = _proto.message;
      stack = new Error().stack.split('\n');
      stack = '\n' + stack.slice(3, 6).join('\n');
    }
  }

  BaseClass.prototype = Object.create(cls.prototype);
  BaseClass.__proto__ = cls;
  // Object.setPrototypeOf(BaseClass, cls);

  BaseClass.prototype.toString = function () {
    var msg = this.message || '';
    return ('[' + this.name + '] ' + msg.toString() + stack);
  };

  return BaseClass;
}
