package in.co.arcus.texvalley;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

public class Tabsactivity extends AppCompatActivity {



    public static JSONObject opputunityPayload = new JSONObject();
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_file);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//Adding Fragments
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new customer_creation(),"CUSTOMER");
        adapter.addFragment(new oppurtunity_creation(), "OPPORTUNITY");
        adapter.addFragment(new followup_creation(), "FOLLOWUP");

//Adapter Setup...
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            Intent intent = new Intent(this,oppurtunity.class);
            this.finish();
            startActivity(intent);

        return true;
    }
   /* @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this,oppurtunity.class);
        this.finish();
        startActivity(intent);
        return true;
    }*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("key back pressed");
            Intent intent = new Intent(this,oppurtunity.class);
            this.finish();
            startActivity(intent);

        }
        return false;
    }
    //spinner functions.....


}



