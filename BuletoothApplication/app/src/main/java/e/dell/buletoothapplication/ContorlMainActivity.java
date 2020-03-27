package e.dell.buletoothapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class ContorlMainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contorl_main);



        bottomNavigationBar=(BottomNavigationBar)findViewById(R.id.bottombar);
        bottomNavigationBar.setActiveColor("#1769cb");
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_bluetooth,"蓝牙"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home1,"首页"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_wifi,"Wifi"));
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.initialise();



        BottomNavigationBar bottomNavigationBar = this.bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {
                Intent intent;
                intent = new Intent(ContorlMainActivity.this, BuletoothMainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }
}
