package tw.edu.fcu.lukeway.fcaccounting.Client.Payment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.edu.fcu.lukeway.fcaccounting.Client.Home.ClientHomePage;
import tw.edu.fcu.lukeway.fcaccounting.Data.LinePayData;
import tw.edu.fcu.lukeway.fcaccounting.HomePage;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class LinePayPaymentVC extends AppCompatActivity {

    private WebView linePayWebView;
    private LinePayData linePayData = LinePayPaymentVM.getLinePayData();
    private Dialog dialog;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_pay_payment_vc);
        initComponent();
        webUrl = linePayData.getPaymentUrlWeb();
        linePayWebView.loadUrl(webUrl);
    }

    private void initComponent() {
        linePayWebView = (WebView)findViewById(R.id.LinePayWebView);
        webviewsetting();
    }

    private void webviewsetting() {
        WebSettings webSettings = linePayWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        linePayWebView.setWebChromeClient(new WebChromeClient());
        linePayWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view,String url,Bitmap favicon){
                if (url.toString().startsWith("http://10.0.2.2:8888/Checkout/LinePayPaid.php?orderId="+linePayData.getOrderId()
                        +"&amount="+linePayData.getAmount()+"&transactionId=" + linePayData.getTransectionId())) {
                    dialog = ProgressDialog.show(LinePayPaymentVC.this, "付款確認中", "請稍候...",true);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.toString().startsWith("http://10.0.2.2:8888/Checkout/LinePayPaid.php?orderId="+linePayData.getOrderId()
                        +"&amount="+linePayData.getAmount()+"&transactionId=" + linePayData.getTransectionId())) {
                    dialog.dismiss();
                    showFinishedDialog();
                }
            }
        });
    }

    private void showFinishedDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("付款成功")
                .setPositiveButton("ＯＫ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();   //結束，關閉頁面
                        startActivity(new Intent(LinePayPaymentVC.this, ClientHomePage.class));
                    }
                }).setMessage("已確認付款成功! \n訂單編號："+linePayData.getOrderId()
                        +"\n交易序號："+linePayData.getTransectionId()
                        +"\n交易金額：$"+linePayData.getAmount()
                        +"\n付款貨幣："+linePayData.getCurrency()
                        +"\n\n跳回菜單頁").create();
        dialog.show();
    }
}
