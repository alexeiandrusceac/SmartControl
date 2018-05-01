package smartcontrol.com.smartcontrol

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Button
import android.widget.ListView
import smartcontrol.com.smartcontrol.CameraRemote.CameraRemote
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity()/*AsyncTask<SwipeRefreshLayout,String,Unit>() */{
    /*override fun doInBackground(vararg layouts: SwipeRefreshLayout) {
        swipeRefreshLayout = layouts[0]
        sleep(3000)}
*/
    private lateinit var  swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var  listViewWithButtons : ListView
    private lateinit var  cameraRemote : Button
    private lateinit var dataRemote : Button

    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent: Intent
        val  cameraRemote : (Button) = findViewById(R.id.cameraRemote)
        val dataRemote : (Button) = findViewById(R.id.dataRemote)

        cameraRemote.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View):Unit {
                val intent = Intent(context,  CameraRemote::class.java)
                startActivity(intent)
            }
        })
    }
}
