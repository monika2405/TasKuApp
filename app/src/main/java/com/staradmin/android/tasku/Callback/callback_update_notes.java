package com.staradmin.android.tasku.Callback;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.staradmin.android.tasku.Network.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class callback_update_notes extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {
    Context context;
    int success;
    String message;
    private JSONParser jsonParser = new JSONParser();
    JSONArray jsonArray = null;
    ArrayList<HashMap<String, String>> arrayListRet;

    public callback_update_notes(Context m_context) {
        this.context= m_context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

        // Building Parameters
        List<NameValuePair> paramsed = new ArrayList<NameValuePair>();


        Log.d("CEKIDBOOK",params[0]);



        paramsed.add(new BasicNameValuePair("notes_title", params[0])); //id
        paramsed.add(new BasicNameValuePair("isi_notes", params[1]));
        paramsed.add(new BasicNameValuePair("time_upload", params[2]));
        paramsed.add(new BasicNameValuePair("id_notes", params[3]));


        JSONObject json = jsonParser.makeHttpRequest("http://dev.projectlab.co.id/mit/1417002/mst_update_notes.php",
                "POST", paramsed);

        System.out.println("json 2 = "+json.toString());
        Log.d("CEKIDBOOK_MSG",json.toString());

        arrayListRet = new ArrayList<>();

        try {
            success = json.getInt("success");

            message = json.getString("message");


            if (success == 1) {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("success", String.valueOf(success));
                map.put("message", message);


                arrayListRet.add(map);

            } else {
                //Unsuccessfully picking book. Another driver has taken the order.
                HashMap<String, String> map = new HashMap<String, String>();

            }
        } catch (JSONException e) {
            return null;
        }

        System.out.println("arrayListRet = "+arrayListRet);
        return arrayListRet;
    }


    /**
     * After completing sys_navdrawer_background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(ArrayList<HashMap<String, String>> file_url) {

    }
}
