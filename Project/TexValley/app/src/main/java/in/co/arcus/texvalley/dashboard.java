package in.co.arcus.texvalley;


        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;

        import android.os.Handler;
        import android.support.design.widget.NavigationView;

        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;

        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;

        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.DatePicker;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;

        import static android.app.PendingIntent.getActivity;


public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle mtoggle;
    TextView mtextheading;
    private DrawerLayout mdrawer;
     JSONArray objectsfordash;
    dashboardAdapter listAdaptor;
    int listItemsValue;
    TextView datepickerremainder;
    AlertDialog alertDialog;
    View dialogviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hideshow);
        relativeLayout.setVisibility(View.INVISIBLE);

        datepickerremainder = (TextView)findViewById(R.id.setdateactions);
        dialogviews = View.inflate(dashboard.this,R.layout.datepicker,null);




        ListView mlistviewssettingfordash =(ListView)findViewById(R.id.listfordashboard);
        mtextheading = (TextView)findViewById(R.id.list_heading);
        mdrawer = (DrawerLayout)findViewById(R.id.drawer_action);
        mtoggle = new ActionBarDrawerToggle(this,mdrawer,R.string.open,R.string.close);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBackendlistview();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.dateaction,menu);
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
           /* final int itemid = item.getItemId();*/


           this.findViewById(R.id.setdateactions).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   alertDialog.show();

               }
           });

            alertDialog = new AlertDialog.Builder(this).create();
            dialogviews.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePicker datePicker = (DatePicker) dialogviews.findViewById(R.id.date_picker);

                    String dateset = datePicker.getYear() +"-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth();

                    item.setTitle(dateset);
                    getBackEndtitledate(dateset);
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogviews);




        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }




    private void getBackEndtitledate(String datetitlevalues) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", datetitlevalues);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/datetitle.php";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("url", url);
            params.put("requestmethod", "POST");
            params.put("inputparams", jsonObject);

            asynctask menudates = new asynctask(params);
            menudates.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String response) throws JSONException {
                    System.out.println("the status date  is: " + response);
                    /*JSONObject jsonObject = new JSONObject(response);*/
                setlistadapterfordash(response);
                    /*getdatetitle(jsonObject);*/

                }

            });
            menudates.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.nav_oppurtunity){

            Intent intent = new Intent(dashboard.this, oppurtunity.class);
            dashboard.this.finish();
            startActivity(intent);

        }


        else if (id == R.id.nav_personlogout){
            Intent intent = new Intent(this,MainActivity.class);
            this.finish();
            startActivity(intent);
        }


        mdrawer.openDrawer(GravityCompat.START);
        return true;

    }

    public  void  getBackendlistview(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/dashboard_listview.php";
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask listviewfordash = new asynctask(params);
        listviewfordash.setListener(new asynctask.MyListener(){
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String response)throws JSONException {
                System.out.println("Post Execute"+response.toString());
                setlistadapterfordash(response);
            }
        });
        listviewfordash.execute();
    }
    private  void  setlistadapterfordash(String response) throws JSONException{


        final JSONObject jsResponse = new JSONObject(response);
        final JSONArray responseJsArray = jsResponse.getJSONArray("response");


        listAdaptor = new dashboardAdapter(this, R.layout.listviewfordashboard, responseJsArray);
         final ListView listviewssettingfordash =(ListView)findViewById(R.id.listfordashboard);
        listviewssettingfordash.setAdapter(listAdaptor);


        if(responseJsArray.length() != 0){


            listviewssettingfordash.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                    try {

                        JSONObject jsonObject = responseJsArray.getJSONObject(position);
                        String listids = jsonObject.get("id").toString();
                        oppurtunitiesfollowupfrdash(listids);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
            SwipeListViewTouchListener touchListener =
                    new SwipeListViewTouchListener(
                            listviewssettingfordash,
                            new SwipeListViewTouchListener.OnSwipeCallback() {
                                @Override
                                public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
                                    Log.i(this.getClass().getName(), "swipe left : pos="+reverseSortedPositions[0]);
                                    // TODO : YOUR CODE HERE FOR LEFT ACTION
                                    try {

                                        JSONObject jsonObject = responseJsArray.getJSONObject(reverseSortedPositions[0]);
                                        String listidswipe = jsonObject.get("id").toString();
                                        oppurtunitiesfollowupfrdash(listidswipe);
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
                                        oppurtunitiesfollowupfrdash(listidswipesright);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            true, // example : left action = dismiss
                            false); // example : right action without dismiss animation

            listviewssettingfordash.setOnTouchListener(touchListener);

// Setting this scroll listener is required to ensure that during ListView scrolling,
// we don't look for swipes.
            listviewssettingfordash.setOnScrollListener(touchListener.makeScrollListener());

            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hideshow);
            relativeLayout.setVisibility(View.INVISIBLE);

        }
        else {
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hideshow);
            relativeLayout.setVisibility(View.VISIBLE);
        }

    }


    public void oppurtunitiesfollowupfrdash(String listviewid)  {

        Intent intent = new Intent(dashboard.this,Tabsactivity2.class);
        intent.putExtra("id",listviewid);
        startActivity(intent);

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}


