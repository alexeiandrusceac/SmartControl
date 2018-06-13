package smartcontrol.com.smartcontrol.CameraRemote


import android.os.AsyncTask
import smartcontrol.com.smartcontrol.Drawing.ImageHandler
import smartcontrol.com.smartcontrol.bluetooth.BluetoothConn
import smartcontrol.com.smartcontrol.bluetooth.BluetoothException
import java.io.IOException

class CameraImageGetTask(imageHandler: ImageHandler, connectBT: String) : AsyncTask<Void, Void, String>() {
    private val COMMAND = charArrayOf('*', 'R', 'D', 'Y', '*')
    private val WIDTH = 320
    private val HEIGHT = 240

    private val stringBuffer: StringBuffer = StringBuffer()
    private var imageHandler: ImageHandler? = null
    private var bluetoothConn: BluetoothConn? = null


    fun CameraImageGetTask(imgHandler: ImageHandler, bth: BluetoothConn) {
        this.imageHandler = imgHandler
        this.bluetoothConn = bth
    }

    override fun doInBackground(vararg params: Void?): String {
        stringBuffer.setLength(0)
        val rgb = Array(HEIGHT) { IntArray(WIDTH) }
        val rgb2 = Array(WIDTH) { IntArray(HEIGHT) }
        // stringBuffer.append("Primirea imaginii\n")
        try {
            readData(rgb, true)
            readData(rgb, false)
            for (y in 0 until HEIGHT) {
                for (x in 0 until WIDTH) {
                    rgb2[x][y] = rgb[y][x]
                }

            }
            imageHandler!!.setCurImage(rgb2)
            bluetoothConn!!.disconnect()

        } catch (exception: Exception) {
            stringBuffer.append("Error" + exception.message)
        }
        return stringBuffer.toString()
    }

    @Throws(IOException::class, BluetoothException::class)
    private fun readData(rgb: Array<IntArray>, firstSecond: Boolean) {
        while (!isImageStart(0)) {
        }

        stringBuffer.append("Found image part " + (if (firstSecond) "First" else "Second") + "\n")

        var flag = true
        for (y in 0 until HEIGHT) {
            for (x in 0 until WIDTH) {
                flag = !flag
                if (flag == firstSecond) {
                    val temp = bluetoothConn!!.read()
                    rgb[y][x] = temp and 0xFF//((temp&0xFF) << 16) | ((temp&0xFF) << 8) | (temp&0xFF);
                }
            }
        }
    }

    @Throws(BluetoothException::class, IOException::class)
    private fun isImageStart(index: Int): Boolean {
        var index = index
        return if (index < COMMAND.size) {
            if (Integer.parseInt(COMMAND[index].toString()) == bluetoothConn!!.read()) {
                isImageStart(++index)
            } else {
                false
            }
        } else true
    }

}