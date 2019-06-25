package in.co.arcus.texvalley;

import android.content.Intent;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class customer_creation extends Fragment {

    View view;

    Spinner listCategory,categoryStatus;
    String[] listArray,statusLists;
    FloatingActionButton nextup1;
    EditText txtName,txtPhone,txtemail,website,companyname,brandname,address,area,products;
    private HashMap<String,String> categoryMapper;
    private String selectedCategory;
    private String selectedcategoryStatus;
    private HashMap<String,String> categorystatuses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.customer_creation,container,false);
        nextup1 = (FloatingActionButton) view.findViewById(R.id.next_up);
        txtPhone=(EditText)view.findViewById(R.id.editcus_no);
        txtName=(EditText)view.findViewById(R.id.editcus_name);
        txtemail=(EditText)view.findViewById(R.id.editcus_email);
        website=(EditText)view.findViewById(R.id.editcus_website);
        companyname=(EditText)view.findViewById(R.id.editcus_cmpnyname);
        brandname=(EditText)view.findViewById(R.id.editcus_brndname);
        address=(EditText)view.findViewById(R.id.editcus_address);
        area=(EditText)view.findViewById(R.id.editcus_area);
        products=(EditText)view.findViewById(R.id.editcus_prdcts);

        getbackendcategories();
        getBackendCategoryStatus();
        nextup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataPayload();
                TabLayout tabLayout = (TabLayout) ((Tabsactivity)getActivity()).findViewById(R.id.tabs);
                tabLayout.getTabAt(1).select();
            }
        });
        return view;


    }

    private void getDataPayload() {
        try {
            Tabsactivity.opputunityPayload.put("contactnumber",txtPhone.getText().toString());
            Tabsactivity.opputunityPayload.put("name",txtName.getText().toString());
            Tabsactivity.opputunityPayload.put("email",txtemail.getText().toString());
            Tabsactivity.opputunityPayload.put("website",website.getText().toString());
            Tabsactivity.opputunityPayload.put("companyname",companyname.getText().toString());
            Tabsactivity.opputunityPayload.put("brandname",brandname.getText().toString());
            Tabsactivity.opputunityPayload.put("address",address.getText().toString());
            Tabsactivity.opputunityPayload.put("area",area.getText().toString());
            Tabsactivity.opputunityPayload.put("customercategory",categoryMapper.get(selectedCategory));
            Tabsactivity.opputunityPayload.put("customerstatus",categorystatuses.get(selectedcategoryStatus));
            Tabsactivity.opputunityPayload.put("products",products.getText().toString());
            System.out.println("The output is " + txtPhone.getText().toString());
            System.out.println("The output is " +txtName.getText().toString());
            System.out.println("The output is " + txtemail.getText().toString());
            System.out.println("The output is " + website.getText().toString());
            System.out.println("The output is " + companyname.getText().toString());
            System.out.println("The output is " + brandname.getText().toString());
            System.out.println("The output is " + address.getText().toString());
            System.out.println("The output is " + area.getText().toString());

            System.out.println("The output is " + categoryMapper.get(selectedCategory));
            System.out.println("The output is " + categorystatuses.get(selectedcategoryStatus));
            System.out.println("The output is " + products.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setExecutiveSpinner() {
        listCategory = (Spinner) view.findViewById(R.id.listCategory);
        ArrayAdapter<String> adapterSpinnerExec = new ArrayAdapter<String>(view.getContext(), R.layout.spinner_item, listArray);
        adapterSpinnerExec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listCategory.setAdapter(adapterSpinnerExec);
        listCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = adapterView.getItemAtPosition(i).toString();
                System.out.println("Selected Cstegory :"+selectedCategory + "   "+categoryMapper.get(selectedCategory));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });



    }

    private void getbackendcategories() {
        String url = "http://texvalley.arcus.co.in/texvalleyapp/spinner.php";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("requestmethod","GET");
        try {
            asynctask categoryTask = new asynctask(params);
            categoryTask.setListener(new asynctask.MyListener() {
                @Override
                public void onpreExecutemethod() {

                }

                @Override
                public void onPostExecutemetod(String result) throws JSONException {
                    System.out.println("The output is " + result);

                    JSONObject jsonObject = new JSONObject(result);
                    processListCategory(jsonObject);

                }
            });
            categoryTask.execute();


        } catch (Exception e) {

        }
    }

    private void processListCategory(JSONObject response) throws JSONException {
        JSONArray categories = response.getJSONArray("response");
        System.out.println("categories length:"+categories.length());
        categoryMapper = new HashMap<String,String>();
        listArray = new String[categories.length()];
        for(int i=0; i<categories.length(); i++){
            System.out.println("categories names:"+categories.getJSONObject(i));
            listArray[i] = categories.getJSONObject(i).get("categoryname").toString();
            categoryMapper.put(listArray[i],categories.getJSONObject(i).get("id").toString());
        }
        setExecutiveSpinner();
    }


    private void setSpinner(){
        categoryStatus = (Spinner)view.findViewById(R.id.categoryStatuslists);
        ArrayAdapter<String> adapterspinner = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item,statusLists);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryStatus.setAdapter(adapterspinner);

        categoryStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedcategoryStatus = adapterView.getItemAtPosition(i).toString();
                System.out.println("Selected Cstegory :"+selectedcategoryStatus + "   "+categorystatuses.get(selectedcategoryStatus));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    private void getBackendCategoryStatus(){
        String url = "http://texvalley.arcus.co.in/texvalleyapp/customerStatus.php";
        HashMap<String,Object> params =new  HashMap<String,Object>();
        params.put("url",url);
        params.put("requestmethod","GET");
        asynctask statusCategory = new asynctask(params);
        statusCategory.setListener(new asynctask.MyListener() {
            @Override
            public void onpreExecutemethod() {

            }

            @Override
            public void onPostExecutemetod(String result) throws JSONException {


                System.out.println("the status output is: "+result);

                JSONObject jsonObject = new JSONObject(result);
                statusArraylists(jsonObject);
            }
        });
        statusCategory.execute();

    }

    private void statusArraylists(JSONObject response) throws JSONException {
        JSONArray categoryStatus = response.getJSONArray("response");
        categorystatuses = new HashMap<String,String>();
        statusLists = new String[categoryStatus.length()];
        for(int j=0;j<categoryStatus.length();j++){
            statusLists[j] =categoryStatus.getJSONObject(j).get("customerStatus").toString();
            categorystatuses.put(statusLists[j],categoryStatus.getJSONObject(j).get("id").toString());
        }
        setSpinner();
    }

}


