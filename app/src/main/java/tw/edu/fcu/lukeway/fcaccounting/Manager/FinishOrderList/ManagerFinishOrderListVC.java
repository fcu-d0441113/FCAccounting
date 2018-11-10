package tw.edu.fcu.lukeway.fcaccounting.Manager.FinishOrderList;

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
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList.ManagerOrderListVC;
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList.ManagerOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerFinishOrderListVC extends AppCompatActivity {

    private ListView payList;
    private ArrayList<PayData> payData = ManagerFinishOrderListVM.payData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_finish_order_list_vc);

        payList = (ListView) findViewById(R.id.managerFinishOrderList);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter();
        payList.setAdapter(myCustomAdapter);
        setEventListener();
    }

    private void setEventListener() {
        payList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
            amount.setText("已達成目標 "+payData.get(i).targetAmount+" $NT");
            date.setText("到期日期：" + payData.get(i).timeLimit);

            return view;
        }
    }
}
