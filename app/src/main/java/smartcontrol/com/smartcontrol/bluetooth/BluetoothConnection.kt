package smartcontrol.com.smartcontrol.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothDevice
import smartcontrol.com.smartcontrol.bluetooth.bluetoothException.BluetoothException
import smartcontrol.com.smartcontrol.bluetooth.bluetoothException.ConnectionBluetoothException
import smartcontrol.com.smartcontrol.bluetooth.bluetoothException.NotSupportBlToothException
import java.io.DataInputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset.isSupported

class BluetoothConnection {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var  bluetoothSocket: BluetoothSocket
    private lateinit var outputStream: OutputStream
    private lateinit var datainputStream: DataInputStream

    fun BluetoothConnection()
    {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    @Throws(BluetoothException::class)
    fun connectToDevice(addressDevice: String)
    {
        if(!isSupported())
            throw NotSupportBlToothException()
        if(isConnected())
        {
            return
        }
    }
    fun isConnected() : Boolean{
        return bluetoothSocket !=null
    }
    fun isSupported (): Boolean{
      if(getBluetoothAdapter() ==null)
      {
          return false
      }
        return true
    }
    fun getBluetoothAdapter() :BluetoothAdapter
    {
        if(bluetoothAdapter ==null)
        {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }
        return bluetoothAdapter
    }

    @Throws(BluetoothException::class,IOException::class)

    fun write(command : ByteArray){
        if(outputStream!=null)
        {
            outputStream.write(command)
            outputStream.flush()
        }
        else
        {
            throw ConnectionBluetoothException("Nu este conectat")
        }

    }
@Throws(BluetoothException::class,IOException::class)
    fun read (): Int {
    val result: Int
    if (datainputStream != null) {
         result = datainputStream.read()
    } else {
        throw ConnectionBluetoothException("Nu este conectat")
    }
    return result
}
    @Throws(BluetoothException::class)
        fun disconnectFromDevice(){
        cancelDiscovery()
        if(outputStream !=null)
        {
            try{
                outputStream.close()

            }
            catch (exception: IOException){}
        }
        if(datainputStream != null)
        {
            try{
                datainputStream.close()
            }catch (exception: IOException){}
        }
        if(bluetoothSocket !=null)
        {
            try
            {
                bluetoothSocket.close()

            }catch (exception: IOException){}
        }
        bluetoothSocket !=null

    }
    @Throws(BluetoothException::class)
    fun getBondedDevices(): Map<String,String>
    {
        if(!isSupported())
        {
            throw  NotSupportBlToothException()
        }
        val pairedDevicesAdapter = HashMap<String,String>()
        val pairedDevices : (Set<BluetoothDevice>)= bluetoothAdapter.bondedDevices
        if(pairedDevices.size >0)
        {
            for(device : BluetoothDevice in pairedDevices)
            {
                pairedDevicesAdapter.put(device.name,device.address)
            }
        }
        return pairedDevicesAdapter
    }
    @Throws(BluetoothException::class)
fun cancelDiscovery(){
        if(!isSupported())
        {
            throw NotSupportBlToothException()
        }
        if(getBluetoothAdapter().isDiscovering()){
            getBluetoothAdapter().cancelDiscovery()
        }
    }
    @Throws(BluetoothException::class)
   fun startDiscovery(): Boolean
   {
        if(!isSupported())
        {
            throw NotSupportBlToothException()
        }
       if(getBluetoothAdapter().isDiscovering())
       {getBluetoothAdapter().cancelDiscovery()}
       return getBluetoothAdapter().startDiscovery()
   }
    @Throws(BluetoothException::class)
    fun getAdapterName () :String{
        if(!isSupported()) {
            throw NotSupportBlToothException()
        }
        return getBluetoothAdapter().name
    }
    @Throws(BluetoothException::class)
    fun isEnabled():Boolean{
        if(!isSupported())
        {
            throw NotSupportBlToothException()
        }
        return getBluetoothAdapter().isEnabled
    }


}