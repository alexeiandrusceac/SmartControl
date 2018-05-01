package smartcontrol.com.smartcontrol.bluetooth.bluetoothException

class ConnectionBluetoothException : BluetoothException{
constructor(throwable: Throwable):super(throwable){}
    constructor(message:String):super(message){}
}