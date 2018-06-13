package smartcontrol.com.smartcontrol

import android.content.Intent
import android.graphics.Camera

import android.os.Bundle
import android.os.PersistableBundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast


import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote
import smartcontrol.com.smartcontrol.DataRemote.DataRemote
import smartcontrol.com.smartcontrol.bluetooth.BluetoothConn
import kotlinx.android.synthetic.main.activity_main.*
import smartcontrol.com.smartcontrol.R.id.swipeRefreshLayout
import smartcontrol.com.smartcontrol.bluetooth.BluetoothException



class MainActivity : AppCompatActivity() {
    private val MY_NAME : String = "MEIZU EP51"
    private val bluetoothConn: BluetoothConn = BluetoothConn()
    private var pairedDevices: Map<String, String> = HashMap<String, String>()
    /////https://github.com/Kanaris/OV7670/blob/master/android/Merl1nVision/app/src/main/java/info/privateblog/merl1n/merl1nvision/drawing/DrawThread.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


Toast.makeText(applicationContext,"sas",Toast.LENGTH_LONG).show()

        swipeRefreshLayout.setOnRefreshListener {
            bluetoothConn.BluetoothConn()
            val temp = HashMap<String, String>()
            try {
                pairedDevices = bluetoothConn.getPairedDevices()
                if (!pairedDevices.isEmpty())
                {
                    val iterator: Iterator<String> = pairedDevices.keys.iterator()
                    while (iterator.hasNext())
                    {
                        Toast.makeText(applicationContext, "dispozitivul imperecheat este" + iterator.next(), Toast.LENGTH_LONG).show()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext, "nu este niciun dispozitiv imperecheat", Toast.LENGTH_LONG).show()
                }
            } catch (exception: Exception) {
                exception.message
            }
            temp.putAll(pairedDevices)
            val address = temp.get(MY_NAME)
            bluetoothConn.connectToDevice(address!!)
            swipeRefreshLayout.isRefreshing = false
            text_view.visibility = View.INVISIBLE

        }


        /*dataRemote.setOnClickListener {
            val dataRemoteActivity: Intent = Intent(DataRemote::class.java.name)
            startActivity(dataRemoteActivity)
        }*/
         cameraRemote.setOnClickListener {
             val cameraRemoteActivity = Intent(applicationContext, CameraRemote::class.java)
             cameraRemoteActivity.putExtra("conB",bluetoothConn.toString())
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


}

