package com.face.setvoice.allsensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.face.setvoice.R;

/**
 * 重力传感器界面
 * 
 * @author Gavin
 * 
 */
public class Gravity extends Activity {
	private TextView textViewX = null;
	private TextView textViewY = null;
	private TextView textViewZ = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gravity_activity);
		textViewX = (TextView) findViewById(R.id.GravityTextViewX);
		textViewY = (TextView) findViewById(R.id.GravityTextViewY);
		textViewZ = (TextView) findViewById(R.id.GravityTextViewZ);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		super.onStop();
		sensorManager.unregisterListener(listener);
	}

	private SensorEventListener listener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent e) {
			textViewX.setText("沿X方向重力加速度\n          " + e.values[0] + "m/s^2");
			textViewY.setText("沿Y方向重力加速度\n          " + e.values[1] + "m/s^2");
			textViewZ.setText("沿Z方向重力加速度\n          " + e.values[2] + "m/s^2");
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}
