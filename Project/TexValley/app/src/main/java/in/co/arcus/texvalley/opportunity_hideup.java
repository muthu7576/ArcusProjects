package in.co.arcus.texvalley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class opportunity_hideup extends Fragment {

    View view;
    TextView opptyhideup_name,hideupexpctdclsedate,listClassificationshideups,editcus_namehideups,listStageshideups,editcus_valuehideups,
            listCategoryhideups,followup_assignhideups,editcus_cmpnynamehideups,categoryStatuslistshideups;

    public opportunity_hideup() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.opportunity_hideup, container, false);
        getBackEndData("id", Tabsactivity2.oppurtunity_id);

        opptyhideup_name=(TextView)view.findViewById(R.id.editcus_opptynamehideup);
        hideupexpctdclsedate=(TextView)view.findViewById(R.id.date_time_sethideup);
        listClassificationshideups=(TextView)view.findViewById(R.id.listClassificationshide);
                editcus_namehideups=(TextView)view.findViewById(R.id.editcus_namehideup);
        listStageshideups=(TextView)view.findViewById(R.id.listStageshideup);
                editcus_valuehideups=(TextView)view.findViewById(R.id.editcus_valuehide);
        listCategoryhideups=(TextView)view.findViewById(R.id.listCategoryhide);
                followup_assignhideups=(TextView)view.findViewById(R.id.followup_assignhide);
        editcus_cmpnynamehideups=(TextView)view.findViewById(R.id.editcus_cmpnynamehide);
                categoryStatuslistshideups=(TextView)view.findViewById(R.id.categoryStatuslistshide);
        return view;

    }

    private void getBackEndData(String id, String clickListViewId) {


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", clickListViewId);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/oppurtunity_followuphide.php";
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("url", url);
            params.put("requestmethod", "POST");
            params.put("inputparams", jsonObject);

            asynctask oppurtunity_listview = new asynctask(params);
            oppurtunity_listview.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String result) throws JSONException {
                    System.out.println("the status output is: " + result);
                    JSONObject jsonObject = new JSONObject(result);

                    gettingresponseobject(jsonObject);

                }

            });
            oppurtunity_listview.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gettingresponseobject(JSONObject jsonObject) throws JSONException {
    JSONObject jsonObjectresponse = jsonObject.getJSONObject("response");
        stringresponseids(jsonObjectresponse);

    }

    private void stringresponseids(JSONObject jsonObjectresponse) throws JSONException {

        String opptyname = jsonObjectresponse.getString("name");
        if(opptyname == "null"){
            opptyhideup_name.setText("-");

        }else{
            opptyhideup_name.setText(opptyname);
        }

        String opptyexpctdclsedate = jsonObjectresponse.getString("expCloseDate");
        if(opptyexpctdclsedate != "null"){
            hideupexpctdclsedate.setText(opptyexpctdclsedate);
        }else{
            hideupexpctdclsedate.setText("-");
        }

        String clssfyhideups = jsonObjectresponse.getString("classification");
        if(clssfyhideups != "null"){
            listClassificationshideups.setText(clssfyhideups);
        }else{
            listClassificationshideups.setText("-");
        }

        String cutmrnmehideups = jsonObjectresponse.getString("cus");
        if(cutmrnmehideups != "null"){
            editcus_namehideups.setText(cutmrnmehideups);
        }else{
            editcus_namehideups.setText("-");
        }

        String slsstgehideups = jsonObjectresponse.getString("stage");
        if(slsstgehideups != "null"){
            listStageshideups.setText(slsstgehideups);
        }else{
            listStageshideups.setText("-");
        }

        String vluehideups = jsonObjectresponse.getString("amt");
        if(vluehideups != "null"){
            editcus_valuehideups.setText(vluehideups);
        }else{
            editcus_valuehideups.setText("-");
        }

        String csctgryhideups = jsonObjectresponse.getString("cuscat");
        if(csctgryhideups != "null"){
            listCategoryhideups.setText(csctgryhideups);
        }else{
            listCategoryhideups.setText("-");
        }

        String usrhideups = jsonObjectresponse.getString("u");
        if(usrhideups != "null"){
            followup_assignhideups.setText(usrhideups);
        }else{
            followup_assignhideups.setText("-");
        }

        String cstrnmehideups = jsonObjectresponse.getString("cn");
        if(cstrnmehideups == "null"){
            editcus_cmpnynamehideups.setText("-");
        }else{
            editcus_cmpnynamehideups.setText(cstrnmehideups);
        }

        String cstmrstshideups = jsonObjectresponse.getString("cs");
        if(cstmrstshideups == "null"){
            categoryStatuslistshideups.setText("-");
        }else {
            categoryStatuslistshideups.setText(cstmrstshideups);
        }




    }




}


           /*
           String listclassificationArrayhideup;
    Spinner listclassificationhideup;
           listclassificationArrayhideup = jsonObjectresponse.getString("classification");

                    setadapter();
private void setadapter() {

        listclassificationhideup = (Spinner)view.findViewById(R.id.listClassificationshide);
        ArrayAdapter<String> adapters = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,listclassificationArrayhideup);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listclassificationhideup.setAdapter(adapters);
        }*/