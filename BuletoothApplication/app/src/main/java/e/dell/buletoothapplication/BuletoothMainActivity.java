package e.dell.buletoothapplication;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import static android.view.View.OnClickListener;


public class BuletoothMainActivity extends AppCompatActivity {
    public static int num = -1;
    public static String mRevMsg, string;
    public static Handler handler = new Handler();
    public ArrayAdapter adapter;
    public ArrayList<String> arrayList = new ArrayList<>();
    public ArrayList<String> deviceName = new ArrayList<>();
    private TextView textView;
    private Button sendn, scann, openn, servern, closen, beChecked;
    private BottomNavigationBar bottomNavigationBar;
    private OutputStream os;
    public BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
    private final int REQUEST_ENABLE_BT = 0xa01;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 0xb01;
    int BOND_NONE = 10; //配对没有成功
    int BOND_BONDING = 11; //配对中
    int BOND_BONDED = 12; //配对成功
    public Toast toast;
    public EditText editText = null;
    public static TextView textView2;
    public Context context;
    public ListView listView;
    public static ClientThread clientThread;
    public static BluetoothDevice device;
    public static String mge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buletooth_main);


        textView = (TextView) findViewById(R.id.text);
        final TextView textView0 = (TextView) findViewById(R.id.text0);
        listView = (ListView) findViewById(R.id.list);
        sendn = (Button) findViewById(R.id.send);
        scann = (Button) findViewById(R.id.scan);
        openn = (Button) findViewById(R.id.open);
        servern = (Button) findViewById(R.id.server);
        closen = (Button) findViewById(R.id.close);
        beChecked = (Button) findViewById(R.id.cannotScan);
        editText = (EditText) findViewById(R.id.edit);
        textView2 = (TextView) findViewById(R.id.take);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottombar);
        bottomNavigationBar.setActiveColor("#1769cb");
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_bluetooth, "蓝牙"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home1, "首页"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_wifi, "Wifi"));
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.initialise();


        //定义底部菜单栏的事件
        BottomNavigationBar bottomNavigationBar = this.bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


        //定义适配器
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, deviceName);

        //给列表添加适配器
        listView.setAdapter(adapter);

        if (blueadapter.isEnabled()) {
            textView0.setText("蓝牙状态：开");
        } else {
            textView0.setText("蓝牙状态：关");
        }

        //定义scan控件的事件
        scann.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断蓝牙适配器是否开启
                if (!blueadapter.isEnabled()) {
                    //开启蓝牙适配器
                    blueadapter.enable();
                    //开始扫描
                    doDiscovry();
                }
                blueadapter.startDiscovery();
                toast = Toast.makeText(getApplicationContext(), "正在搜索设备。。。", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        //注册广播接收信号
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //用BroadcastReceiver 来取得结果
        registerReceiver(bluetoothReceiver, intentFilter);


        //定义开启open控件的事件
        openn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断是否有蓝牙
                if (blueadapter != null) {
                    //判断蓝牙是否开启
                    if (blueadapter.isEnabled()) {
                        //提示语：蓝牙已开启
                        toast = Toast.makeText(getApplicationContext(), "蓝牙已开启", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        //开启蓝牙
                        blueadapter.enable();
                        //提示语：蓝牙开启成功
                        toast = Toast.makeText(getApplicationContext(), "蓝牙开启成功", Toast.LENGTH_SHORT);
                        toast.show();
                        textView0.setText("蓝牙状态：开");

                    }
                } else {
                    //提示语：设备没有蓝牙
                    toast = Toast.makeText(getApplicationContext(), "设备没有蓝牙", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });


        //定义server控件的事件
        servern.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断蓝牙是否开启
                if (blueadapter.isEnabled()) {
                    //开启蓝牙服务器端
                    AcceptThread acceptThread = new AcceptThread(blueadapter, context);
                    acceptThread.start();
                    toast = Toast.makeText(getApplicationContext(), "服务器已开启", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    toast = Toast.makeText(getApplicationContext(), "请先开启蓝牙", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        //定义控件close事件
        closen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blueadapter.isEnabled()) {
                    //关闭蓝牙
                    blueadapter.disable();
                    toast = Toast.makeText(getApplicationContext(), "蓝牙关闭成功", Toast.LENGTH_SHORT);
                    toast.show();
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    textView0.setText("蓝牙状态：关");
                } else {
                    toast = Toast.makeText(getApplicationContext(), "蓝牙已关闭", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        //定义cannotScan控件的事件
        beChecked.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (blueadapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    //设置300秒内可被扫描
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);
                }
                toast = Toast.makeText(getApplicationContext(), "300秒内可被检测", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        //定义列表Item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                device = adapter.getRemoteDevice(arrayList.get(i));
                mge = editText.getText().toString() + "\r\n";//获得编辑文本框里的文字
                clientThread = new ClientThread(device, mge, context);
                clientThread.start();
            }


        });


        //定义send控件的点击事件
        sendn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuletoothMainActivity.this, newMainActivity.class);
                startActivity(intent);

            }
        });
        applypermission();
    }


    //重写BroadcastReceiver类
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //扫描到其他的设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //将设备名和设备地址添加到deviceName列表
                deviceName.add("设备名：" + device.getName() + "\n" + "设备地址：" + device.getAddress() + "\n");
                //添加设备的地址到ArrayList列表
                arrayList.add(device.getAddress());
                //更新
                adapter.notifyDataSetChanged();

            }



        }
    };


    public void doDiscovry() {
        if (blueadapter.isDiscovering()) {
            //判断蓝牙是否正在扫描，如果是调用取消扫描方法；如果不是，则开始扫描
            blueadapter.cancelDiscovery();
        } else {
            blueadapter.startDiscovery();
        }
    }


    //安卓6.0动态获取权限
   /** @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                textView.setText("打开蓝牙成功");
            }

            if (resultCode == RESULT_CANCELED) {
                textView.setText("放弃打开蓝牙");
            }

        } else {
            textView.setText("蓝牙异常");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

                break;
        }

    }
**/
   public void applypermission() {
       if (Build.VERSION.SDK_INT >= 23) {
           //检查是否已经给了权限
           int checkpermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                   Manifest.permission.ACCESS_FINE_LOCATION);
           if (checkpermission != PackageManager.PERMISSION_GRANTED) {//没有给权限
               Log.e("permission", "动态申请");
               //参数分别是当前活动，权限字符串数组，requestcode
               ActivityCompat.requestPermissions(BuletoothMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
           }
       }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(BuletoothMainActivity.this, "已授权", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BuletoothMainActivity.this, "拒绝授权", Toast.LENGTH_SHORT).show();
        }

    }


    //注销广播
    protected void onDestroy() {
        super.onDestroy();//解除注册
        unregisterReceiver(bluetoothReceiver);
    }


    //更新消息到编辑文本框
    public static void UpdateRevMsg(String revMsg) {
        mRevMsg = revMsg;

        switch (num) {
            /**case 0:
             handler.post(RefreshTextView);
             break;
             case 1:
             handler.post(RefreshTextView1);
             break;
             case 2:{
             handler.post(RefreshTextView2);
             num=-1;}
             break;
             **/
            case 0:
                string = mRevMsg + ".";
                break;
            case 1: {
                mRevMsg = string + mRevMsg;
                handler.post(RefreshTextView);
            }
            break;
            case 2:
                string = mRevMsg + ".";
                break;
            case 3:{
                mRevMsg = string + mRevMsg;
                handler.post(RefreshTextView1);
            }
            break;
            case 4:
                string = mRevMsg + ".";
                break;
            case 5:
            {
                mRevMsg = string + mRevMsg;
                handler.post(RefreshTextView2);
                num=-1;
            }
            break;



        }


        //handler.post(RefreshTextView);
    }


    private static Runnable RefreshTextView = new Runnable() {
        @Override
        public void run() {

            newMainActivity.tv.setText(mRevMsg);
        }
    };

    private static Runnable RefreshTextView1 = new Runnable() {
        @Override
        public void run() {

            newMainActivity.tv1.setText(mRevMsg);
        }
    };


    private static Runnable RefreshTextView2 = new Runnable() {
        @Override
        public void run() {

            newMainActivity.tv2.setText(mRevMsg);
        }
    };

    public static String bytesToIntString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

}


