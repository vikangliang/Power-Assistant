package e.dell.buletoothapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class WelcomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(0,3000);
    }

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome()
    {
        Intent intent=new Intent(WelcomeActivity.this,BuletoothMainActivity.class);
        startActivity(intent);
        finish();
    }

}
