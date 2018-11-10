package tw.edu.fcu.lukeway.fcaccounting.Client.Order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Client.OrderList.ClientOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Client.Payment.LinePayPaymentVM;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ClientOrder extends AppCompatActivity {

    private ArrayList<PayData> payData = ClientOrderListVM.payData;
    private TextView payName;
    private TextView amount;
    private TextView date;
    private TextView fee;
    private EditText nowAmount;
    private Button submit;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order);
        final Intent intent = this.getIntent();
        //取得傳遞過來的資料
        count = intent.getIntExtra("count",0);
        initComponent();
        setData();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int paymentType = 3;
                String price = nowAmount.getText().toString();
                Intent intent2 = new Intent();
                intent2.putExtra("PAYMENTTYPE", paymentType);
                intent2.putExtra("COUNT", count);
                intent2.putExtra("AMOUNT", price);
                if (paymentType == 3) {
                    intent2.setClass(ClientOrder.this, LinePayPaymentVM.class);
                    startActivity(intent2);
                }
            }
        });
    }

    private void initComponent(){
        payName = (TextView) findViewById(R.id.payName);
        amount = (TextView) findViewById(R.id.Amount);
        date = (TextView) findViewById(R.id.timeLimit);
        fee = (TextView) findViewById(R.id.feeAmount);
        nowAmount = (EditText) findViewById(R.id.nowPrice);
        submit = (Button) findViewById(R.id.submit);
    }

    private void setData() {
        payName.setText(payData.get(count).payName);
        amount.setText(payData.get(count).currentAmount + " / " + payData.get(count).targetAmount + " $NT");
        date.setText(payData.get(count).timeLimit);
        fee.setText(payData.get(count).feeAmount);
    }
}
