package smartcontrol.com.smartcontrol.Drawing

import android.view.SurfaceHolder

class SurfaceDrawer(imageHandler: ImageHandler) : SurfaceHolder.Callback {
    private lateinit var drawThread: DrawThread
    private lateinit var imageHandler: ImageHandler

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        var retry : Boolean = true
        drawThread.setRunning(retry)
        while(retry)
        {
            try{
                drawThread.join()
                retry = false
            }
            catch ( ex : Exception){}
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    fun SurfaceDrawer(imgHandler: ImageHandler) {
        this.imageHandler = imgHandler
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val drawThread : DrawThread = DrawThread(holder, imageHandler)
        drawThread.setRunning(true)
        drawThread.start()
    }


}