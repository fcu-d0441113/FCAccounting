package tw.edu.fcu.lukeway.fcaccounting.Manager.OrderListContent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Data.OrderRecord;
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList.ManagerOrderListVC;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerOrderListContentVC extends AppCompatActivity {

    private TextView payName;
    private TextView date;
    private TextView amount;
    private ListView orderList;
    private String payNameStr;
    private String dateStr;
    private String amountStr;
    public static ArrayList<OrderRecord> orderRecords = ManagerOrderListContentVM.orderRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order_list_content_vc);
        Intent intent = getIntent();
        payNameStr = intent.getStringExtra("payName");
        dateStr = intent.getStringExtra("dateLimit");
        amountStr = intent.getStringExtra("amount");
        initComponent();
    }

    private void initComponent() {
        payName = (TextView) findViewById(R.id.payName);
        date = (TextView) findViewById(R.id.dateLimit);
        amount = (TextView) findViewById(R.id.amount);
        orderList = (ListView) findViewById(R.id.managerOrderListContent);
        payName.setText(payNameStr);
        date.setText(dateStr);
        amount.setText(amountStr);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter();
        orderList.setAdapter(myCustomAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderRecords.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.pay_list_body, null);
            TextView payName = (TextView)view.findViewById(R.id.textview);
            TextView amount = (TextView)view.findViewById(R.id.payAmount);
            TextView date = (TextView) view.findViewById(R.id.dateLimit);

            payName.setText(orderRecords.get(i).memberId);
            amount.setText(orderRecords.get(i).totalPrice+" $NT");
            date.setText(orderRecords.get(i).orderToken);

            return view;
        }
    }
}
