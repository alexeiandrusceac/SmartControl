package smartcontrol.com.smartcontrol.CameraRemote

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import smartcontrol.com.smartcontrol.Arduino.ArduinoActions

import smartcontrol.com.smartcontrol.R

class CameraRemote : AppCompatActivity() {
    private lateinit var cameraLive: CameraRemote
    //private lateinit var
    private lateinit var arduinoActions: ArduinoActions

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.camera_remote)

        arduinoActions = ArduinoActions().getInstance(this)

        arduinoActions.getCamera()
    }

}