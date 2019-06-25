package in.co.arcus.texvalley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class login_Asynctask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context context;
    login_Asynctask(Context ctx){
        context = ctx;
    }
    public interface MyAsyncTaskListener{
        void onPreExecuteConcluded();
        void onPostExecuteConcluded(String result);

    }
    private MyAsyncTaskListener mlistener;

    public void setListener(MyAsyncTaskListener listener){
        mlistener = listener;
    }


    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://texvalley.arcus.co.in/texvalleyapp/Login.php";
        if(type.equals("login")){
            try {
                String username = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                OutputStream outputStreamWriter = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStreamWriter,"UTF-8"));
                String post_data = URLEncoder.encode("un","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("pw","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
          bufferedWriter.flush();
          bufferedWriter.close();
          outputStreamWriter.close();
                String result="";
                String inputline;
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while ((inputline = bufferedReader.readLine())!= null){
                    result += inputline;
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
/*alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setTitle("Login Status");
        alertDialog.setMessage("loading....");
        alertDialog.show();*/
        if(mlistener!= null){
            mlistener.onPreExecuteConcluded();
        }
    }

    @Override
    protected void onPostExecute(String result) {

mlistener.onPostExecuteConcluded(result);
       // alertDialog.dismiss();
    }

    @Override    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }
}
