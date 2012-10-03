package com.example.dmtest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoDetailsActivity  extends Activity {

	private VideoView mVideoView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetails);
        
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVideoView.setVideoURI(Uri.parse("http://mirror.cessen.com/blender.org/peach/trailer/trailer_iphone.m4v"));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.start();
        
    }
}
