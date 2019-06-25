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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class oppurtunity_creation extends Fragment {



    View view;
        Spinner stageslists,classificationlists,enquirylist,leadsrce_status;
        String[] listStagesArray,listEnquiry,leadsrce_array;
        String[] classificationListarray = {"Cold","Hot","Warm"};
        View dialogView;
        AlertDialog alertDialog;
        TextView mdatepickertexts;
       private String selctedstagelists,selectedenquirylists,selctedleadsrce,selectedclassificationlistst;
        private  HashMap<String,String> selectedstagesmapper,selctedenquirymapper,selectedleadsrcemapper;

    FloatingActionButton nextup;
    EditText opptyname,oppty_value,descriptionleadsrce;
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
        oppty_value = (EditText)view.findViewById(R.id.editcus_value);
        descriptionleadsrce=(EditText)view.findViewById(R.id.editcus_descrptn);
        classificationLists();
        getBackendsrce();
        getBackendsrceenquiry();
        getBackendleadsrce();
        nextup.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        getdatafrmoppty();
        TabLayout tabLayout = (TabLayout) ((Tabsactivity)getActivity()).findViewById(R.id.tabs);
        tabLayout.getTabAt(2).select();
        }
        });
        mdatepickertexts = (TextView)view.findViewById(R.id.date_time_set);
        dialogView = View.inflate(view.getContext(), R.layout.datepicker, null);
        alertDialog = new AlertDialog.Builder(view.getContext()).create();
        dialogView.findViewById(R.id.date_time_picker).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
        // TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

        String selectedDate = datePicker.getYear() +"-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth();
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
        Tabsactivity.opputunityPayload.put("values",oppty_value.getText().toString());
        Tabsactivity.opputunityPayload.put("enquirylist",selctedenquirymapper.get(selectedenquirylists));
        Tabsactivity.opputunityPayload.put("leadsrcestatus",selectedleadsrcemapper.get(selctedleadsrce));
        Tabsactivity.opputunityPayload.put("descriptionleadsrce",descriptionleadsrce.getText().toString());

        System.out.println("The output oppurtunityname is " + opptyname.getText().toString());
        System.out.println("The output expecteddate is " + expctd_date.getText().toString());
        System.out.println("The output categorystatuslists is " + selectedclassificationlistst);
        System.out.println("The output opptySalesstage is " + selectedstagesmapper.get(selctedstagelists));
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

private void setadapter(){
        stageslists = (Spinner)view.findViewById(R.id.listStages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,listStagesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stageslists.setAdapter(adapter);
    stageslists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selctedstagelists = adapterView.getItemAtPosition(i).toString();
            System.out.println("Selected stageslists :"+selctedstagelists + "   "+selectedstagesmapper.get(selctedstagelists));
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