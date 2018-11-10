package tw.edu.fcu.lukeway.fcaccounting.Client.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tw.edu.fcu.lukeway.fcaccounting.Client.OrderList.ClientOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Client.OrderRecord.ClientOrderRecordVM;
import tw.edu.fcu.lukeway.fcaccounting.HomePage;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ClientHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_hone_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clientHome) {
            return true;
        } else if (id == R.id.clientList) {
            startActivity(new Intent(ClientHomePage.this, ClientOrderListVM.class));
            return true;
        } else if (id == R.id.clientOrderRecord) {
            startActivity(new Intent(ClientHomePage.this, ClientOrderRecordVM.class));
            return true;
        } else if (id == R.id.clientLogout) {
            finish();
            startActivity(new Intent(ClientHomePage.this, HomePage.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
