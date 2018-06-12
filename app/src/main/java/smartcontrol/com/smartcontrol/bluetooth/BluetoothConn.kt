package smartcontrol.com.smartcontrol.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket

import smartcontrol.com.smartcontrol.MainActivity
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import java.util.logging.Logger

class BluetoothConn : Thread() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothDevice: BluetoothDevice? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var connected: Boolean = false
    private var founded: Boolean = false
    private var bluetoothConn: BluetoothConn? = null
    private var isBTEnabled: Boolean = true
    private lateinit var robotName: String
    private lateinit var outputStream: OutputStream
    private lateinit var inputStream: InputStream
    private val mMessages = ArrayList<String>()
    private val DELIMITER  = '#'

    fun getInstance(): BluetoothConn? {
        return if (bluetoothConn == null) {
            BluetoothConn()
        } else {
            bluetoothConn
        }
    }

    fun BluetoothConn(name: String) {
        try {
            robotName = name
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                Logger.getLogger(MainActivity::class.java.name).warning("Dispozitivul nu suporta Bluetooth")

            }
            if (!isBTEnabled) {
                Logger.getLogger(MainActivity::class.java.name).warning("Bluetooth nu este activat")
            }
            var setPairedDevice: Set<BluetoothDevice> = bluetoothAdapter!!.bondedDevices
            if (setPairedDevice.size > 0) {
                for (device: BluetoothDevice in setPairedDevice) {
                    if (device.name.equals(robotName)) {
                        bluetoothDevice = device
                        founded = true
                        break
                    }
                }
            }
            if (!founded) {
                Logger.getLogger(MainActivity::class.java.name).warning("Nu este nici un dispozitiv cu Bluetooth imperecheat")
            }
        } catch (e: Exception) {
            Logger.getLogger(MainActivity::class.java.name).warning(e.message)
        }
    }

    fun isBTEnabled(): Boolean {
        return bluetoothAdapter!!.isEnabled
    }

    fun connectToDevice(): Boolean {

        if (!founded) {
            return false
        }
        try {
            Logger.getLogger(MainActivity::class.java.name).warning("Se conecteaza")

            val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            bluetoothSocket = bluetoothDevice!!.createInsecureRfcommSocketToServiceRecord(uuid)
            bluetoothSocket!!.connect()
            outputStream = bluetoothSocket!!.outputStream
            inputStream = bluetoothSocket!!.inputStream
            connected = true
            this.start()
            Logger.getLogger(MainActivity::class.java.name).warning(bluetoothAdapter!!.name.toString())
            Logger.getLogger(MainActivity::class.java.name).warning("OK")
            return true
        } catch (exception: Exception) {
            return false
        }
    }

    override fun run() {

        while (true) {
            if (connected) {
                try {
                    var ch: Byte
                    val buffer = ByteArray(1024)
                    var i = 0

                    val s = ""
                    do {
                        ch = inputStream.read().toByte()
                        if (ch == DELIMITER.toByte()) {
                            buffer[i++] = ch
                        }
                    } while (true)

                    buffer[i] = '\u0000'.toByte()

                    val msg = String(buffer)

                    MessageReceived(msg.trim { it <= ' ' })
                    Logger.getLogger(MainActivity::class.java.name).warning("[Blue]:$msg")

                } catch (e: IOException) {
                    Logger.getLogger(MainActivity::class.java.name).warning("->[#]Failed to receive message: " + e.message)
                }

            }
        }
    }

    private fun MessageReceived(msg: String) {
        try {

            mMessages.add(msg)
            try {
                
            } catch (e: IllegalMonitorStateException) {
                //
            }

        } catch (e: Exception) {
            Logger.getLogger(MainActivity::class.java.name).warning("->[#] Failed to receive message: " + e.message)
        }

    }

    fun hasMensagem(i: Int): Boolean {
        try {
            val s = mMessages.get(i)
            return if (s.length > 0)
                true
            else
                false
        } catch (e: Exception) {
            return false
        }

    }

    fun getMenssage(i: Int): String {
        return mMessages.get(i)
    }

    fun clearMessages() {
        mMessages.clear()
    }

    fun countMessages(): Int {
        return mMessages.size
    }

    fun getLastMessage(): String {
        return if (countMessages() == 0) "" else mMessages.get(countMessages() - 1)
    }

    fun SendMessage(msg: String) {
        try {
            if (connected) {
                outputStream.write(msg.toByteArray())
            }

        } catch (e: IOException) {
            Logger.getLogger(MainActivity::class.java.name).warning("->[#]Error while sending message: " + e.message)
        }

    }


}