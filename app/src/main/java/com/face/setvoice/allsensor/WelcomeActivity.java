package com.face.setvoice.allsensor;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

import com.face.setvoice.R;

public class WelcomeActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,MainSensorActivity.class);
			    startActivity(intent);
			    WelcomeActivity.this.finish();
			}
		}, 2000);
	}
}
