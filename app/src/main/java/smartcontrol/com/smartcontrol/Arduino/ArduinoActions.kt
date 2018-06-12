package smartcontrol.com.smartcontrol.Arduino

import android.content.Context

class  ArduinoActions{
    private lateinit var arduinoActions: ArduinoActions
    private lateinit var context:Context

    fun ArduinoActions(context:Context)
    {
        this.context = context

    }
    fun getInstance(context: Context) : ArduinoActions
    {
        if (arduinoActions == null) {
            arduinoActions = ArduinoActions()
        }
        return synchronized(this){arduinoActions}

    }

    fun getData()
    {

    }
    fun setTime(hours: Int, minutes: Int) {
    }
    fun getCamera()
    {

    }
}