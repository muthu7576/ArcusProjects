package in.co.arcus.texvalley;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Tabsactivity2 extends AppCompatActivity {



    /*public static JSONObject opputunityPayload = new JSONObject();*/
    private ViewPager mViewPager1;
    public static String oppurtunity_id;
    private TabLayout tabLayout1;
    private AppBarLayout appBarLayout1;
    public  static Context appContext;
public static JSONObject followupdate = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext=getApplicationContext();
        setContentView(R.layout.tabs_file2);
        Intent intent = getIntent();
        oppurtunity_id = intent.getStringExtra("id");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tabLayout1 = (TabLayout) findViewById(R.id.tabs1);
        appBarLayout1 = (AppBarLayout) findViewById(R.id.appbar1);
        mViewPager1 = (ViewPager) findViewById(R.id.container1);
//Adding Fragments
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        /*adapter.addFragment(new customer_creation(),"CUSTOMER");*/
        adapter.addFragment(new opportunity_hideup(), "OPPORTUNITY");
        adapter.addFragment(new followupHistory_hideup(), "FOLLOWUP");

//Adapter Setup...
        mViewPager1.setAdapter(adapter);
        tabLayout1.setupWithViewPager(mViewPager1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.historymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int itemid = item.getItemId();
       if(itemid == R.id.histryclick){
           Intent intent = new Intent(this,recyclerview_main.class);
           startActivity(intent);
       }else {
           Intent intent = new Intent(this,oppurtunity.class);
           this.finish();
           startActivity(intent);
       }
        return true;
    }
    /*@Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this,oppurtunity.class);
        this.finish();
        startActivity(intent);
        return true;
    }*/
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


    }


