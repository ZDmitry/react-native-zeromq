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
import org.zeromq.rnzeromq.ReactNativeZeroMQPackage;

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

`ZeroMQ.socket(ZeroMQ.SOCKET_TYPE socketType)` (Promise) - creates new ZeroMQ socket of correspond type

`ZeroMQ.getDeviceIdentifier()` (Promise) - returns user-friendly device identifier

`socket.bind(String address)` (Promise) - set socket to listen state (acts as a server)
 
`socket.connect(String address)` (Promise) - connect socket to remote server (acts as a client) 

`socket.setIdentity(String identity)` (Promise) - set client identity for ZeroMQ protocol

`socket.send(String body, int flags)` (Promise) - send message. To send multipart message, use `ZMQ_SNDMORE` flag

`socket.recv(int flags)` (Promise) - read incomming message, if available. Use `ZMQ_DONTWAIT` flag to read immediately 

`socket.close(String address)` (Promise) - close connection (and destroy object). Alias: `.destroy()`


##Usage

Methods should be called from React Native as any other promise.
Prevent methods from being called multiple times (on Android).

###Example

```javascript
import { ZeroMQ } from 'react-native-zeromq';

ZeroMQ.socket(ZeroMQ.SOCKET_TYPE.DEALER).then((socket) => {
  socket.connect("tcp://127.0.0.1:5566").then(() => {
    socket.send("Hi there!").then(() => {
      socket.recv().then((msg) => {
        console.log(msg);
      });
    });
  });
});

```
