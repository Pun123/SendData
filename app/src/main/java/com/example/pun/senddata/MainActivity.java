package com.example.pun.senddata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private static AsyncHttpClient _client;
    private static String _csrfToken;

    private Button btn_new;
    private Button btn_edit;
    private Button btn_view;
    private Button btn_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

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

        btn_new = (Button)findViewById(R.id.btn_new);
        btn_new.setOnClickListener(newClick);

        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(editClick);

        btn_view = (Button)findViewById(R.id.btn_view);
        btn_view.setOnClickListener(viewClick);

        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(deleteClick);





    }
    private View.OnClickListener newClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent1 = new Intent(MainActivity.this, newarticle.class);
            //pass parameter to next activity

            startActivity(intent1);



        }
    };
    private View.OnClickListener editClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent2 = new Intent(MainActivity.this, editarticle.class);
            startActivity(intent2);




        }
    };
    private View.OnClickListener viewClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent3 = new Intent(MainActivity.this, viewarticle.class);
            startActivity(intent3);



        }
    };
    private View.OnClickListener deleteClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent4 = new Intent(MainActivity.this, deletearticle.class);
            startActivity(intent4);



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
