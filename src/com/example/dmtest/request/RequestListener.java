package com.example.dmtest.request;

public interface RequestListener {

	/**
	 * Callback for RequestManager requests
	 * 
	 * @param code The HTTP code
	 * 
	 * @param response The body of the response
	 */
	public void onResponse(int code, String response);

}
