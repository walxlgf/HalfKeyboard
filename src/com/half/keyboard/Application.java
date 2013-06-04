package com.single.keyboard;

import android.content.Intent;

import com.half.keyboard.service.Service;
import com.half.keyboard.utils.Log;

/**
 * 
 */

/**
 * @author Thinkpad
 *
 */
public class Application extends android.app.Application {
	public static final String TAG = "Application";

	@Override
	public void onCreate() {
		super.onCreate();
		Intent intent = new Intent(this, Service.class);
		startService(intent);
		Log.d(TAG, "onCreate:startService");
	}

}
