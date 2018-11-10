package tw.edu.fcu.lukeway.fcaccounting.Client.Payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import tw.edu.fcu.lukeway.fcaccounting.Client.OrderList.ClientOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Data.LinePayData;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayData;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class LinePayPaymentVM extends AppCompatActivity {

    private RequestQueue mQueue;
    private int paymentType;
    private int count;
    private String amount;
    private static LinePayData linePayData;
    private ArrayList<PayData> payData = ClientOrderListVM.payData;
    private SharedPreferences userProfileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_pay_payment_vm);
        userProfileManager = getSharedPreferences("userProfile",0);
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        paymentType = intent.getIntExtra("PAYMENTTYPE", -1);
        count = intent.getIntExtra("COUNT", 0);
        amount = intent.getStringExtra("AMOUNT");
        jsonParse();
    }

    private void jsonParse() {

        String url = "http://10.0.2.2:8888/order.php";
        //打包parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("paymentType", String.valueOf(paymentType));
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
                            Log.v("result", response.getString("returnCode"));
                            //Log.v("info", response.getString("info"));
                            String info = response.getString("info");
                            String paymentUrl = new JSONObject(info).getString("paymentUrl");
                            linePayData = new LinePayData(response.getString("amount").toString(),
                                    response.getString("productName").toString(),
                                    response.getString("orderId").toString(), "TWD");
                            linePayData.setPaymentUrlWeb(new JSONObject(paymentUrl).getString("web"));
                            linePayData.setPaymentUrlLine(new JSONObject(paymentUrl).getString("app"));
                            linePayData.setTransactionId(new JSONObject(info).getString("transactionId"));
                            //處理transactionReserveId
                            String weburl = linePayData.getPaymentUrlWeb();
                            int index = weburl.indexOf("transactionReserveId");
                            index = index + 21;
                            String transactionReserveId = weburl.substring(index, index + 86);
                            Log.v("transactionReserveId", transactionReserveId);
                            linePayData.setTransactionReserveId(transactionReserveId);
                            //處理loginUrl
                            String loginUrl = "https://access.line.me/dialog/oauth/weblogin?channelId=1410784205&redirectUrl=https%3A%2F%2Fsandbox-web-pay.line.me%2Fweb%2Fpayment%2FwaitPostLogin%3FtransactionReserveId%3D"
                                    + linePayData.getTransactionReserveId() + "&state=fkupdq71kjtqdqc3btlj8g6lgv";
                            linePayData.setLoginUrl(loginUrl);
                            finish();   //結束，關閉頁面
                            startActivity(new Intent(LinePayPaymentVM.this, LinePayPaymentVC.class));
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

    public static LinePayData getLinePayData() {
        return linePayData;
    }
}
