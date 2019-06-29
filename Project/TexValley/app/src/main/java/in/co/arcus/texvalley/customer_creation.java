package in.co.arcus.texvalley;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class customer_creation extends Fragment {

    View view;

    Spinner listCategory,categoryStatus;
    String[] listArray,statusLists;
    FloatingActionButton nextup1;
     public static EditText txtName;
  public static EditText txtPhone;
    public static EditText txtemail;
    EditText website;
    EditText companyname;
    EditText brandname;
    EditText address;
    public static EditText area;
    EditText products;
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
       /* if( txtPhone.getText().toString().isEmpty()){

            txtPhone.setError( "Contact Number is required!" );

        }*/
        txtPhone.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(txtPhone.getText()!=null && txtPhone.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Contact Number is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        txtName=(EditText)view.findViewById(R.id.editcus_name);
       /* if( txtName.getText().toString().isEmpty()){

            txtName.setError( "Name is required!" );

        }*/
        txtName.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(txtName.getText()!=null && txtName.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Name is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        txtemail=(EditText)view.findViewById(R.id.editcus_email);
       /* if( txtemail.getText().toString().isEmpty()){

            txtemail.setError( "Email is required!" );

        }*/
        txtemail.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(txtemail.getText()!=null && txtemail.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Email is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        website=(EditText)view.findViewById(R.id.editcus_website);
        companyname=(EditText)view.findViewById(R.id.editcus_cmpnyname);
        brandname=(EditText)view.findViewById(R.id.editcus_brndname);
        address=(EditText)view.findViewById(R.id.editcus_address);
        area=(EditText)view.findViewById(R.id.editcus_area);

       /* if( area.getText().toString().isEmpty()){

            area.setError( "Area is required!" );

        }*/
        area.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_NEXT || keyEvent != null &&
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                        keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if(area.getText()!=null && area.getText().length()>0){

                    }
                    else {
                        Toast.makeText(getContext(),"Email is required",Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        products=(EditText)view.findViewById(R.id.editcus_prdcts);

        getbackendcategories();
        getBackendCategoryStatus();
        nextup1.setOnClickListener(new View.OnClickListener() {
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


                }catch (JSONException e){
                    e.printStackTrace();
                }
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


/*
 if(txtPhone.getText().toString()!=null || (txtPhone.getText().toString()).length() != 0)
         {
         try {
         Tabsactivity.opputunityPayload.put("phone",txtPhone.getText().toString());
         } catch (JSONException e) {
         e.printStackTrace();
         }

         } else
         { Toast.makeText(getContext(),"Contact number is required",Toast.LENGTH_LONG).show();
         return;
         }


         if(txtPhone.getText().toString()!=null || (txtPhone.getText().toString()).length() != 0)
         {
         try {
         Tabsactivity.opputunityPayload.put("phone",txtPhone.getText().toString());
         } catch (JSONException e) {
         e.printStackTrace();
         }

         } else
         { Toast.makeText(getContext(),"Contact number is required",Toast.LENGTH_LONG).show();
         return;
         }


         if(txtName.getText().toString()!=null || (txtName.getText().toString()).length() != 0)
         {
         try {
         Tabsactivity.opputunityPayload.put("name",txtName.getText().toString());
         } catch (JSONException e) {
         e.printStackTrace();
         }

         } else
         { Toast.makeText(getContext(),"Name is required",Toast.LENGTH_LONG).show();
         return;
         }


         if(txtemail.getText().toString()!=null || (txtemail.getText().toString()).length() != 0)
         {
         try {
         Tabsactivity.opputunityPayload.put("email",txtemail.getText().toString());
         } catch (JSONException e) {
         e.printStackTrace();
         }

         } else
         { Toast.makeText(getContext(),"Email is required",Toast.LENGTH_LONG).show();
         return;
         }


         if(area.getText().toString()!=null || (area.getText().toString()).length() != 0)
         {
         try {
         Tabsactivity.opputunityPayload.put("area",area.getText().toString());
         } catch (JSONException e) {
         e.printStackTrace();
         }

         } else
         { Toast.makeText(getContext(),"Area is required",Toast.LENGTH_LONG).show();
         return;
         }*/
