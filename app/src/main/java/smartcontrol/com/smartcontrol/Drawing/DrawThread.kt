package smartcontrol.com.smartcontrol.Drawing

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder

class DrawThread : Thread() {
    private var runFlag: Boolean = false
    private lateinit var imageHandler: ImageHandler
    private lateinit var surfaceHolder: SurfaceHolder

    fun DrawThread(imgHandler: ImageHandler, surfHolder: SurfaceHolder) {
        this.imageHandler = imgHandler
        this.surfaceHolder = surfHolder
    }

    fun setRunning(run: Boolean) {
        runFlag = run
    }

    override fun run() {
        val canvas:Canvas? = null
         while(runFlag)
         {
             try {
                 val currImage: Array<IntArray>? = imageHandler.getCurImage()
                 if (currImage != null)
                 {
                     val paint : Paint
                     val config : Bitmap.Config = Bitmap.Config.ARGB_8888
                     val bitmap : Bitmap  = Bitmap.createBitmap(currImage.size,currImage[0].size,config)
                     val tempCanvas : Canvas = Canvas(bitmap)

                 }

             }
             catch (ex: Exception)
             {

             }
         }
    }
}