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

public class AmbientTemperature extends Activity {
	private TextView textViewX = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ambient_temperature_activity);
		textViewX = (TextView) findViewById(R.id.AmbientTemperatureTextViewX);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
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
			textViewX.setText("环境温度" + e.values[0] + "度");
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}
