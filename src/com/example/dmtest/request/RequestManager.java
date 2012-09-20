package com.example.dmtest.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class RequestManager {

	private static final String TAG = "com.example.dailymotiontest.request.RequestManager";
	
	private Context mContext;

	public RequestManager(Context context) {
		mContext = context;
	}

	// Not tested (doesn't work)
	public void doGet(String url, RequestListener listener) {

		HttpGet getRequest = new HttpGet(url);

		// Add Headers
		//getRequest.addHeader("country", "FR");

		new RequestTask(getRequest, listener).execute();
	}

	public void doPost(String url, String body, RequestListener listener) {

		HttpPost postRequest = new HttpPost(url);

		// Add Headers (at least X-Tags)
		/*postRequest.addHeader("X-Tags","service=api;uakey=DefaultW;version=1.0");*/

		new RequestTask(postRequest, listener).execute();
	}

	private class RequestTask extends AsyncTask<Void, Void, HttpResponse> {

		private HttpUriRequest request;
		private RequestListener listener;

		public RequestTask(HttpUriRequest request, RequestListener listener) {
			this.request = request;
			this.listener = listener;
		}

		@Override
		protected HttpResponse doInBackground(Void... arg0) {
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				return httpClient.execute(request);
			} catch (ClientProtocolException e) {
				request.abort();
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				request.abort();
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(HttpResponse result) {
			// Send the request
			InputStream source = null;
			int statusCode = -1;
			HttpEntity getResponseEntity = null;
			if (result != null) {
				statusCode = result.getStatusLine().getStatusCode();
				getResponseEntity = result.getEntity();
			}

			StringBuilder total = new StringBuilder();
			try {
				source = getResponseEntity.getContent();

				// Return the response
				String line;
				BufferedReader r = new BufferedReader(new InputStreamReader(source));
				/*while ((line = r.readLine()) != null) {
					total.append(line);
				}*/
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (NullPointerException e1) {
				e1.printStackTrace();
			}

			listener.onResponse(statusCode, total.toString());
		}
	}
}
