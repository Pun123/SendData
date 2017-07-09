package com.example.pun.senddata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
//import cz.msebera.android.httpclient.Header;

/**
 * Created by Pun on 7/6/2017 AD.
 */

public class newarticle extends AppCompatActivity {



    private static AsyncHttpClient _client;
    private static String _csrfToken;
    private Button btn_submitnew;
    private EditText editText_title, editText_description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newarticle);

        _client = new AsyncHttpClient();

        _client.get("http://deewaste.ddns.net/?q=services/session/token", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("SendData", "Fetch Passed");
                String csrf = new String(responseBody);

                Log.d("SendData", csrf);
                login(csrf);





            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("httptest", "Fetch Failed");

            }
        });



        btn_submitnew = (Button) findViewById(R.id.btn_submitnew);
        btn_submitnew.setOnClickListener(submitnewClick);

        editText_title = (EditText)findViewById(R.id.editText_title);
        editText_description = (EditText)findViewById(R.id.editText_description);


    }

    private View.OnClickListener submitnewClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            _client.addHeader("X-CSRF-Token", _csrfToken);
            RequestParams params = new RequestParams();

            String title = editText_title.getText().toString();
          //  String description = editText_description.getText().toString();


            params.put("title", title);
            params.put("type", "report");



            _client.post("http://deewaste.ddns.net/?q=my_endpoint/node.json", params, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("SendData", "create node success");
                }

            });




        }

    };
    public static void login(String csrf){
        _client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        _client.addHeader("Accept", "application/json");

        _client.addHeader("X-CSRF-Token", csrf);


        RequestParams params = new RequestParams();
        params.put("username", "admin");
        params.put("password", "pun1112355687");

        _client.post("http://deewaste.ddns.net/?q=my_endpoint/user/login.json", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("SendData", "Login Sucessful");
                _csrfToken = response.optString("token");

                Log.d("SendData", _csrfToken);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
                Log.d("httptest", "Login Failed");


            }


        });
    }


}

