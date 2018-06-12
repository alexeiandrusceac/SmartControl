package smartcontrol.com.smartcontrol.Drawing

abstract class ImageHandler {
    private var WIDTH: Int = 240
    private var HEIGHT: Int = 320

    private var currentImage: Array<IntArray>? = null
    fun ImageHandler() {}

    fun getCurImage(): Array<IntArray>? {
        return currentImage
    }
    fun setCurImage(curImage : Array<IntArray>?){
        this.currentImage = curImage
    }

}