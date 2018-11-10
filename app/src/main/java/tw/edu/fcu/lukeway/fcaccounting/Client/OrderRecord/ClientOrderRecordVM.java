package tw.edu.fcu.lukeway.fcaccounting.Client.OrderRecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Data.OrderRecord;
import tw.edu.fcu.lukeway.fcaccounting.Foundation.MyJsonArrayRequest;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ClientOrderRecordVM extends AppCompatActivity {

    private RequestQueue mQueue;
    public static ArrayList<OrderRecord> orderRecords = new ArrayList<>();
    private SharedPreferences userProfileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_record_vm);
        userProfileManager = getSharedPreferences("userProfile",0);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {

        String url = "http://10.0.2.2:8888/orderRecord.php";

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("memberId", userProfileManager.getString("NID","").replace("\"",""));
            parameters.put("offset", "0");
            parameters.put("limit", "40");
        }catch (Exception e){
            e.printStackTrace();
        }
        final MyJsonArrayRequest request = new MyJsonArrayRequest (Request.Method.POST, url, parameters,
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finish();   //完成，將空白頁面刪除
                        startActivity(new Intent(ClientOrderRecordVM.this, ClientOrderRecordVC.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //處理錯誤，並關閉頁面
                showErrorDialog(Integer.toString(error.networkResponse.statusCode),new String(error.networkResponse.data));
            }
        });
        mQueue.add(request);
    }

    //處理錯誤
    private void showErrorDialog(String statusCode,String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("錯誤")
                .setMessage(statusCode + " , " + errorMessage.substring(12, errorMessage.length()-2) + ".")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                }).create();
        dialog.show();
    }
}
