package smartcontrol.com.smartcontrol

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote
import smartcontrol.com.smartcontrol.bluetooth.BluetoothConn
import kotlinx.android.synthetic.main.activity_main.*
import smartcontrol.com.smartcontrol.DataRemote.DataRemote
import smartcontrol.com.smartcontrol.R.id.*

import java.util.*


class MainActivity : AppCompatActivity() {

    private val MY_NAME: String = "MEIZU EP51"
    private val bluetoothConn: BluetoothConn = BluetoothConn()
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var pairedDevices: Map<String, String> = HashMap<String, String>()
    private var handler: Handler = Handler()
    private lateinit var mRunnable: Runnable

    private val bluetoothReceiver =  object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if (bluetoothAdapter.isEnabled)
            {
                connect()
            }
            else
            {
                val turnOnBlth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(turnOnBlth, 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent  = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothReceiver,intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                connect()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                bluetoothConn.disconnect()
                text_view.text = "Va rugam porniti Bluetooth-ul manual"
            }

        }
    }

    fun connect() {
        swipeRefreshLayout.setOnRefreshListener {

            bluetoothConn.BluetoothConn()
            val temp = HashMap<String, String>()
            try {
                pairedDevices = bluetoothConn.getPairedDevices()
                if (!pairedDevices.isEmpty()) {
                    temp.putAll(pairedDevices)
                    val address = temp.get(MY_NAME)
                    bluetoothConn.connectToDevice(address!!)
                    swipeRefreshLayout.isRefreshing = false
                    text_view.visibility = View.INVISIBLE
                }
            } catch (ex: Exception) {
                ex.message
            }
           // swipeRefreshLayout.isRefreshing = false
            handler.postDelayed(mRunnable, (randomInRange(1, 5) * 1000).toLong())
        }
    }


   /* fun checkBluetoothAvailable() {
        if (!bluetoothAdapter.isEnabled) {
            val turnOnBlth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnOnBlth, 1)
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
        //bluetoothConn.disconnect()

    }


    private fun randomInRange(min: Int, max: Int): Int {
        // Define a new Random class
        val r = Random()

        // Get the next random number within range
        // Including both minimum and maximum number
        return r.nextInt((max - min) + 1) + min
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }
}



