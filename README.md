# react-native-zeromq
ZeroMQ client for React Native

## Installation

### Automatic (for Android with RN 0.28 and before)

If you have rnpm installed, all you need to do is

```
npm install zdmitry/react-native-zeromq --save
rnpm link react-native-zeromq
```


### Manual

#### Android (with RN 0.29 and above)
in `settings.gradle`

```
include ':react-native-zeromq'
project(':react-native-zeromq').projectDir = file('../node_modules/react-native-zeromq/android')
```

in `android/app/build.gradle`

```
dependencies {
    compile project(':react-native-zeromq')
```

in `MainApplication.java`
add package to getPacakges()

```java
import org.zeromq.rnzeromq.ReactNativeRedisPackage;

// ...

@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        // ... ,
        new ReactNativeZeroMQPackage()
   );
}
```

Additionally multi dex support could be required.


##API

`start(Object config)` (Promise) - start ZeroMQ client

`destroy()` (Promise) - destroy redis client instance (optional)


##Usage

Methods should be called from React Native as any other promise.
Prevent methods from being called multiple times (on Android).

###Example

```javascript
import { ZeroMQ } from 'react-native-zeromq';

// ...

ZeroMQ.connect({
  // ...
}).then(() => {
  // ...
});

```
