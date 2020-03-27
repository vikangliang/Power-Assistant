package e.dell.buletoothapplication;

import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;


public class ReceiveMessageThread extends Thread
{

    private  BluetoothSocket bluetoothSocket;
    private InputStream is;

    public ReceiveMessageThread(BluetoothSocket socket)
    {
        bluetoothSocket=socket;
        InputStream im=null;
        try
        {
            im=bluetoothSocket.getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        is=im;
    }

    public  void run()
    {
        while(true)
        {
            int count =0;
            while(count==0)
            {
                try
                {
                    count = is.available();
                    byte buf[] = new byte[1024];
                    if (is != null)
                    {
                        is.read(buf, 0, buf.length);
                        String message = new String(buf);
                        //BuletoothMainActivity.UpdateRevMsg(message);
                        is.close();
                    }
                }
                catch (IOException e)
                {
                    break;
                }
            }
        }
    }



    public void cannel()
    {
        try
        {
            bluetoothSocket.close();
        }
        catch (IOException e)
        {

        }
    }

    @Override
    public void destroy()
    {
        cannel();
    }

}



