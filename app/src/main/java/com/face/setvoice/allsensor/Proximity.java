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
 * 近距离传感器界面
 * 
 * @author Gavin
 * 
 */
public class Proximity extends Activity {
	private TextView textViewX = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proximity_activity);
		textViewX = (TextView) findViewById(R.id.ProximityTextViewX);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
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
			textViewX.setText("设备与对象间距离" + e.values[0] + "cm");
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}
