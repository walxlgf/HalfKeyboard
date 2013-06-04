package com.half.keyboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.half.keyboard.fragment.KeyboardFragment;
import com.half.keyboard.service.Service;

public class KeyboardActivity extends FragmentActivity {
	public final static String ACTION_FRAGMENT_TRANSACTION = "action_fragment_transaction";
	public final static String PARAM_FRAGMENT_TRANSACTION = "param_fragment_transaction";
	public final static String FRAGMENT_CONNECT = "fragment_connect";
	public final static String FRAGMENT_KEYBOARD = "fragment_keyboard";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        KeyboardFragment keyboardFragment = new KeyboardFragment();
		fragmentTransaction.add(android.R.id.content, keyboardFragment);
		fragmentTransaction.commit();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(getApplicationContext(), Service.class);
		stopService(intent);
	}

}
