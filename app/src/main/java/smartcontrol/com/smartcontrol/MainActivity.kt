package smartcontrol.com.smartcontrol

import android.content.Intent
import android.graphics.Camera

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout


import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast


import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote

import smartcontrol.com.smartcontrol.bluetooth.BluetoothConn
import kotlinx.android.synthetic.main.activity_main.*
import smartcontrol.com.smartcontrol.R.id.*

import smartcontrol.com.smartcontrol.bluetooth.BluetoothException
import java.util.*


class MainActivity : AppCompatActivity() {


    private val MY_NAME: String = "MEIZU EP51"
    private val bluetoothConn: BluetoothConn = BluetoothConn()
    private var pairedDevices: Map<String, String> = HashMap<String, String>()
    private var handler: Handler = Handler()
    private lateinit var mRunnable: Runnable
    /////https://github.com/Kanaris/OV7670/blob/master/android/Merl1nVision/app/src/main/java/info/privateblog/merl1n/merl1nvision/drawing/DrawThread.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout.setOnRefreshListener {

            mRunnable = Runnable {
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
                text_view.text = "Refresh and get random number "

                // Change the text view text color with a random color


                // Hide swipe to refresh icon animation
                swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(mRunnable, (randomInRange(1, 5) * 1000).toLong())

        }

        /*      bluetoothConn.BluetoothConn()
              val temp = HashMap<String, String>()

      pairedDevices = bluetoothConn.getPairedDevices()
      if (!pairedDevices.isEmpty()) {
          val iterator: Iterator<String> = pairedDevices.keys.iterator()
          while (iterator.hasNext()) {
              Toast.makeText(applicationContext, "dispozitivul imperecheat este" + iterator.next(), Toast.LENGTH_LONG).show()
          }
          temp.putAll(pairedDevices)
          val address = temp.get(MY_NAME)
          bluetoothConn.connectToDevice(address!!)
          swipeRefreshLayout.isRefreshing = false
          text_view.visibility = View.INVISIBLE
      }
      ( Handler()).postDelayed( Runnable() {
          @Override
          fun run() {

              Log.d("Swipe", "Refreshing Number");

          }
      }, 0))*/
        /*dataRemote.setOnClickListener {
            val dataRemoteActivity: Intent = Intent(DataRemote::class.java.name)
            startActivity(dataRemoteActivity)
        }*/
        cameraRemote.setOnClickListener {
            val cameraRemoteActivity = Intent(applicationContext, CameraRemote::class.java)
            cameraRemoteActivity.putExtra("conB", bluetoothConn.toString())
            startActivity(cameraRemoteActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothConn.disconnect()
        } catch (exception: BluetoothException) {

        }
    }

    /*fun getArduinoData()
    {

    }*/

    /*override fun onRefresh() {
        bluetoothConn.BluetoothConn()
        val temp = HashMap<String, String>()

        pairedDevices = bluetoothConn.getPairedDevices()
        if (!pairedDevices.isEmpty()) {
            val iterator: Iterator<String> = pairedDevices.keys.iterator()
            while (iterator.hasNext()) {
                Toast.makeText(applicationContext, "dispozitivul imperecheat este" + iterator.next(), Toast.LENGTH_LONG).show()
            }
            temp.putAll(pairedDevices)
            val address = temp.get(MY_NAME)
            bluetoothConn.connectToDevice(address!!)
            swipeRefreshLayout.isRefreshing = false
            text_view.visibility = View.INVISIBLE
        }


    }*/
    private fun randomInRange(min: Int, max: Int): Int {
        // Define a new Random class
        val r = Random()

        // Get the next random number within range
        // Including both minimum and maximum number
        return r.nextInt((max - min) + 1) + min
    }
}



