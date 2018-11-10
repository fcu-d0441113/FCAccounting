package tw.edu.fcu.lukeway.fcaccounting.Manager.OrderListContent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Data.OrderRecord;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.Foundation.MyJsonArrayRequest;
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList.ManagerOrderListVC;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerOrderListContentVM extends AppCompatActivity {

    private RequestQueue mQueue;
    private String payId;
    private String payName;
    private String dateLimit;
    private String amount;
    public static ArrayList<OrderRecord> orderRecords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order_list_content_vm);
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        payId = intent.getStringExtra("payId");
        payName = intent.getStringExtra("payName");
        dateLimit = intent.getStringExtra("dateLimit");
        amount = intent.getStringExtra("amount");
        jsonParse();
    }

    private void jsonParse() {

        String url = "http://10.0.2.2:8888/payOrderRecord.php";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("payId", payId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            orderRecords.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                orderRecords.add(new OrderRecord(
                                        jsonObject.getString("orderId"),
                                        jsonObject.getString("memberId"),
                                        jsonObject.getString("payId"),
                                        jsonObject.getString("payName"),
                                        jsonObject.getString("totalPrice"),
                                        jsonObject.getString("fee"),
                                        jsonObject.getString("orderDate"),
                                        jsonObject.getString("paymentType"),
                                        jsonObject.getString("status"),
                                        jsonObject.getString("orderToken")
                                ));
                            }
                            Intent intent = new Intent();
                            intent.putExtra("payName", payName);
                            intent.putExtra("dateLimit", dateLimit);
                            intent.putExtra("amount", amount);
                            intent.setClass(ManagerOrderListContentVM.this, ManagerOrderListContentVC.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mQueue.add(request);
    }
}
