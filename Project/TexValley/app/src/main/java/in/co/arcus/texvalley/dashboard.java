package in.co.arcus.texvalley;


        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Build;
        import android.os.Bundle;

        import android.os.Handler;
        import android.support.annotation.RequiresApi;
        import android.support.design.widget.NavigationView;

        import android.support.v4.app.FragmentManager;
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
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Locale;

        import static android.app.PendingIntent.getActivity;


public class dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private ActionBarDrawerToggle mtoggle;
    TextView mtextheading;
    private DrawerLayout mdrawer;
    JSONArray objectsfordash;
    dashboardAdapter listAdaptor;
    int listItemsValue;
    TextView datepickerremainder;
    AlertDialog alertDialog;
    View dialogviews;
    public static String userroleid;
    public static String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Intent intent = getIntent();
        userroleid = intent.getStringExtra("role_id");
        userid = intent.getStringExtra("user_id");

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.hideshow);
        relativeLayout.setVisibility(View.INVISIBLE);



        datepickerremainder = (TextView) findViewById(R.id.setdateactions);

        dialogviews = View.inflate(dashboard.this, R.layout.datepicker, null);


        ListView mlistviewssettingfordash = (ListView) findViewById(R.id.listfordashboard);
        mtextheading = (TextView) findViewById(R.id.list_heading);
        mdrawer = (DrawerLayout) findViewById(R.id.drawer_action);
        mtoggle = new ActionBarDrawerToggle(this, mdrawer, R.string.open, R.string.close);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_bar);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBackendlistview();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dateaction, menu);
        final MenuItem datefunction = menu.findItem(R.id.setdateactions);
        String date_n = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault()).format(new Date());
        datefunction.setTitle(date_n);
        datefunction.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                alertDialog.show();
                return true;

            }

        });

        alertDialog = new AlertDialog.Builder(this).create();
        dialogviews.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialogviews.findViewById(R.id.date_picker);

                String dateset = (datePicker.getDayOfMonth()) + "-" + (datePicker.getMonth() + 1) + "-" +datePicker.getYear() ;


               datefunction.setTitle(dateset);
                getBackEndtitledate(dateset, "role_id", userroleid, "user_id", userid);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogviews);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item))  {
            return true;
        }
        return true;
    }


    private void getBackEndtitledate(String datetitlevalues, String role_id, String userroleid, String user_id, String userid) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", datetitlevalues);
            jsonObject.put("role_id", userroleid);
            jsonObject.put("user_id", userid);
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.nav_oppurtunity) {

            Intent intent = new Intent(dashboard.this, oppurtunity.class);
            intent.putExtra("user_id",userid);
            intent.putExtra("role_id",userroleid);
            startActivity(intent);

        } else if (id == R.id.nav_personlogout) {
            Intent intent = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(intent);
        }


        mdrawer.openDrawer(GravityCompat.START);
        return true;

    }

    public void getBackendlistview() {
        String url = "http://texvalley.arcus.co.in/texvalleyapp/dashboard_listview.php?user_id=" + dashboard.userid + "&role_id=" + dashboard.userroleid;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("requestmethod", "GET");
        asynctask listviewfordash = new asynctask(params);
        listviewfordash.setListener(new asynctask.MyListener() {
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String response) throws JSONException {
                System.out.println("Post Execute" + response.toString());
                setlistadapterfordash(response);
            }
        });
        listviewfordash.execute();
    }

    private void setlistadapterfordash(String response) throws JSONException {


        final JSONObject jsResponse = new JSONObject(response);
        final JSONArray responseJsArray = jsResponse.getJSONArray("response");


        listAdaptor = new dashboardAdapter(this, R.layout.listviewfordashboard, responseJsArray);
        final ListView listviewssettingfordash = (ListView) findViewById(R.id.listfordashboard);
        listviewssettingfordash.setAdapter(listAdaptor);


        if (responseJsArray.length() != 0) {


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
                                public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                                    Log.i(this.getClass().getName(), "swipe left : pos=" + reverseSortedPositions[0]);
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
                                public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {
                                    Log.i(dashboard.class.getClass().getName(), "swipe right : pos=" + reverseSortedPositions[0]);
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

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.hideshow);
            relativeLayout.setVisibility(View.INVISIBLE);

        } else {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.hideshow);
            relativeLayout.setVisibility(View.VISIBLE);
        }

    }


    public void oppurtunitiesfollowupfrdash(String listviewid) {

        Intent intent = new Intent(dashboard.this, Tabsactivity2.class);
        intent.putExtra("id", listviewid);
        startActivity(intent);

    }

    private static final long delay = 2000L;
    private boolean mRecentlyBackPressed = false;
    private Handler mExitHandler = new Handler();
    private Runnable mExitRunnable = new Runnable() {

        @Override
        public void run() {
            mRecentlyBackPressed = false;
        }
    };

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();

        if (manager.getBackStackEntryCount() > 1) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();

        } else {
            // Otherwise, ask user if he wants to leave :)
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface arg0, int arg1) {

                            System.out.println("thge kill values");

                            finish();
                            moveTaskToBack(true);

                        }
                    }).create().show();
        }
    }



}

/* final int itemid = item.getItemId();*/


       /* this.findViewById(R.id.setdateactions).setOnClickListener(new View.OnClickListener() {
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

                String dateset = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" +datePicker.getYear() ;

                item.setTitle(dateset);
                getBackEndtitledate(dateset, "role_id", userroleid, "user_id", userid);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogviews);*/

