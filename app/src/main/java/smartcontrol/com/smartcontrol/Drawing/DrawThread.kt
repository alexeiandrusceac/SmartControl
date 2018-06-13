package smartcontrol.com.smartcontrol.Drawing

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder

class DrawThread(holder: SurfaceHolder?, imageHandler: ImageHandler) : Thread() {
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
        var canvas : Canvas? = null
         while(runFlag)
         {
             try {
                 val currImage: Array<IntArray>? = imageHandler.getCurImage()
                 if (currImage != null)
                 {
                     val paint : Paint = Paint()
                     val config : Bitmap.Config = Bitmap.Config.ARGB_8888
                     val bitmap : Bitmap  = Bitmap.createBitmap(currImage.size,currImage[0].size,config)
                     val tempCanvas : Canvas = Canvas(bitmap)
                        for(j in 0 until currImage.size)
                        {
                            for(i in 0 until currImage[0].size)
                            {
                                var color : Int  = currImage[j][i]
                                paint.color = Color.rgb(color,color,color)
                                tempCanvas.drawPoint(i.toFloat(), (currImage.size-j-1).toFloat(),paint)

                            }
                        }
                    canvas = surfaceHolder.lockCanvas()
                     if(canvas !=null)
                     {
                         val resized : Bitmap = Bitmap.createScaledBitmap(bitmap,canvas.width,canvas.height,true)
                          canvas.drawBitmap(resized,(0).toFloat(),(0).toFloat(),Paint(Paint.ANTI_ALIAS_FLAG))
                     }

                 }

             }
             finally {
               if(canvas != null)
               {
                   surfaceHolder.unlockCanvasAndPost(canvas)
               }
             }
         }
    }
}