//定义客户端类
class ClientThread extends Thread {
    public static OutputStream os;
    private InputStream is;
    String message;
    public BluetoothDevice device = null;
    public Context context;
    public BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();


    public ClientThread(BluetoothDevice device, String message, Context context) {
        this.device = device;
        this.message = message;
        this.context = context;

    }


    public void run() {

        new Thread(new Runnable() {
            /**
             *
             */
            @Override
            public void run() {
                try {
                    final BluetoothSocket socket = (BluetoothSocket) device.getClass().getDeclaredMethod("createRfcommSocket", new Class[]{int.class}).invoke(device, 1);
                    //final BluetoothSocket socket= device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    adapter.cancelDiscovery();
                    socket.connect();//连接
                   // Toast.makeText(context,"连接成功",Toast.LENGTH_SHORT).show();
                    is = socket.getInputStream();
                    os = socket.getOutputStream();
                    //BufferedReader in=new BufferedReader(new InputStreamReader(is));


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (true) {
                                    int count = 0;
                                    while (count == 0) {
                                        count = is.available();
                                    }
                                    byte buf[] = new byte[count];
                                    if (buf != null) {
                                        is.read(buf);
                                        BuletoothMainActivity.num++;
                                        String message = BuletoothMainActivity.bytesToIntString(buf);

                                        BuletoothMainActivity.UpdateRevMsg(message);
                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    /** if (os != null) {

                     if (newMainActivity.a != 0) {
                     byte datatest[] = {(byte) 0xAA, 0x55, (byte) newMainActivity.a, 0x3C, (byte) 0xC3};
                     os.write(datatest);
                     os.flush();
                     }


                     }

                     os.close();
                     **/


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException E) {
                    E.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    public void writen(byte bytes[]) {
        try {
            os.write(bytes);
        } catch (IOException e) {

        }

    }


}



