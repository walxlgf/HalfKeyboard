package com.half.keyboard.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;

import com.half.keyboard.fragment.ConnectFragment;
import com.half.keyboard.service.Service;

public class ConnectionActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Service.ACTION_SESSION_OPENED);
		registerReceiver(receiver, filter);

		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ConnectFragment connectFragment = new ConnectFragment();
		fragmentTransaction.add(android.R.id.content, connectFragment);
		fragmentTransaction.commit();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Service.ACTION_SESSION_OPENED.equals(action)) {
                Intent intent1 = new Intent(ConnectionActivity.this,KeyboardActivity.class);
                startActivity(intent1);
                finish();
			}
		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), Service.class);
		stopService(intent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

}
