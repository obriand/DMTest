package com.example.dmtest;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	 
	private Context context;
	
	private static String TAG = "ImageAdapter";
 
	// stores all data for album..
	private ArrayList<HashMap<String, Object>> photos;
 
	// my folder I'm currently loading from..
	private String folder;
 
	// views stores all the loaded ImageViews linked to their
	// position in the GridView; hence <Integer, ImageView> in
	// the HashMap..
	private HashMap<Integer, ImageView> views;
 
	public ImageAdapter (Context c, String f, ArrayList<HashMap<String, Object>> p) {
		context = c;
		// I need to hold a reference to my current album folder..
		folder = f;
		// photos stores all of my data for the album, this includes
		// the filename, some other stuff I'm not using..
		photos = p;
 
		// 'views' is a HashMap that holds all
 		// of the loaded ImageViews linked to their position
		// inside the GridView. it's used for checking to see if
		// the particular ImageView has already been loaded
		// (inside the getView method) and if not, creates the
		// new ImageView and stores it in the HashMap along with
		// its position.. 
		views = new HashMap<Integer, ImageView>();
	}
 
	public int getCount() {
		return photos.size();
	}
 
	public Object getItem(int position) {
		return position;
	}
 
	public long getItemId(int position) {
		return position;
	}
 
	public View getView(int position, View view, ViewGroup parent) {
 
		ImageView v;
 
		// get the ImageView for this position in the GridView..
		v = views.get(position);
 
		// this ImageView might not be created yet..
		if (v == null) {
			Log.d(TAG, "This view is not created. create it.");
 
			// create a new ImageView..
			v = new ImageView(context);
 
			v.setLayoutParams(new GridView.LayoutParams(110, 110));
			v.setPadding(10, 10, 10, 10);
 
			// I'm setting a default image here so that you don't
			// see black nothingness.. (just using an icon that
			// comes with the Android SDK)
			v.setImageResource(android.R.drawable.ic_menu_gallery);
 
			// get the filename that this ImageView will hold..
			String file = photos.get(position).get("file").toString();
 
			// pass this Bundle along to the LoadImage class,
			// which is a subclass of Android's utility class
			// AsyncTask. Whatever happens in this class is
			// on its own thread.. the Bundle passes
			// the file to load and the position the photo
			// should be placed in the GridView..
			Bundle b = new Bundle ();
			b.putString("file", file);
			b.putInt("pos", position);
 
			// just a test to make sure that the position and
			// file name are matching before and after the
			// image has loaded..
			Log.d(TAG, "*before: " + b.getInt("pos") + " | " + b.getString("file"));
 
			// this executes a new thread, passing along the file
			// to load and the position via the Bundle..
			new LoadImage().execute(b);
 
			// puts this new ImageView and position in the HashMap.
			views.put(position, v);
		}
 
		// return the view to the GridView..
		// at this point, the ImageView is only displaying the
		// default icon..
		return v;
 
	}
 
	// this is the class that handles the loading of images from the cloud
	// inside another thread, separate from the main UI thread..
	private class LoadImage extends AsyncTask<Bundle, Void, Bundle> {
 
		private String TAG = "LoadImage";
		
		@Override
		protected Bundle doInBackground(Bundle... b) {
 
			// get the file that was passed from the bundle..
			String file = b[0].getString("file");
 
			// fetchPhoto is a helper method to get the photo..
			// returns a Bitmap which we'll place inside the
			// appropriate ImageView component..
			Bitmap bm = fetchPhoto(file);
 
			// now that we have the bitmap (bm), we'll
			// create another bundle to pass to 'onPostExecute'.
			// this is the method that is called at the end of 
			// our task. like a callback function..
			// this time, we're not passing the filename to this
			// method, but the actual bitmap, not forgetting to
			// pass the same position along..
			Bundle bundle = new Bundle();
			bundle.putParcelable("bm", bm);
			bundle.putInt("pos", b[0].getInt("pos"));
			bundle.putString("file", file); // this is only used for testing..
 
			return bundle;
		}
 
		@Override
		protected void onPostExecute(Bundle result) {
			super.onPostExecute(result);
 
			// just a test to make sure that the position and
			// file name are matching before and after the
			// image has loaded..
			Log.d(TAG, "*after: " + result.getInt("pos") + " | " + result.getString("file"));
 
			// here's where the photo gets put into the
			// appropriate ImageView. we're retrieving the
			// ImageView from the HashMap according to
			// the position..
			ImageView view = views.get(result.getInt("pos"));
 
			// then we set the bitmap into that view. and that's it.
			view.setImageBitmap((Bitmap) result.getParcelable("bm"));
		}
 
	}
 
	// this is a helper method to retrieve the photo from the cloud..
	private Bitmap fetchPhoto (String file) {
		Bitmap bm = null;
 
		String path = "http://s1.dmcdn.net/sjsA/" + file;
 
		Log.d(TAG, "path: " + path);
 
		try {
 
			final HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is established.
			HttpConnectionParams.setConnectionTimeout(httpParameters, 7000);
 
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setSoTimeout(httpParameters, 10000);
 
			final HttpClient client = new DefaultHttpClient(httpParameters);
			final HttpResponse response = client.execute(new HttpGet(path));
			final HttpEntity entity = response.getEntity();
			final InputStream imageContentInputStream = entity.getContent();
 
			// wrapping the imageContentInputStream with FlushedInputStream.
			bm = BitmapFactory.decodeStream(new FlushedInputStream (imageContentInputStream));
 
		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
 
		return bm;
	}
	
	 static class FlushedInputStream extends FilterInputStream {
	        public FlushedInputStream(InputStream inputStream) {
	            super(inputStream);
	        }

	        @Override
	        public long skip(long n) throws IOException {
	            long totalBytesSkipped = 0L;
	            while (totalBytesSkipped < n) {
	                long bytesSkipped = in.skip(n - totalBytesSkipped);
	                if (bytesSkipped == 0L) {
	                    int b = read();
	                    if (b < 0) {
	                        break;  // we reached EOF
	                    } else {
	                        bytesSkipped = 1; // we read one byte
	                    }
	                }
	                totalBytesSkipped += bytesSkipped;
	            }
	            return totalBytesSkipped;
	        }
	    }

 
}
