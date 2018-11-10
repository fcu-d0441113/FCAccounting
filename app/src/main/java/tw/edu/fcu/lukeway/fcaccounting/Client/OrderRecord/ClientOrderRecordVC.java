package tw.edu.fcu.lukeway.fcaccounting.Client.OrderRecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Data.OrderRecord;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ClientOrderRecordVC extends AppCompatActivity {

    private ListView orderRecordListView;   //訂單列表
    private ArrayList<OrderRecord> orderRecords = ClientOrderRecordVM.orderRecords;    //獲得OrderRecordViewModel中得到的訂單資料

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_record_vc);
        initComponent();
    }

    //設置元件
    private void initComponent() {

        orderRecordListView = (ListView)findViewById(R.id.orderRecord);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter();
        orderRecordListView.setAdapter(myCustomAdapter);
    }

    //產生ListView內容
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

            view = getLayoutInflater().inflate(R.layout.layout_order_record_body, null);
            TextView payName = (TextView) view.findViewById(R.id.payName);
            TextView date = (TextView) view.findViewById(R.id.orderDate);
            TextView amount = (TextView) view.findViewById(R.id.amount);
            date.setText("到期日期：" + orderRecords.get(i).orderDate);
            payName.setText(orderRecords.get(i).payName);
            amount.setText("共NT$ "+orderRecords.get(i).totalPrice);
            notifyDataSetChanged();
            return view;
        }
    }
}
