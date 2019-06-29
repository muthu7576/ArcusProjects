package in.co.arcus.texvalley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class oppurtunity_creation extends Fragment {


    public static EditText opptyname;
    public static EditText oppty_value;
    View view;
        Spinner stageslists,classificationlists,enquirylist,leadsrce_status,typesofspinner;
        String[] listStagesArray,listEnquiry,leadsrce_array,typesofstagesarray;
        String[] classificationListarray = {"Cold","Hot","Warm"};
        View dialogView;
        AlertDialog alertDialog;
    public static  TextView mdatepickertexts;
       private String selctedstagelists,selectedenquirylists,selctedleadsrce,selectedclassificationlistst,selectedtypesofstagelistst;
        private  HashMap<String,String> selectedstagesmapper,selctedenquirymapper,selectedleadsrcemapper,selectedtypesofstagemapper;

    FloatingActionButton nextup;
    EditText descriptionleadsrce;
    TextView expctd_date;
    public oppurtunity_creation(){

    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.oppurtunity_creation,container,false);
        nextup = (FloatingActionButton) view.findViewById(R.id.next_up);
        expctd_date=(TextView)view.findViewById(R.id.date_time_set);

        opptyname=(EditText)view.findViewById(R.id.editcus_opptyname);
        opptyname.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(opptyname.getText()!=null && opptyname.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Opportunity name is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });
        oppty_value = (EditText)view.findViewById(R.id.editcus_value);
        oppty_value.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(oppty_value.getText()!=null && oppty_value.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Value is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });
        descriptionleadsrce=(EditText)view.findViewById(R.id.editcus_descrptn);
        classificationLists();
        getBackendsrce();
        getBackendsrceenquiry();
        getBackendleadsrce();
        nextup.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

    // check required fields...

    try{
        if(((opptyname.getText()).length() > 0) ) {
            Tabsactivity.opputunityPayload.put("oppurtunityname",opptyname.getText().toString());
        }
        else {
            Toast.makeText(getContext(),"Required fields should not be empty",Toast.LENGTH_LONG).show();
            opptyname.setError( "Opportunity name is required!" );

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
            mdatepickertexts.setError( "Expected close date is required!" );
            return;
        }

    }catch (JSONException e){
        e.printStackTrace();
    }

        getdatafrmoppty();
        TabLayout tabLayout = (TabLayout) ((Tabsactivity)getActivity()).findViewById(R.id.tabs);
        tabLayout.getTabAt(2).select();
        }
        });
        mdatepickertexts = (TextView)view.findViewById(R.id.date_time_set);

       /* mdatepickertexts.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(mdatepickertexts.getText()!=null && mdatepickertexts.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Opportunity name is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });*/
        dialogView = View.inflate(view.getContext(), R.layout.datepicker, null);
        alertDialog = new AlertDialog.Builder(view.getContext()).create();
        dialogView.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
        // TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

        String selectedDate = datePicker.getDayOfMonth() +"-" + (datePicker.getMonth()+1) + "-" + datePicker.getYear();
        System.out.println(selectedDate);
        mdatepickertexts.setText(selectedDate);
        // time = calendar.getTimeInMillis();
        alertDialog.dismiss();
        }});
        alertDialog.setView(dialogView);
        view.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        alertDialog.show();

        }
        });
        return view;
        }

