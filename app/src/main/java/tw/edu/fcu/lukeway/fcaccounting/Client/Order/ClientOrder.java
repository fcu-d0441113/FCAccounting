package tw.edu.fcu.lukeway.fcaccounting.Client.Order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Client.OrderList.ClientOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Client.Payment.LinePay.LinePayPaymentVM;
import tw.edu.fcu.lukeway.fcaccounting.Client.Payment.iSunny.SunnyBankPaymentVM;
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
    private Spinner paymentTypeSpinner;
    private int count;
    private ArrayAdapter<CharSequence> paymentType;

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
                String Type = String.valueOf(paymentTypeSpinner.getLastVisiblePosition()+1);
                String price = nowAmount.getText().toString();
                Intent intent2 = new Intent();
                intent2.putExtra("PAYMENTTYPE", Type);
                intent2.putExtra("COUNT", count);
                intent2.putExtra("AMOUNT", price);
                Log.v("paymentType", Type);
                if (Type.equals("1")) {
                    intent2.setClass(ClientOrder.this, SunnyBankPaymentVM.class);
                    startActivity(intent2);
                }
                else if (Type.equals("3")) {
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
        paymentTypeSpinner = (Spinner) findViewById(R.id.paymentTypeSpinner);
    }

    private void setData() {
        payName.setText(payData.get(count).payName);
        amount.setText(payData.get(count).currentAmount + " / " + payData.get(count).targetAmount + " $NT");
        date.setText(payData.get(count).timeLimit);
        fee.setText(payData.get(count).feeAmount);
        paymentType = ArrayAdapter.createFromResource(
                this, R.array.paymentType_array, android.R.layout.simple_spinner_item );
        paymentType.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        paymentTypeSpinner.setAdapter(paymentType);
    }
}
