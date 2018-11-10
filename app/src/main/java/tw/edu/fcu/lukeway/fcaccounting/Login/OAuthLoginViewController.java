package tw.edu.fcu.lukeway.fcaccounting.Login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import tw.edu.fcu.lukeway.fcaccounting.Client.Home.ClientHomePage;
import tw.edu.fcu.lukeway.fcaccounting.HomePage;
import tw.edu.fcu.lukeway.fcaccounting.Manager.Home.ManagerHomePage;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class OAuthLoginViewController extends AppCompatActivity {

    private WebView webview;
    private SharedPreferences userProfileManager;
    private String nid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_login_view_controller);
        initComponent();
        webviewsetting();
        userProfileManager = getSharedPreferences("userProfile",0);
        webview.loadUrl("http://10.0.2.2:8888/login.php");
    }

    private void initComponent() {
        webview = (WebView) findViewById(R.id.webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webviewsetting() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                if(url.toString().startsWith("https://testapi.kid7.club/fcuOAuth/Login.aspx")){
                    webview.evaluateJavascript("javascript:document.getElementsByName('nid')[0].value='"+userProfileManager.getString("NID","").replace("\"","")+"';"
                            , new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                        }
                    });
                } else if (url.toString().startsWith("http://10.0.2.2:8888/login_OAuth.php")) {
                    webview.evaluateJavascript("javascript:document.getElementById('nid').innerHTML;", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            userProfileManager.edit().putString("NID", value).commit();
                            //Log.v("test",value);
                            Toast toast = Toast.makeText(OAuthLoginViewController.this,
                                    "歡迎使用 FC Accounting!! "+value.replace("\"","")+"!!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                    webview.evaluateJavascript("javascript:document.getElementById('permission').innerHTML;", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            userProfileManager.edit().putString("permission", value).commit();
                            finish();   //完成，關閉頁面
                            value = value.replace("\"", "");
                            //Log.v("permission", value);
                            if (value.equals("1")) {
                                startActivity(new Intent(OAuthLoginViewController.this, ManagerHomePage.class));
                            } else {
                                startActivity(new Intent(OAuthLoginViewController.this, ClientHomePage.class));
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitDialog(OAuthLoginViewController.this).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private Dialog ExitDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("系统信息");
        builder.setMessage("確定要回到登入頁面?");
        builder.setPositiveButton("確定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                        startActivity(new Intent(OAuthLoginViewController.this, HomePage.class));
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        return builder.create();
    }
}
