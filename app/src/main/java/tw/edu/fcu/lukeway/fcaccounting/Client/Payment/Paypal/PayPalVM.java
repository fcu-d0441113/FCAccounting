package tw.edu.fcu.lukeway.fcaccounting.Client.Payment.Paypal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tw.edu.fcu.lukeway.fcaccounting.Client.OrderList.ClientOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayPalData;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class PayPalVM extends AppCompatActivity {

    private RequestQueue mQueue;
    private SharedPreferences userProfileManager;
    private String paymentType;
    private int count;
    private String amount;
    public static PayPalData payPalData;
    private ArrayList<PayData> payData = ClientOrderListVM.payData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_vm);
        mQueue = Volley.newRequestQueue(this);
        userProfileManager = getSharedPreferences("userProfile",0);
        Intent intent = getIntent();
        paymentType = intent.getStringExtra("PAYMENTTYPE");
        count = intent.getIntExtra("COUNT", 0);
        amount = intent.getStringExtra("AMOUNT");
        jsonParse();
    }

    private void jsonParse() {

        String url = "http://10.0.2.2:8888/order.php";
        //打包parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("paymentType", paymentType);
            parameters.put("memberId", userProfileManager.getString("NID","").replace("\"",""));
            parameters.put("payId", payData.get(count).payId);
            parameters.put("payName", payData.get(count).payName);
            parameters.put("amount", amount);
            parameters.put("fee", payData.get(count).feeAmount);
        }catch (Exception e){
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String totalAmount = String.valueOf(Integer.valueOf(payData.get(count).feeAmount) + Integer.valueOf(amount));
                            payPalData = new PayPalData(response.getString("payment_url").toString(),
                                    totalAmount+"");
                            finish();   //結束，關閉頁面
                            startActivity(new Intent(PayPalVM.this, PayPalVC.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //處理錯誤，並關閉頁面
                //showErrorDialog(Integer.toString(error.networkResponse.statusCode),new String(error.networkResponse.data));
                Log.v("Error", new String(error.networkResponse.data));
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
