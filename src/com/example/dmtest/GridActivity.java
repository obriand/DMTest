package com.example.dmtest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridActivity extends Activity {

	public class MyAdapter extends BaseAdapter {

		final int NumberOfItem = 30;
		private Bitmap[] bitmap = new Bitmap[NumberOfItem];

		private Context context;
		private LayoutInflater layoutInflater;
		
		private ArrayList<String> videoURLs = new ArrayList<String>();

		public int getCount() {
			// TODO Auto-generated method stub
			return bitmap.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return bitmap[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			View grid;
			if (convertView == null) {
				grid = new View(context);
				grid = layoutInflater.inflate(R.layout.grid_item, null);
			} else {
				grid = (View) convertView;
			}

			ImageView imageView = (ImageView) grid.findViewById(R.id.image);
			imageView.setImageBitmap(bitmap[position]);
			TextView textView = (TextView) grid.findViewById(R.id.text);
			textView.setText(String.valueOf(position));

			return grid;
		}

	}

	GridView gridView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		gridView = (GridView) findViewById(R.id.grid);

		ArrayList<HashMap<String, Object>> photos = new ArrayList();
		HashMap photo = new HashMap();
		photo.put("file", "160x120-QHY.jpg");
		for (int i = 0; i< 20 ; i++) {
			photos.add(i, photo);
		}
		gridView.setAdapter(new ImageAdapter(this, "./images", photos));
	}

}
