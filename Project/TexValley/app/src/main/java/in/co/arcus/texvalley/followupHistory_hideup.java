package in.co.arcus.texvalley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class followupHistory_hideup extends Fragment {

    View view;
    TextView datepickerremainder,Timepickerset;
    Button histryhideup;
    Button upadtehideupsbtn;
    View dialogviews,dialogviewtime;
    AlertDialog alertDialog,alertDialogtime;
    private String format = "";
    TimePicker timePicker;
    EditText remarkhideups;
    String[] mdeofcntcthideuparray,classificationhideuparray,visitedbyhideuparray,stagearrayhideup;
    Spinner mdeofcntcthideup,classificationhideup,visitedbyhideup,stagehideup;
    String followupcntct,followupclssfy,followupuser,followupstage;
    HashMap<String ,String> followupcntctmapper,followupclssfymapper,followupusermapper,followupstagemapper;
    public followupHistory_hideup(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.followuphistory_hideup,container,false);
        getBackendleadsrce();
        getBackEndclssfy("id", Tabsactivity2.oppurtunity_id);
        getBackEndvistdup("id",Tabsactivity2.oppurtunity_id );
        getBackEndstgshidup("id",Tabsactivity2.oppurtunity_id );
        remarkhideups = (EditText)view.findViewById(R.id.remarkedit);
        datepickerremainder = (TextView)view.findViewById(R.id.date_time_setterhide);
        Timepickerset = (TextView)view.findViewById(R.id.date_time_setterfollowuphide);
        dialogviews = View.inflate(view.getContext(),R.layout.datepicker,null);
        histryhideup = (Button) view.findViewById(R.id.histryviewget);
        upadtehideupsbtn = (Button)view.findViewById(R.id.btnupdatefollowup);
        dialogviewtime = View.inflate(view.getContext(),R.layout.timepicker,null);

        alertDialog = new AlertDialog.Builder(view.getContext()).create();
        dialogviews.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = (DatePicker) dialogviews.findViewById(R.id.date_picker);

                String dateset =  datePicker.getDayOfMonth()+"-" + (datePicker.getMonth()+1) + "-" + datePicker.getYear();

                datepickerremainder.setText(dateset);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogviews);

        view.findViewById(R.id.date_time_setterhide).setOnClickListener(new View.OnClickListener() {
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

        view.findViewById(R.id.date_time_setterfollowuphide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogtime.show();


            }
        });

        histryhideup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent;
               intent = new Intent(getActivity(),recyclerview_main.class);

               startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        upadtehideupsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBackendfllwupdate();
                String url = "http://texvalley.arcus.co.in/texvalleyapp/followup_update.php?user_id="+dashboard.userid;

                System.out.println("the updated url is:"+url);
                HashMap<String,Object> params = new HashMap<String, Object>();
                params.put("url",url);
                params.put("requestmethod","POST");
                params.put("inputparams",Tabsactivity2.followupdate);
                asynctask followupupdatesfctn = new asynctask(params);
                followupupdatesfctn.setListener(new asynctask.MyListener() {
                    @Override
                    public void onpreExecutemethod() {

                    }

                    @Override
                    public void onPostExecutemetod(String result) throws JSONException {
                        System.out.println("the status output is: " + result);
                        JSONObject jsonObject = new JSONObject(result);
                        checkingcreationoffllwupdate(jsonObject);
                    }
                });
                followupupdatesfctn.execute();
            }
        });

        return view;

    }

    private void checkingcreationoffllwupdate(JSONObject result) throws JSONException {
        if((result.getInt("status") == 200)){
            /* result.get("cno");*/
            Toast.makeText(view.getContext(),"Successfully Created",Toast.LENGTH_SHORT).show();
            System.out.println("the all values are:"+Tabsactivity2.followupdate);
            Intent intent = new Intent(view.getContext(),oppurtunity.class);
            startActivity(intent);
        }else {
            Toast.makeText(view.getContext(),"Enter Valid Values",Toast.LENGTH_LONG).show();
        }
    }

    private void getBackendfllwupdate() {
        try {
            Tabsactivity2.followupdate.put("opportunity_id",Tabsactivity2.oppurtunity_id);
            Tabsactivity2.followupdate.put("contact_id",followupcntctmapper.get(followupcntct));
            Tabsactivity2.followupdate.put("classification",followupclssfymapper.get(followupclssfy));
            Tabsactivity2.followupdate.put("user_id",followupusermapper.get(followupuser));
            Tabsactivity2.followupdate.put("stage_id",followupstagemapper.get(followupstage));
            Tabsactivity2.followupdate.put("remark",remarkhideups.getText().toString());
            Tabsactivity2.followupdate.put("reminddate",datepickerremainder.getText().toString());
            Tabsactivity2.followupdate.put("remindtime",Timepickerset.getText().toString());

            System.out.println("The output is " + Tabsactivity2.oppurtunity_id);
            System.out.println("The output is " +followupcntctmapper.get(followupcntct));
            System.out.println("The output is " + followupclssfymapper.get(followupclssfy));
            System.out.println("The output is " + followupusermapper.get(followupuser));

            System.out.println("The output is " + followupstagemapper.get(followupstage));
            System.out.println("The output is " +remarkhideups.getText().toString());
            System.out.println("The output is " + datepickerremainder.getText().toString());
            System.out.println("The output is " + Timepickerset.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


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



    private void setadapterfollowupassgn(){
        mdeofcntcthideup = (Spinner)view.findViewById(R.id.slctmdeofcncthide);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,mdeofcntcthideuparray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mdeofcntcthideup.setAdapter(adapter);
        mdeofcntcthideup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                followupcntct = parent.getItemAtPosition(position).toString();
                System.out.println("The assign to is:"+followupcntct+" "+followupcntctmapper.get(followupcntct));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private  void  getBackendleadsrce(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/mode_of_cntct.php";
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
                JSONObject mdeofhideups = new JSONObject(result);
                jsonarraysaasigntomde(mdeofhideups);
            }
        });
        followups.execute();
    }

    private void jsonarraysaasigntomde(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        mdeofcntcthideuparray = new String[jsonArray.length()];
           followupcntctmapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("stages:"+jsonArray.getJSONObject(i));
            mdeofcntcthideuparray[i] = jsonArray.getJSONObject(i).get("contact").toString();
           followupcntctmapper.put(mdeofcntcthideuparray[i],jsonArray.getJSONObject(i).get("id").toString());
            setadapterfollowupassgn();
        }
    }

    private void setadapterforclssfy() {

        classificationhideup = (Spinner)view.findViewById(R.id.visiteduphide);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,classificationhideuparray);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classificationhideup.setAdapter(adapters);
        classificationhideup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                followupclssfy = parent.getItemAtPosition(position).toString();
                System.out.println("The assign to is:"+followupclssfy+" "+followupclssfymapper.get(followupclssfy));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getBackEndclssfy(String id, String clickListViewId) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", clickListViewId);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/classification_hideup.php";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("url", url);
            params.put("requestmethod", "POST");
            params.put("inputparams", jsonObject);

            asynctask clssfyhides = new asynctask(params);
            clssfyhides.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String result) throws JSONException {
                    System.out.println("the status classification is: " + result);
                    JSONObject jsonObject = new JSONObject(result);

                    gettingresponseobjectclssfy(jsonObject);

                }

            });
            clssfyhides.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gettingresponseobjectclssfy(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        classificationhideuparray = new String[jsonArray.length()];
        followupclssfymapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("stages:"+jsonArray.getJSONObject(i));
            classificationhideuparray[i] = jsonArray.getJSONObject(i).get("name").toString();
            followupclssfymapper.put(classificationhideuparray[i],jsonArray.getJSONObject(i).get("name").toString());
            setadapterforclssfy();
        }
    }


    //visitedby hideups...

    private void setadapterforvistdhidup() {

        visitedbyhideup = (Spinner)view.findViewById(R.id.slctclssfyuphide);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,visitedbyhideuparray);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitedbyhideup.setAdapter(adapters);
        visitedbyhideup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                followupuser = parent.getItemAtPosition(position).toString();
                System.out.println("The assign to is:"+followupuser+" "+followupusermapper.get(followupuser));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getBackEndvistdup(String id, String clickListViewIdvstd) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", clickListViewIdvstd);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/visitedby_hideup.php";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("url", url);
            params.put("requestmethod", "POST");
            params.put("inputparams", jsonObject);

            asynctask clssfyhides = new asynctask(params);
            clssfyhides.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String result) throws JSONException {
                    System.out.println("the status output is: " + result);
                    JSONObject jsonObject = new JSONObject(result);

                    gettingresponseobjectvstdup(jsonObject);

                }

            });
            clssfyhides.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gettingresponseobjectvstdup(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        visitedbyhideuparray = new String[jsonArray.length()];
          followupusermapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("stages:"+jsonArray.getJSONObject(i));
            visitedbyhideuparray[i] = jsonArray.getJSONObject(i).get("username").toString();
            followupusermapper.put(visitedbyhideuparray[i],jsonArray.getJSONObject(i).get("id").toString());
            setadapterforvistdhidup();
        }
    }

    //stages hideup to.....

    private void setadapterforstgshidup() {

        stagehideup = (Spinner)view.findViewById(R.id.slctfollowuphidehstry);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,stagearrayhideup);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stagehideup.setAdapter(adapters);
        stagehideup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                followupstage = parent.getItemAtPosition(position).toString();
                System.out.println("The assign to is:"+followupstage+" "+followupstagemapper.get(followupstage));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getBackEndstgshidup(String id, String clickListViewIdstgs) {

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", clickListViewIdstgs);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/stage_hideup.php";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("url", url);
            params.put("requestmethod", "POST");
            params.put("inputparams", jsonObject);

            asynctask clssfyhides = new asynctask(params);
            clssfyhides.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String result) throws JSONException {
                    System.out.println("the status output is: " + result);
                    JSONObject jsonObject = new JSONObject(result);

                    gettingresponseobjectstgs(jsonObject);

                }

            });
            clssfyhides.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gettingresponseobjectstgs(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        stagearrayhideup = new String[jsonArray.length()];
        followupstagemapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("stages:"+jsonArray.getJSONObject(i));
            stagearrayhideup[i] = jsonArray.getJSONObject(i).get("stage").toString();
            followupstagemapper.put(stagearrayhideup[i],jsonArray.getJSONObject(i).get("id").toString());
            setadapterforstgshidup();
        }
    }

}