private void getdatafrmoppty() {
    try {
        Tabsactivity.opputunityPayload.put("oppurtunityname",opptyname.getText().toString());
        Tabsactivity.opputunityPayload.put("expecteddate",expctd_date.getText().toString());
        Tabsactivity.opputunityPayload.put("categorystatuslists",selectedclassificationlistst);
        Tabsactivity.opputunityPayload.put("opptySalesstage",selectedstagesmapper.get(selctedstagelists));
        Tabsactivity.opputunityPayload.put("typesinfo",selectedtypesofstagemapper.get(selectedtypesofstagelistst));
        Tabsactivity.opputunityPayload.put("values",oppty_value.getText().toString());
        Tabsactivity.opputunityPayload.put("enquirylist",selctedenquirymapper.get(selectedenquirylists));
        Tabsactivity.opputunityPayload.put("leadsrcestatus",selectedleadsrcemapper.get(selctedleadsrce));
        Tabsactivity.opputunityPayload.put("descriptionleadsrce",descriptionleadsrce.getText().toString());

        System.out.println("The output oppurtunityname is " + opptyname.getText().toString());
        System.out.println("The output expecteddate is " + expctd_date.getText().toString());
        System.out.println("The output categorystatuslists is " + selectedclassificationlistst);
        System.out.println("The output opptySalesstage is " + selectedstagesmapper.get(selctedstagelists));
        System.out.println("The output opptySalesstage is " + selectedtypesofstagemapper.get(selectedtypesofstagelistst));

        System.out.println("The output values is " + oppty_value.getText().toString());
        System.out.println("The output enquirylist is " + selctedenquirymapper.get(selectedenquirylists));
        System.out.println("The output leadsrcestatus is " + selectedleadsrcemapper.get(selctedleadsrce));
        System.out.println("The output descriptionleadsrce is " +descriptionleadsrce.getText().toString());
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

private void classificationLists(){
        classificationlists = (Spinner)view.findViewById(R.id.listClassifications);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,classificationListarray);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classificationlists.setAdapter(adapters);
    classificationlists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedclassificationlistst = adapterView.getItemAtPosition(i).toString();
            System.out.println("Selected classificationlists :"+selectedclassificationlistst );
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    });

        }
//sales stage lists
private void setadapter(){
        stageslists = (Spinner)view.findViewById(R.id.listStages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,listStagesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stageslists.setAdapter(adapter);
    stageslists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selctedstagelists = adapterView.getItemAtPosition(i).toString();
            String typeinfoid = selectedstagesmapper.get(selctedstagelists);
            getstagesid(typeinfoid);
            System.out.println("Selected stageslists :"+selctedstagelists + "   "+selectedstagesmapper.get(selctedstagelists));
            System.out.println("Selected stageslists of typeinfo:"+typeinfoid);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    });
        }





    private  void  getBackendsrce(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/opportunity_stages.php";
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask computetasks = new asynctask(params);
        computetasks.setListener(new asynctask.MyListener(){
@Override
public void onpreExecutemethod() {

        }

@Override
public void onPostExecutemetod(String result) throws JSONException {
        System.out.println("The output is " + result);
        JSONObject jsonObject = new JSONObject(result);
        jsonarrays(jsonObject);
        }
        });
        computetasks.execute();
        }

private void jsonarrays(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        listStagesArray = new String[jsonArray.length()];
        selectedstagesmapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
        System.out.println("stages:"+jsonArray.getJSONObject(i));
        listStagesArray[i] = jsonArray.getJSONObject(i).get("stage").toString();
        selectedstagesmapper.put(listStagesArray[i],jsonArray.getJSONObject(i).get("id").toString());
        setadapter();
        }
        }
   //stages lists type spinner...


    //postmethod of type
    private void getstagesid(String idresult){

        String url = "http://texvalley.arcus.co.in/texvalleyapp/typesofstages.php";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("requestmethod", "POST");
        params.put("inputparams",idresult);

        asynctask stagestypesgetting = new asynctask(params);
        stagestypesgetting.setListener(new asynctask.MyListener() {
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String response) throws JSONException {
                System.out.println("the status typeinfo values is: "+response);
                JSONObject jsonobjectoftypesinfo = new JSONObject(response);
                jsonarraystypesinfo(jsonobjectoftypesinfo);
                /*gettypesofstageslisted();*/

            }

        });
        stagestypesgetting.execute();

    }

    private void setadapterforstagestype(){
        typesofspinner = (Spinner)view.findViewById(R.id.spintypesofstage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,typesofstagesarray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesofspinner.setAdapter(adapter);
        typesofspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedtypesofstagelistst = adapterView.getItemAtPosition(i).toString();
                System.out.println("Selected types of stage info :"+selectedtypesofstagelistst + "   "+selectedtypesofstagemapper.get(selectedtypesofstagelistst));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }


   /* private void gettypesofstageslisted() {

        String url ="http://texvalley.arcus.co.in/texvalleyapp/typesofstages.php";
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask typesofinfo = new asynctask(params);
        typesofinfo.setListener(new asynctask.MyListener(){
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String result) throws JSONException {
                System.out.println("The output is " + result);

            }
        });
        typesofinfo.execute();
    }*/



    private void jsonarraystypesinfo(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        typesofstagesarray = new String[jsonArray.length()];
        selectedtypesofstagemapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
            System.out.println("types info:"+jsonArray.getJSONObject(i));
            typesofstagesarray[i] = jsonArray.getJSONObject(i).get("type").toString();
            selectedtypesofstagemapper.put(typesofstagesarray[i],jsonArray.getJSONObject(i).get("id").toString());
            setadapterforstagestype();
        }
    }

