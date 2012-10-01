package com.example.dmtest;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

import com.loopj.android.http.*;

public class DMTest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmtest);
    }
    
    // Get all videos for homescreen
    public void getVideosKids(View view) {  
		// Make a http request
        AsyncHttpClient client = new AsyncHttpClient();
        Toast.makeText(getApplicationContext(), "Make the request", Toast.LENGTH_LONG).show();
        client.get("https://api.dailymotion.com/channel/kids/videos?filters=official", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
            	JSONObject jsonResponse = null;
            	try {
					jsonResponse = new JSONObject(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                //Toast.makeText(getApplicationContext(), "Response : "+response, Toast.LENGTH_LONG).show();
                JSONArray jsonAResponse = null;
                try {
                	jsonAResponse = jsonResponse.getJSONArray("list");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                for(int i=0; i<jsonAResponse.length(); i++) {
                	try {
						Toast.makeText(getApplicationContext(), "Json first element : "+jsonAResponse.getJSONObject(i), Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }            
            }
        });
    }
    
    public void launchGridView(View view) {      	
    	Intent intent = new Intent(DMTest.this, GridActivity.class);
    	startActivity(intent);
    }
    
    public void launchVideoDetailsView(View view) {      	
    	Intent intent = new Intent(DMTest.this, GridActivity.class);
    	startActivity(intent);
    }
    
}

