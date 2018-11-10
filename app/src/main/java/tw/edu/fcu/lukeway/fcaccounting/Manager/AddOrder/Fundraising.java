package tw.edu.fcu.lukeway.fcaccounting.Manager.AddOrder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import tw.edu.fcu.lukeway.fcaccounting.HomePage;
import tw.edu.fcu.lukeway.fcaccounting.Manager.Home.ManagerHomePage;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class Fundraising extends AppCompatActivity {

    private TextView nid;
    private EditText tital;
    private EditText date;
    private EditText totalPrice;
    private EditText fee;
    private Button submit;
    private String NID;
    private RequestQueue mQueue;
    private SharedPreferences userProfileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundraising);
        mQueue = Volley.newRequestQueue(this);
        userProfileManager = getSharedPreferences("userProfile",0);

        nid = (TextView) findViewById(R.id.NID);
        tital = (EditText) findViewById(R.id.titalInput);
        date = (EditText) findViewById(R.id.dateInput);
        totalPrice = (EditText) findViewById(R.id.priceInput);
        fee = (EditText) findViewById(R.id.feeInput);
        submit = (Button) findViewById(R.id.submitFund);

        nid.setText(userProfileManager.getString("NID","").replace("\"",""));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tital.getText().toString();
                String orderDate = date.getText().toString();
                String amount = totalPrice.getText().toString();
                String feeAmount = fee.getText().toString();

                jsonParse(name, orderDate, amount, feeAmount);
            }
        });
    }

    private void jsonParse(String name, String orderDate, String amount, String feeAmount) {

        String url = "http://10.0.2.2:8888/createFund.php";
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("name", name);
            parameters.put("orderDate", orderDate);
            parameters.put("amount", amount);
            parameters.put("feeAmount", feeAmount);
            parameters.put("nid", userProfileManager.getString("NID","").replace("\"",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(Fundraising.this,response.getString("successMsg"),Toast.LENGTH_LONG).show();
                            Log.v("successMSG", "successful");
                            startActivity(new Intent(Fundraising.this, ManagerHomePage.class));
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
