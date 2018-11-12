package tw.edu.fcu.lukeway.fcaccounting.Client.Payment.Paypal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tw.edu.fcu.lukeway.fcaccounting.Client.Home.ClientHomePage;
import tw.edu.fcu.lukeway.fcaccounting.Data.PayPalData;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class PayPalVC extends AppCompatActivity {

    private WebView payPalWebView;
    private PayPalData payPalData = PayPalVM.payPalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal_vc);
        initComponent();
        payPalWebView.loadUrl(payPalData.getPayment_url());
    }

    private void initComponent() {
        payPalWebView = (WebView)findViewById(R.id.payPalWebView);
        webviewsetting();
    }

    private void webviewsetting() {
        WebSettings webSettings = payPalWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);

        payPalWebView.setWebChromeClient(new WebChromeClient());
        payPalWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.toString().startsWith("http://10.0.2.2:8888/payment-successful.html")){
                    showFinishedDialog();
                }
                else if(url.toString().startsWith("http://10.0.2.2:8888/payment-cancelled.html")){
                    showCancelledDialog();
                }
            }
        });
    }
    private void showCancelledDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("付款取消")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();   //結束，關閉頁面
                        startActivity(new Intent(PayPalVC.this, ClientHomePage.class));
                    }
                }).setMessage("已取消附款!\n將跳回菜單頁").create();
        dialog.show();
    }

    private void showFinishedDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("付款完成")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();   //結束，關閉頁面
                        startActivity(new Intent(PayPalVC.this, ClientHomePage.class));
                    }
                }).setMessage("已附款成功! 交易金額: NT "+payPalData.getAmount()+"$\n將跳回菜單頁").create();
        dialog.show();
    }
}
