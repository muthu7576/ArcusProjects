package in.co.arcus.texvalley;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class recyclerview_main extends AppCompatActivity {

    private RecyclerView recyclerView;
    private recyclerview_adapter mrecycleradapter;
    private List<MyData> datalist = new ArrayList<MyData>();
    String[] listStagesArrayrecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_hideup);
        getBackendrecyclerview("id",Tabsactivity2.oppurtunity_id);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hideshow2);
        relativeLayout.setVisibility(View.INVISIBLE);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp(){
        Intent intent = new Intent(this,Tabsactivity2.class);
        this.finish();

        return true;
    }

    private void getBackendrecyclerview(String id, String oppurtunity_id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", oppurtunity_id);
            String url = "http://texvalley.arcus.co.in/texvalleyapp/history_recyclerview.php";
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
                    System.out.println("the status histryview is: " + result);
                    JSONObject jsonObject = new JSONObject(result);

                    gettingresponseobjectofhstry(jsonObject);

                }

            });
            oppurtunity_listview.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gettingresponseobjectofhstry(JSONObject jsonObject) throws JSONException {

        JSONArray jsonArrayview = jsonObject.getJSONArray("response");
        stringresponseids(jsonArrayview);
    }

    private void stringresponseids(JSONArray jsonArrayview) throws JSONException {

        if(jsonArrayview.length()!=0){


        for(int i = 0;i<jsonArrayview.length();i++)
        {
            int idofuser = jsonArrayview.getJSONObject(i).getInt("id");
            String dateofvistd = jsonArrayview.getJSONObject(i).getString("visitdate");
            String uservisited = jsonArrayview.getJSONObject(i).getString("username");
            String cntctofuser = jsonArrayview.getJSONObject(i).getString("contact");
            String stageofuser = jsonArrayview.getJSONObject(i).getString("stage");

            MyData data = new MyData(idofuser,dateofvistd,uservisited,cntctofuser,stageofuser);
            datalist.add(data);
        }
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mrecycleradapter = new recyclerview_adapter(this,datalist);
        recyclerView.setAdapter(mrecycleradapter);

        }else {
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.hideshow2);
            relativeLayout.setVisibility(View.VISIBLE);

        }

    }

}

