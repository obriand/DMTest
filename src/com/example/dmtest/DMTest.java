package com.example.dmtest;

import java.util.HashMap;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

import com.loopj.android.http.*;

public class DMTest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmtest);
        
		// Make a http request
        AsyncHttpClient client = new AsyncHttpClient();
        Toast.makeText(getApplicationContext(), "Make the request", Toast.LENGTH_LONG).show();
        client.get("https://api.dailymotion.com/channel/kids/videos?filters=official", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                Toast.makeText(getApplicationContext(), "Response : "+response, Toast.LENGTH_LONG).show();
            }
        });
    }
    
}
