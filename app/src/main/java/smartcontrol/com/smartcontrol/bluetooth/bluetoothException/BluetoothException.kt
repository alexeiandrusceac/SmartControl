package smartcontrol.com.smartcontrol.bluetooth.bluetoothException

open class BluetoothException : Exception {
    constructor(throwable: Throwable):super(throwable){}
    constructor(message: String):super(message){}
    constructor(){}
}