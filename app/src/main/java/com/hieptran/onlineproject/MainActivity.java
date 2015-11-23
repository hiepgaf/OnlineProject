package com.hieptran.onlineproject;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("HiepTH", "HiepT");

    }

    private static String ReadJSON(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJson(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = ReadJSON(rd);
            Log.d("HiepTHb", "jsontext return : " + jsonText);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyAsync().execute("http://graph.facebook.com/duythanhcse");
    }

    public class MyAsync extends AsyncTask<String, JSONObject, Void> {
        StringBuilder builder = new StringBuilder();

        @Override
        protected Void doInBackground(String... params){
            Log.d("HiepTH","doInBackground");
            String url = params[0];
            JSONObject jsonObj;
            try {
                jsonObj = readJson(url);
                publishProgress(jsonObj);
            } catch (Exception e) {Log.d("HiepTH",e.toString());}
            return null;
        }

        @Override
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);
            Log.d("HiepTH","onProgressUpdate");
            JSONObject myObj = values[0];
            try {
                builder.append("id: " + myObj.getString("id") + "\n");
                builder.append("first_name: " + myObj.getString("first_name") + "\n");
                builder.append("last_name: " + myObj.getString("last_name") + "\n");
                builder.append("name: " + myObj.getString("name") + "\n");
                builder.append("link: " + myObj.getString("link") + "\n");
            } catch (Exception e) {Log.d("HiepTH",e.toString());}
            Log.d("HiepTH",builder.toString());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("HiepTH", builder.toString());
        }
    }
}
