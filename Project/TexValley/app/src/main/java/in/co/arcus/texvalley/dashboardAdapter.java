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

import java.util.BitSet;


public class dashboardAdapter extends ArrayAdapter {
    public static dashboard finalCurrentRow;

    /*public static int listviewid;*/

    private final Context _mcontexts;
    JSONArray mListDatas;
    Button mcreatebutton;
    String selectedimageid;

    public dashboardAdapter(Context context, int resource, JSONArray objects) {
        super(context, resource);
        mListDatas = objects;
        _mcontexts = context;
    }

    @Override
    public int getCount() {
        return mListDatas.length();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View currentRowViewfordash = convertView;
        if (currentRowViewfordash == null) {
            currentRowViewfordash = LayoutInflater.from(getContext()).inflate(R.layout.listviewfordashboard, parent, false);
        }

        JSONObject currentRowfrdash = null;
        TextView opportunitynamefrdash = currentRowViewfordash.findViewById(R.id.opportunitynamefrdash);
        TextView customernamefrdash = currentRowViewfordash.findViewById(R.id.customernamefrdash);
        TextView customernofrdash = currentRowViewfordash.findViewById(R.id.customernofrdash);
        ImageView imagefollowupfrdash = currentRowViewfordash.findViewById(R.id.Imageviewfollowupfrdash);
        try {
            currentRowfrdash = mListDatas.getJSONObject(position);
            opportunitynamefrdash.setText(currentRowfrdash.get("oppname").toString());
            customernamefrdash.setText(currentRowfrdash.get("cusn").toString());
            customernofrdash.setText(currentRowfrdash.get("mno").toString());

            final JSONObject finalCurrentRow = currentRowfrdash;

            imagefollowupfrdash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String listviewid =  finalCurrentRow.get("id").toString();
                        ((dashboard)_mcontexts).oppurtunitiesfollowupfrdash(listviewid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }




        return currentRowViewfordash;
    }




}
