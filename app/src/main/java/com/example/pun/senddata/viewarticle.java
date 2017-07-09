package com.example.pun.senddata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Pun on 7/8/2017 AD.
 */
public class viewarticle extends AppCompatActivity {



    private static AsyncHttpClient _client;
    private static String _csrfToken;
    private Button btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewarticle);
        btn_view = (Button)findViewById(R.id.btn_view);
        btn_view.setOnClickListener(viewClick);

    }

    private View.OnClickListener viewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // _client = new AsyncHttpClient();
            _client.addHeader("X-CSRF-Token", _csrfToken);

            _client.get("http://deewaste.ddns.net/?q=my_endpoint/node/27.json", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    Log.d("SendData", "Success");
                    String vid = response.optString("vid");
                    Log.d("SendData", "Node ID is " + String.valueOf(vid));
                    String title = response.optString("title");
                    Log.d("SendData", "Title is " + String.valueOf(title));
                    String type = response.optString("type");
                    Log.d("SendData", "Type is " + String.valueOf(type));



                }
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline
                    Log.d("httptest", "Not found");



                }


            });
        }
    };








}
