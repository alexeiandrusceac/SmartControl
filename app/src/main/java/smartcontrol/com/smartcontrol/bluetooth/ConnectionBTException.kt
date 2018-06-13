package smartcontrol.com.smartcontrol.bluetooth

class ConnectionBTException : BluetoothException{
    constructor(throwable: Throwable):super(throwable){}
    constructor(message:String):super(message){}
}