package smartcontrol.com.smartcontrol.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.io.DataInputStream
import java.io.OutputStream

class BluetoothConnection {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var  bluetoothSocket: BluetoothSocket
    private lateinit var outputStream: OutputStream
    private lateinit var datainputStream: DataInputStream

    fun BluetoothConnection()
    {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }
    @Throws BluetoothException
   fun connectToDevice(address:String){}

}