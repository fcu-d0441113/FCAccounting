package tw.edu.fcu.lukeway.fcaccounting.Manager.AddOrder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tw.edu.fcu.lukeway.fcaccounting.R;

public class OrderChoose extends AppCompatActivity {

    private Button submitOne;
    private Button submitTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_choose);

        submitOne = (Button) findViewById(R.id.submit1);
        submitTwo = (Button) findViewById(R.id.submit2);

        submitOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderChoose.this, Fundraising.class));
                finish();
            }
        });
    }
}
