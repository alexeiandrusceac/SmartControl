package smartcontrol.com.smartcontrol.DataRemote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import smartcontrol.com.smartcontrol.R

class DataRemote : AppCompatActivity() {
    private lateinit var switchOffTheLight : Button
    private lateinit var stopExecution : Button
    private lateinit var clearExecution : Button
    private var off : Boolean = false
    private var stopExecBol : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_remote)

        switchOffTheLight.setOnClickListener { off = true }
        stopExecution.setOnClickListener { stopExecBol = true }
        clearExecution.setOnClickListener { }
    }
}