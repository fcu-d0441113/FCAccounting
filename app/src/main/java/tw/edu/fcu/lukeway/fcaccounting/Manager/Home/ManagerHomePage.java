package tw.edu.fcu.lukeway.fcaccounting.Manager.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tw.edu.fcu.lukeway.fcaccounting.HomePage;
import tw.edu.fcu.lukeway.fcaccounting.Manager.AddOrder.OrderChoose;
import tw.edu.fcu.lukeway.fcaccounting.Manager.FinishOrderList.ManagerFinishOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.Manager.OrderList.ManagerOrderListVM;
import tw.edu.fcu.lukeway.fcaccounting.R;

public class ManagerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manager_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.managerHome) {
            return true;
        } else if (id == R.id.managerList) {
            startActivity(new Intent(ManagerHomePage.this, ManagerOrderListVM.class));
            return true;
        } else if (id == R.id.managerAddOrder) {
            startActivity(new Intent(ManagerHomePage.this, OrderChoose.class));
            return true;
        } else if (id == R.id.managerFinishList) {
            startActivity(new Intent(ManagerHomePage.this, ManagerFinishOrderListVM.class));
            return true;
        } else if (id == R.id.managerLogout) {
            finish();
            startActivity(new Intent(ManagerHomePage.this, HomePage.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
