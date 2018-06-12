package smartcontrol.com.smartcontrol

import android.content.Intent

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity

import android.widget.Button
import android.widget.ImageButton
import smartcontrol.com.smartcontrol.Arduino.ArduinoActions
import smartcontrol.com.smartcontrol.Arduino.ArduinoData
import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote
import smartcontrol.com.smartcontrol.DataRemote.DataRemote
import smartcontrol.com.smartcontrol.bluetooth.BluetoothConn

class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var stopExecution: Button
    private lateinit var clearData: Button
    private lateinit var setTimer: Button
    private lateinit var arduinoActions: ArduinoActions
    private lateinit var dataRemote: ImageButton
    private lateinit var cameraRemote: ImageButton
    private lateinit var bluetoothConn: BluetoothConn
    private lateinit var arduinoData: ArduinoData

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        val dataRemote: (ImageButton) = findViewById(R.id.dataRemote)
        val cameraRemote: (ImageButton) = findViewById(R.id.cameraRemote)

        val bluetoothConn  = BluetoothConn().getInstance()!!
        val arduinoActions = ArduinoActions().getInstance(this)!!
        swipeRefreshLayout.setOnRefreshListener {
            bluetoothConn.connectToDevice()
        }


        dataRemote.setOnClickListener {
            val dataRemoteActivity: Intent = Intent(DataRemote::class.java.name)
            startActivity(dataRemoteActivity)
        }
        cameraRemote.setOnClickListener {
            val cameraRemoteActivity: Intent = Intent(CameraRemote::class.java.name)
            startActivity(cameraRemoteActivity)
        }
    }

    fun getArduinoData()
    {

    }


}