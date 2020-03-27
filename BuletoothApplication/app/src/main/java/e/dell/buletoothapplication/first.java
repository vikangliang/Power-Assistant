package e.dell.buletoothapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class first extends Activity {
    private My f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_first);
        Button button;

        myGUI();
        button=findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                My.c=1;
            }
        });
    }

    private void myGUI() {
        //f = new My(this);
        setContentView(R.layout.first);
    }
}