// enguiry spinner...
private void setadapterenquiry(){
        enquirylist = (Spinner)view.findViewById(R.id.listEnquiry);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,listEnquiry);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enquirylist.setAdapter(adapter);
    enquirylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedenquirylists = adapterView.getItemAtPosition(i).toString();
            System.out.println("Selected enquirylists :"+selectedenquirylists + "   "+selctedenquirymapper.get(selectedenquirylists));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    });
        }

private  void  getBackendsrceenquiry(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/Enquirylists.php";
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask computetasks = new asynctask(params);
        computetasks.setListener(new asynctask.MyListener(){
@Override
public void onpreExecutemethod() {

        }

@Override
public void onPostExecutemetod(String result) throws JSONException {
        System.out.println("The output is " + result);
        JSONObject jsonObjectenquiry = new JSONObject(result);
        jsonarraysEnquiry(jsonObjectenquiry);
        }
        });
        computetasks.execute();
        }

private void jsonarraysEnquiry(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        listEnquiry = new String[jsonArray.length()];
        selctedenquirymapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
        System.out.println("stages:"+jsonArray.getJSONObject(i));
        listEnquiry[i] = jsonArray.getJSONObject(i).get("enquiryStatus").toString();
        selctedenquirymapper.put(listEnquiry[i],jsonArray.getJSONObject(i).get("id").toString());
        setadapterenquiry();
        }
        }

//leadsrce_statuses...
private void setadapterleadsrce(){
        leadsrce_status = (Spinner)view.findViewById(R.id.leadsrce_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,leadsrce_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadsrce_status.setAdapter(adapter);
    leadsrce_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selctedleadsrce = adapterView.getItemAtPosition(i).toString();
            System.out.println("Selected enquirylists :"+selctedleadsrce + "   "+selectedleadsrcemapper.get(selctedleadsrce));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    });
        }

private  void  getBackendleadsrce(){
        String url ="http://texvalley.arcus.co.in/texvalleyapp/leadsrce_status.php";
        HashMap<String,Object> params =  new HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask computetasks = new asynctask(params);
        computetasks.setListener(new asynctask.MyListener(){
@Override
public void onpreExecutemethod() {

        }

@Override
public void onPostExecutemetod(String result) throws JSONException {
        System.out.println("The output is " + result);
        JSONObject jsonObjectleadsrce = new JSONObject(result);
        jsonarraysleadsrce(jsonObjectleadsrce);
        }
        });
        computetasks.execute();
        }

private void jsonarraysleadsrce(JSONObject response) throws JSONException {
        JSONArray jsonArray = response.getJSONArray("response");
        leadsrce_array = new String[jsonArray.length()];
    selectedleadsrcemapper = new HashMap<String, String>();
        for(int i=0;i<jsonArray.length();i++){
        System.out.println("stages:"+jsonArray.getJSONObject(i));
        leadsrce_array[i] = jsonArray.getJSONObject(i).get("source").toString();
            selectedleadsrcemapper.put(leadsrce_array[i],jsonArray.getJSONObject(i).get("id").toString());

            setadapterleadsrce();
        }
        }


        }
