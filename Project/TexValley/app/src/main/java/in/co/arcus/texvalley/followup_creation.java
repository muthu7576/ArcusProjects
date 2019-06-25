package in.co.arcus.texvalley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class followup_creation extends Fragment {

    View view;
    String[] followup_assgnarray;
    Spinner followup_spinner;
    TextView datepickerremainder,Timepickerset;
    View dialogviews,dialogviewtime;
    AlertDialog alertDialog,alertDialogtime;
    Button creationsubmit;
    EditText descriptionsfollowup;
    String followupassignto;
    HashMap<String ,String> followupassignmapper;
    private Calendar calendar;
    private String format = "";
    TimePicker timePicker;

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

        datepickerremainder = (TextView)view.findViewById(R.id.date_time_setter);
        Timepickerset = (TextView)view.findViewById(R.id.date_time_setterfollowup);
        dialogviews = View.inflate(view.getContext(),R.layout.datepicker,null);

        dialogviewtime = View.inflate(view.getContext(),R.layout.timepicker,null);
        /*calendar = Calendar.getInstance();
        showTime(hour, min);
         hour = calendar.get(Calendar.HOUR_OF_DAY);
         min = calendar.get(Calendar.MINUTE);*/

        alertDialog = new AlertDialog.Builder(view.getContext()).create();
        dialogviews.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialogviews.findViewById(R.id.date_picker);

                String dateset = datePicker.getYear() +"-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth();

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
                Timepickerset.setText(new StringBuilder().append(hour).append(" : ").append(min)
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
                getfollowupdata();
                String url = "http://texvalley.arcus.co.in/texvalleyapp/oppurtunity_creationdatabase.php";
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
                Intent intent = new Intent(view.getContext(),oppurtunity.class);
                startActivity(intent);

            }
        });

        return view;


    }

    private void checkingcreation(JSONObject result) throws JSONException {

    if((result.getInt("status") == 200)){
       /* result.get("cno");*/
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
        String url ="http://texvalley.arcus.co.in/texvalleyapp/followup_Assign.php";
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