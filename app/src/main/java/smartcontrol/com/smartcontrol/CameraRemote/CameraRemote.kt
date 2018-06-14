package smartcontrol.com.smartcontrol.CameraRemote

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import smartcontrol.com.smartcontrol.Drawing.ImageHandler
import smartcontrol.com.smartcontrol.Drawing.SurfaceDrawer
import smartcontrol.com.smartcontrol.R

class CameraRemote : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView

    private lateinit var  surfaceHolder: SurfaceHolder
    private var  imageHandler : ImageHandler = ImageHandler()
    private  var  surfaceDrawer : SurfaceDrawer = SurfaceDrawer(imageHandler)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.camera_remote)

        val connectBT = intent.getStringExtra("conB")

        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(surfaceDrawer)

        val imageGetTask : CameraImageGetTask = CameraImageGetTask(imageHandler,connectBT)
        imageGetTask.execute()
    }

}