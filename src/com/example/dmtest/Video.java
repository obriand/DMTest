package com.example.dmtest;

import android.provider.BaseColumns;
import android.util.Log;

public class Video implements BaseColumns {
	
	private static final String TAG = "com.example.dmtest.Video";
	
	/**
	 * Video constructor
	 */
	private Video() {
		Log.i(TAG, "Video()");
	}
    
    public static final String VIDEO_ID = "";
    public static final String VIDEO_NAME = "";
    public static final String VIDEO_OWNER = "";
    public static final String VIDEO_CHANNEL = "";
    public static final String VIDEO_TITLE = "";

}

