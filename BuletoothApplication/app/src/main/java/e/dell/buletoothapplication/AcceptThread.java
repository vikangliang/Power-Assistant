package e.dell.buletoothapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import java.io.IOException;
import java.util.UUID;



public class AcceptThread extends Thread
{

    public BluetoothServerSocket bluetoothServerSocket=null;
    public BluetoothAdapter bluetoothAdapter;
    public Context context;
    BluetoothSocket socket;
    ReceiveMessageThread comThread;



    public AcceptThread(BluetoothAdapter bluetoothAdapter, Context context)
    {
        this.bluetoothAdapter=bluetoothAdapter;
        this.context=context;
    }



    public void run()
    {
        try
        {
            //创建服务器
            //接收客户端的连接请求

            bluetoothServerSocket=bluetoothAdapter.listenUsingRfcommWithServiceRecord(bluetoothAdapter.getDefaultAdapter().getName(), UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            //bluetoothServerSocket= (BluetoothServerSocket) bluetoothAdapter.getClass().getMethod("listenUsingRfcommOn",new Class[]{int.class}).invoke(bluetoothAdapter,10);
            socket=bluetoothServerSocket.accept();//接收连接

            if(socket!=null)
            {
                comThread=new ReceiveMessageThread(socket);
                comThread.start();
            }

            bluetoothServerSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }


}































