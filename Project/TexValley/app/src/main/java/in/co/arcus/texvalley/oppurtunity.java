package in.co.arcus.texvalley;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class oppurtunity extends AppCompatActivity {


    private static Context mContext;
    opportunityAdapter listAdaptor;
    String listviewid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oppurtunity);
        mContext = getApplicationContext();
        Button mcreate = findViewById(R.id.create_oppurtunity);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBackendlistview();
    }
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this,dashboard.class);
        this.finish();
        startActivity(intent);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("key back pressed");
            Intent intent = new Intent(this,dashboard.class);
            this.finish();
            startActivity(intent);

        }
        return false;
    }

    public static Context getAppContext() {
        return oppurtunity.mContext;
    }


    public void createopportunity(View view){
    Intent intent = new Intent(oppurtunity.this,Tabsactivity.class);
    oppurtunity.this.finish();
    startActivity(intent);
}
    public  void  getBackendlistview(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/oppurtunity_listview.php?user_id="+dashboard.userid+"&role_id="+dashboard.userroleid;
System.out.println("the url listview is "+url);
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask listview = new asynctask(params);
        listview.setListener(new asynctask.MyListener(){
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String response)throws JSONException{
                setlistadapter(response);
            }
        });
        listview.execute();
    }
    private  void  setlistadapter(String response) throws JSONException{


            JSONObject jsResponse = new JSONObject(response);
            final JSONArray responseJsArray = jsResponse.getJSONArray("response");


            listAdaptor = new opportunityAdapter(this, R.layout.listview, responseJsArray);
            final ListView listviewssetting =(ListView)findViewById(R.id.product_list);
            listviewssetting.setAdapter(listAdaptor);
        listviewssetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                try {

                    JSONObject jsonObject = responseJsArray.getJSONObject(position);
                    String listids = jsonObject.get("id").toString();
                    oppurtunitiesfollowup(listids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        SwipeListViewTouchListener touchListener =
                new SwipeListViewTouchListener(
                        listviewssetting,
                        new SwipeListViewTouchListener.OnSwipeCallback() {
                            @Override
                            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
                                Log.i(this.getClass().getName(), "swipe left : pos="+reverseSortedPositions[0]);
                                // TODO : YOUR CODE HERE FOR LEFT ACTION
                                try {

                                    JSONObject jsonObject = responseJsArray.getJSONObject(reverseSortedPositions[0]);
                                    String listidswipe = jsonObject.get("id").toString();
                                    oppurtunitiesfollowup(listidswipe);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
                                Log.i(dashboard.class.getClass().getName(), "swipe right : pos="+reverseSortedPositions[0]);
                                // TODO : YOUR CODE HERE FOR RIGHT ACTION
                                try {

                                    JSONObject jsonObject = responseJsArray.getJSONObject(reverseSortedPositions[0]);
                                    String listidswipesright = jsonObject.get("id").toString();
                                    oppurtunitiesfollowup(listidswipesright);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        false, // example : left action = dismiss
                        false); // example : right action without dismiss animation

        listviewssetting.setOnTouchListener(touchListener);

// Setting this scroll listener is required to ensure that during ListView scrolling,
// we don't look for swipes.
        listviewssetting.setOnScrollListener(touchListener.makeScrollListener());


}

    public void oppurtunitiesfollowup(String listviewid)  {

    Intent intent = new Intent(oppurtunity.this,Tabsactivity2.class);
     intent.putExtra("id",listviewid);
    startActivity(intent);

    }




}


