package in.co.arcus.texvalley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class opportunityAdapter extends ArrayAdapter {

    /*public static int listviewid;*/

    private final Context _mcontext;
    JSONArray mListData;
    Button mcreatebutton;
    String selectedimageid;

    public opportunityAdapter(Context context, int resource, JSONArray objects) {
        super(context, resource);
        mListData = objects;
        _mcontext = context;
    }

    @Override
    public int getCount() {
        return mListData.length();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View currentRowView = convertView;
        if (currentRowView == null) {
            currentRowView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        JSONObject currentRow = null;
        TextView opportunityname = currentRowView.findViewById(R.id.opportunityname);
        TextView customername = currentRowView.findViewById(R.id.customername);
        TextView customerno = currentRowView.findViewById(R.id.customerno);
        ImageView imagefollowup = currentRowView.findViewById(R.id.Imageviewfollowup);
        try {
            currentRow = mListData.getJSONObject(position);
            opportunityname.setText(currentRow.get("name").toString());
            customername.setText(currentRow.get("cus").toString());
            customerno.setText(currentRow.get("mno").toString());

            final JSONObject finalCurrentRow = currentRow;

            imagefollowup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String listviewid =  finalCurrentRow.get("id").toString();
                        ((oppurtunity)_mcontext).oppurtunitiesfollowup(listviewid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }




        return currentRowView;
    }




}
