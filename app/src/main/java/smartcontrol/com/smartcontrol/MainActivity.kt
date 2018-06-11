package smartcontrol.com.smartcontrol

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote
import smartcontrol.com.smartcontrol.DataRemote.DataRemote
import smartcontrol.com.smartcontrol.bluetooth.BluetoothConnection
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity()/*AsyncTask<SwipeRefreshLayout,String,Unit>() */{
    /*override fun doInBackground(vararg layouts: SwipeRefreshLayout) {
        swipeRefreshLayout = layouts[0]
        sleep(3000)}
*/
    private lateinit var  swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var  bluetoothConnection: BluetoothConnection
    private lateinit var  cameraRemote : Button
    private lateinit var dataRemote : Button
    private lateinit var arduinoActions: ArduinoActions
    private lateinit var context: Context
    private var BLUETOOTH_ADDRESS_DEVICE : String =  "fjsbfd"
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  cameraRemote : (ImageButton) = findViewById(R.id.cameraRemote)
        val dataRemote : (ImageButton) = findViewById(R.id.dataRemote)
        val swipeRefreshLayout : (SwipeRefreshLayout) = findViewById(R.id.swipeRefreshLayout)

        arduinoActions  = ArduinoActions().getInstance(this)

        cameraRemote.setOnClickListener {
            val intent = Intent(context,  CameraRemote::class.java)
            startActivity(intent)
        }

        dataRemote.setOnClickListener {
            val intentData = Intent(context, DataRemote::class.java)
            startActivity(intentData)
        }
        swipeRefreshLayout.setOnRefreshListener {
            bluetoothConnection = BluetoothConnection().connectToDevice(BLUETOOTH_ADDRESS_DEVICE)
        }
    }
    }

