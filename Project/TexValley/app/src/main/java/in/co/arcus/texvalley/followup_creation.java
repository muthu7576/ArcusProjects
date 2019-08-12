package in.co.arcus.texvalley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;
import static in.co.arcus.texvalley.customer_creation.area;
import static in.co.arcus.texvalley.customer_creation.txtName;
import static in.co.arcus.texvalley.customer_creation.txtPhone;
import static in.co.arcus.texvalley.customer_creation.txtemail;
import static in.co.arcus.texvalley.oppurtunity_creation.mcretedtepickrtxts;
import static in.co.arcus.texvalley.oppurtunity_creation.mdatepickertexts;
import static in.co.arcus.texvalley.oppurtunity_creation.oppty_value;


public class followup_creation extends Fragment {

    View view;
    String[] followup_assgnarray;
    Spinner followup_spinner;
    private static TextView datepickerremainder,Timepickerset;
    View dialogviews,dialogviewtime;
    AlertDialog alertDialog,alertDialogtime;
    Button creationsubmit;
    private static EditText descriptionsfollowup;
    String followupassignto;
    HashMap<String ,String> followupassignmapper;
    private Calendar calendar;
    private String format = "";
    TimePicker timePicker;
    public static String latitudecrds;
    public static String longitudecrds;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;
    public static String projectlatitude,projectlongitude;
/*
    int hour,min;
*/
    public followup_creation(){

    }


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.followup_creation,container,false);
        creationsubmit = (Button)view.findViewById(R.id.next_up);
        getBackendleadsrce();
        descriptionsfollowup = (EditText)view.findViewById(R.id.editcus_followupdescrptn) ;
        descriptionsfollowup.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(descriptionsfollowup.getText()!=null && descriptionsfollowup.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Value is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        datepickerremainder = (TextView)view.findViewById(R.id.date_time_setter);
        Timepickerset = (TextView)view.findViewById(R.id.date_time_setterfollowup);
        dialogviews = View.inflate(view.getContext(),R.layout.datepicker,null);

        dialogviewtime = View.inflate(view.getContext(),R.layout.timepicker,null);
        /*calendar = Calendar.getInstance();
        showTime(hour, min);
         hour = calendar.get(Calendar.HOUR_OF_DAY);
         min = calendar.get(Calendar.MINUTE);*/
        String[] perms = {"android.permission.ACCESS_FINE_LOCATION"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, 200);
        }
        alertDialog = new AlertDialog.Builder(view.getContext()).create();
        dialogviews.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialogviews.findViewById(R.id.date_picker);

                String dateset = datePicker.getDayOfMonth() +"-" + (datePicker.getMonth()+1) + "-" +datePicker.getYear() ;

                datepickerremainder.setText(dateset);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogviews);

        view.findViewById(R.id.date_time_setter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });


        alertDialogtime = new AlertDialog.Builder(view.getContext()).create();
        dialogviewtime.findViewById(R.id.date_time_picker1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = (TimePicker) dialogviewtime.findViewById(R.id.time_picker);

                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                showTime( hour, min);
                Timepickerset.setText(new StringBuilder().append(hour).append(":").append(min)
                        .append(" ").append(format));
                alertDialogtime.dismiss();
            }
        });
        alertDialogtime.setView(dialogviewtime);

        view.findViewById(R.id.date_time_setterfollowup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogtime.show();


            }
        });



        creationsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                try{
                    if(((txtPhone.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("contactnumber",txtPhone.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        txtPhone.setError( "Contact Number is required!" );
                        return;
                    }

                    if(((txtName.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("name",txtName.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        txtName.setError( "Customer Name is required!" );
                        return;
                    }
                    if(((txtemail.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("email",txtemail.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        txtemail.setError( "Email is required!" );
                        return;
                    }
                    if(((area.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("area",area.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        area.setError( "Area is required!" );
                        return;
                    }


                    if(((oppty_value.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("values",oppty_value.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        oppty_value.setError( "Value is required!" );
                        return;
                    }
                    if(((mdatepickertexts.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("expecteddate",mdatepickertexts.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        /*mdatepickertexts.setError( "Expected close date is required!" );*/
                        return;
                    }

                    if(((mcretedtepickrtxts.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("createdate",mcretedtepickrtxts.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        /*mcretedtepickrtxts.setError( "Expected close date is required!" );*/
                        return;
                    }

                    if(((descriptionsfollowup.getText()).length() > 0) ) {
                        Tabsactivity.opputunityPayload.put("description",descriptionsfollowup.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        /*mcretedtepickrtxts.setError( "Expected close date is required!" );*/
                        return;
                    }


                    if(datepickerremainder.getText().length() >0){
                        Tabsactivity.opputunityPayload.put("Remainderdate",datepickerremainder.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        /*datepickerremainder.setError( "Expected close date is required!" );*/
                        return;
                    }

                    if(Timepickerset.getText().length() >0){
                        Tabsactivity.opputunityPayload.put("Time",Timepickerset.getText().toString());
                    }
                    else {
                        Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
                        /*Timepickerset.setError( "Expected close date is required!" );*/
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                GPSTracker gps = new GPSTracker(view.getContext());
                // check if GPS enabled
                if(gps.canGetLocation()){
                    LocationManager locationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);

                    if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        Log.d("Your Location", "latitude:" + gps.getLatitude()
                                + ", longitude: " + gps.getLongitude());
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        latitudecrds = String.valueOf(latitude);
                        longitudecrds = String.valueOf(longitude);
                        // \n is for new line
                        // Toast.makeText(view.getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    }

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }


                getfollowupdata();

                    if(!gps.isGPSenabled){
                        gps.showSettingsAlert();


                    }

                    else{
                        GPSTracker gpss = new GPSTracker(view.getContext());
                        // check if GPS enabled
                        if(gpss.canGetLocation()){
                            LocationManager locationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);

                            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                Log.d("Your Location", "latitude:" + gpss.getLatitude()
                                        + ", longitude: " + gpss.getLongitude());
                                double latitude = gpss.getLatitude();
                                double longitude = gpss.getLongitude();
                                latitudecrds = String.valueOf(latitude);
                                longitudecrds = String.valueOf(longitude);
                                // \n is for new line
                                // Toast.makeText(view.getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            }

                        }else{
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gpss.showSettingsAlert();
                        }
                        getfollowupdata();


                        String url = "http://texvalley.arcus.co.in/texvalleyapp/oppurtunity_creationdatabase.php?user_id="+dashboard.userid;
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("url", url);
                        params.put("requestmethod", "POST");
                        params.put("inputparams", Tabsactivity.opputunityPayload);
                        asynctask oppurtunity_creationdatabase = new asynctask(params);
                        oppurtunity_creationdatabase.setListener(new asynctask.MyListener() {
                            @Override
                            public void onpreExecutemethod() {

                            }

                            @Override
                            public void onPostExecutemetod(String result) throws JSONException {
                                System.out.println("the status output is: " + result);
                                JSONObject jsonObject = new JSONObject(result);
                                checkingcreation(jsonObject);

                            }
                        });
                        oppurtunity_creationdatabase.execute();

                    }


                /*getfollowupdata();*/

            }
        });

        return view;


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    //Success Perform Task Here
                    break;
                case Activity.RESULT_CANCELED:
                    Log.e("GPS","User denied to access location");
                    openGpsEnableSetting();
                    break;
            }
        } else if (requestCode == REQUEST_ENABLE_GPS) {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGpsEnabled) {
                openGpsEnableSetting();
            } else {

            }
        }
    }

    private void openGpsEnableSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }



    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){
            case 200:
                boolean locationPermision = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                break;
        }

    }

    private void checkingcreation(JSONObject result) throws JSONException {

    if((result.getInt("status") == 200)){
        Toast.makeText(view.getContext(),"Successfully Created",Toast.LENGTH_SHORT).show();
        System.out.println("the all values are:"+Tabsactivity.opputunityPayload);
        Intent intent = new Intent(view.getContext(),oppurtunity.class);
        startActivity(intent);
    }else{
        Toast.makeText(view.getContext(),"Enter Valid Values",Toast.LENGTH_LONG).show();
    }


    }

    /* public void setTime(View view) {

     }*/
    public void showTime(int hour, int min) {

        if (hour == 0) {
           hour  += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }


    }

    private void getfollowupdata() {

        try {
            Tabsactivity.opputunityPayload.put("description",descriptionsfollowup.getText().toString());
            Tabsactivity.opputunityPayload.put("Assignto",followupassignmapper.get(followupassignto));
            Tabsactivity.opputunityPayload.put("Remainderdate",datepickerremainder.getText().toString());
            Tabsactivity.opputunityPayload.put("Time",Timepickerset.getText().toString());
            Tabsactivity.opputunityPayload.put("latitude",latitudecrds);
            Tabsactivity.opputunityPayload.put("longitude",longitudecrds);

            System.out.println("The output is " + descriptionsfollowup.getText().toString());
            System.out.println("The output is " +followupassignmapper.get(followupassignto));
            System.out.println("The output is " + datepickerremainder.getText().toString());
            System.out.println("The output is " + Timepickerset.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setadapterfollowupassgn(){
        followup_spinner = (Spinner)view.findViewById(R.id.followup_assign);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,followup_assgnarray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        followup_spinner.setAdapter(adapter);
        followup_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                followupassignto = adapterView.getItemAtPosition(i).toString();
                System.out.println("The assign to is:"+followupassignto+" "+followupassignmapper.get(followupassignto));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private  void  getBackendleadsrce(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/followup_Assign.php?userroleid="+dashboard.userroleid
                +"&userid="+dashboard.userid;
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask followups = new asynctask(params);
        followups.setListener(new asynctask.MyListener(){
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String result) throws JSONException {
                System.out.println("The output is " + result);
                JSONObject jsonObjectfollowupassgn = new JSONObject(result);
                jsonarraysaasignto(jsonObjectfollowupassgn);
            }
        });
        followups.execute();
    }

    private void jsonarraysaasignto(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        followup_assgnarray = new String[jsonArray.length()];
        followupassignmapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("stages:"+jsonArray.getJSONObject(i));
            followup_assgnarray[i] = jsonArray.getJSONObject(i).get("username").toString();
            followupassignmapper.put(followup_assgnarray[i],jsonArray.getJSONObject(i).get("id").toString());
            setadapterfollowupassgn();
        }
    }


}
    /*AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
        // Getting the Container Layout of the ListView
        LinearLayout linearLayoutParent = (LinearLayout) container;

        // Getting the inner Linear Layout
        LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);

        // Getting the Country TextView
        TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

        Toast.makeText(getContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
    }
};*/