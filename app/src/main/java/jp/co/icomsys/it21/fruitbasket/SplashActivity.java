package jp.co.icomsys.it21.fruitbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler hdl = new Handler();
        hdl.postDelayed(new splashHandler(), 4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class splashHandler implements Runnable {
        public void run() {
            //Intent intent = new Intent(getApplication(), BookListActivity.class);
            //Intent intent = new Intent(getApplication(), BookListsActivity.class);
            Intent intent = new Intent(getApplication(), BookRegistrationActivity.class);
            //Intent intent = new Intent(getApplication(), WebSearchActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
