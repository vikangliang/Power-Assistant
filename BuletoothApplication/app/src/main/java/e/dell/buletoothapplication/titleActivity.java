package e.dell.buletoothapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class titleActivity extends Activity implements View.OnClickListener {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.titlelayout);
        button = findViewById(R.id.button_backward);
        textView=findViewById(R.id.tview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_backward:
                onBackward(v);
                break;
            case R.id.tview:
                onBackward(v);
                break;
        }

    }

    private void showBackwardView() {
        if (button != null) {
            button.setText("返回");
            button.setVisibility(View.VISIBLE);
        }
    }

    protected void onBackward(View v) {
        finish();
    }

}
