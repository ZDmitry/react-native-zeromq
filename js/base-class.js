//http://stackoverflow.com/questions/33870684/why-doesnt-instanceof-work-on-instances-of-error-subclasses-under-babel-node
export function BaseClass(cls) {
  let stack = '';

  function BaseClass() {
    cls.apply(this, arguments);

    if (this instanceof Error) {
      stack = new Error().stack.split('\n');
      stack = '\n' + stack.slice(3, 6).join('\n');
    }
  }

  BaseClass.prototype = Object.create(cls.prototype);
  Object.setPrototypeOf(BaseClass, cls);

  BaseClass.prototype.toString = function () {
    var msg = this.message || '';
    return ('[' + this.name + '] ' + msg.toString() + stack);
  };

  return BaseClass;
}