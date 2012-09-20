package com.example.dmtest;

import com.example.dmtest.request.RequestManager;
import com.example.dmtest.request.RequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class DMTest extends Activity implements RequestListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmtest);
        
		// Make a http request
        Toast.makeText(getApplicationContext(), "Make the request", Toast.LENGTH_LONG).show();
        String jsonBody = "";
        new RequestManager(getApplicationContext()).doPost("https://api.dailymotion.com/", jsonBody, this);  
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dmtest, menu);
        return true;
    }
    
	public void onResponse(int code, String response) {
		Toast.makeText(getApplicationContext(), "response code: "+code, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(), "response body: "+response, Toast.LENGTH_LONG).show();
	}
    
}
