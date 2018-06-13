package smartcontrol.com.smartcontrol.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket

import android.bluetooth.BluetoothDevice
import java.lang.reflect.Method

import java.io.*


class BluetoothConn {
    private  lateinit var bluetoothAdapter: BluetoothAdapter
    private  lateinit var bluetoothSocket: BluetoothSocket
    private  var  bluetoothDevice: BluetoothDevice? =null
    private  var outStream: OutputStream?=null
    private  var inputStream: InputStream?=null

    fun BluetoothConn() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    @Synchronized
    @Throws(BluetoothException::class)
    fun connectToDevice(address: String) {
        if (!isDeviceBTSuported())
            throw NotSupportBlToothException()
        if (isDeviceConnected())
            return
        val bluetoothDevice : BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)

        try {
            val method: Method = bluetoothDevice.javaClass.getMethod("createRfcommSocket", *arrayOf<Class<*>>(Int::class.javaPrimitiveType!!))
            bluetoothSocket = method.invoke(bluetoothDevice, 1) as BluetoothSocket
            bluetoothSocket.connect()
            outStream = DataOutputStream(bluetoothSocket.outputStream)
            inputStream = DataInputStream(bluetoothSocket.inputStream)
        } catch (exception: Exception) {
            bluetoothSocket!!
            throw ConnectionBTException(exception)
        }
    }

    @Throws(BluetoothException::class, IOException::class)
    fun write(commByte: ByteArray) {
        if (outStream != null) {
            outStream!!.write(commByte)
            outStream!!.flush()
        } else {
            throw ConnectionBTException("Nu sa connectat ")
        }
    }

    @Throws(BluetoothException::class, IOException::class)
    fun read(): Int {
        val readResult: Int
        if (inputStream != null) {
            readResult = inputStream!!.read()
        } else {
            throw ConnectionBTException("Nu sa connectat")
        }
        return readResult
    }

    private fun isDeviceBTSuported(): Boolean {
        if (getBTAdapter() == null)
            return false
        return true

    }

    fun getBTAdapter(): BluetoothAdapter {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }
        return bluetoothAdapter!!
    }

    fun isDeviceConnected(): Boolean {
        return bluetoothSocket != null
    }

    @Throws(BluetoothException::class)
    fun getPairedDevices(): Map<String, String> {
        if(!isDeviceBTSuported())
        {
            throw NotSupportBlToothException()
        }
        val pairedDevicesAdapter  = HashMap<String,String>()
        val pairedDevice = bluetoothAdapter!!.bondedDevices
        if(!pairedDevice.isEmpty())
        {
            for(bluetoothDevice : BluetoothDevice in pairedDevice)
            {
                pairedDevicesAdapter.put(bluetoothDevice.name,bluetoothDevice.address)

            }
        }
        return pairedDevicesAdapter
    }

    @Throws(BluetoothException::class)
    fun cancelDiscovery() {
        if (!isDeviceBTSuported())
            throw NotSupportBlToothException()

        if (getBTAdapter().isDiscovering()) {
            getBTAdapter().cancelDiscovery()
        }
    }

    /**
     * Start discovery
     * @return value
     * @throws BluetoothException
     */
    @Throws(BluetoothException::class)
    fun startDiscovery(): Boolean {
        if (!isDeviceBTSuported())
            throw NotSupportBlToothException()

        if (getBTAdapter().isDiscovering()) {
            getBTAdapter().cancelDiscovery()
        }
        return getBTAdapter().startDiscovery()
    }

    /**
     * Get Adapter name
     * @return name
     * @throws BluetoothException
     */
    @Throws(BluetoothException::class)
    fun getAdapterName(): String {
        if (!isDeviceBTSuported())
            throw NotSupportBlToothException()
        return getBTAdapter().name
    }

    @Throws(BluetoothException::class, IOException::class)
    fun read(response: ByteArray): Int {
        val result: Int
        if (inputStream != null) {
            result = inputStream!!.read(response)
        } else {
            throw ConnectionBTException("Nu este conectat")
        }
        return result
    }

    @Synchronized
    @Throws(BluetoothException::class)
    fun disconnect() {
        cancelDiscovery()

        if (outStream != null) {
            try {
                outStream!!.close()
            } catch (e: IOException) {
            }

        }
        if (inputStream != null) {
            try {
                inputStream!!.close()
            } catch (e: IOException) {
            }

        }
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.close()
            } catch (e: IOException) {
            }

        }
        bluetoothSocket!!
    }

}