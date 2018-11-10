package tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList;

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

import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.Foundation.MyJsonArrayRequest;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerOrderListVM extends AppCompatActivity {

    private RequestQueue mQueue;
    public static ArrayList<PayData> payData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order_list_vm);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {

        String url = "http://10.0.2.2:8888/payList.php";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("name", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final MyJsonArrayRequest request = new MyJsonArrayRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            payData.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String responseString = response.getString(i);
                                String totalPrice = new JSONObject(responseString).getString("totalAmount");
                                String totalAmount =new JSONObject(totalPrice).getString("SUM(totalPrice)");
                                if (totalAmount == "null") {
                                    totalAmount = "0";
                                }
                                payData.add(new PayData(
                                        jsonObject.getString("payId"),
                                        jsonObject.getString("memberId"),
                                        jsonObject.getString("targetAmount"),
                                        jsonObject.getString("timeLimit"),
                                        jsonObject.getString("payName"),
                                        jsonObject.getString("feeAmount"),
                                        totalAmount
                                ));
                            }
                            startActivity(new Intent(ManagerOrderListVM.this, ManagerOrderListVC.class));
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
