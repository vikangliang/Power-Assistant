package e.dell.buletoothapplication;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import static e.dell.buletoothapplication.ClientThread.os;

public class newMainActivity extends titleActivity {

    public static int a;
    public static TextView tv,tv1,tv2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        init();

    }

    public void init() {
        Button send1, send2, send3, send4, send5, send6,del;
        send1 = findViewById(R.id.button1);
        send2 = findViewById(R.id.button2);
        send3 = findViewById(R.id.button3);
        send4 = findViewById(R.id.button4);
        send5 = findViewById(R.id.button5);
        send6 = findViewById(R.id.button6);
        //del = findViewById(R.id.button7);
        tv = findViewById(R.id.tv);
        tv1= findViewById(R.id.tv1);
        tv2= findViewById(R.id.tv2);

        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x01;
                /**if (os != null) {
                    try {
                        byte datatest[] = {(byte) 0xAA, 0x55, (byte) newMainActivity.a, 0x3C, (byte) 0xC3};
                        os.write(datatest);
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }**/
                write(a);
            }
        });


        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x02;
                write(a);
            }
        });

        send3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x03;
                write(a);
            }
        });

        send4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x04;
                write(a);
            }
        });

        send5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x05;
                write(a);
            }
        });

        send6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 0x06;
                write(a);
            }
        });



    }

    public void write(int a) {
        if (os != null) {
            try {
                byte datatest[] = {(byte) 0xAA, 0x55, (byte) newMainActivity.a, 0x3C, (byte) 0xC3};
                os.write(datatest);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
