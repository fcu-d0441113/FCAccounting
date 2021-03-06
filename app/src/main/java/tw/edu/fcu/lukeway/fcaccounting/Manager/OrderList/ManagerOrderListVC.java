package tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderListContent.ManagerOrderListContentVM;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerOrderListVC extends AppCompatActivity {

    private ListView payList;
    private ArrayList<PayData> payData = ManagerOrderListVM.payData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_list_vc);

        payList = (ListView) findViewById(R.id.payList);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter();
        payList.setAdapter(myCustomAdapter);
        setEventListener();
    }

    private void setEventListener() {
        payList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("payName", payData.get(i).payName);
                intent.putExtra("dateLimit", payData.get(i).timeLimit);
                intent.putExtra("amount", payData.get(i).currentAmount + " / " + payData.get(i).targetAmount + " $NT");
                intent.putExtra("payId", payData.get(i).payId);
                intent.setClass(ManagerOrderListVC.this, ManagerOrderListContentVM.class);
                startActivity(intent);
            }
        });
    }

    private class MyCustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return payData.size();
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

            payName.setText(payData.get(i).payName);
            amount.setText(payData.get(i).currentAmount+" / "+payData.get(i).targetAmount+" $NT");
            date.setText("到期日期：" + payData.get(i).timeLimit);

            return view;
        }
    }
}
