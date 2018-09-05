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
 * 磁场传感器界面
 * 
 * @author Gavin
 * 
 */
public class Magnetic extends Activity {
	private TextView textViewX = null;
	private TextView textViewY = null;
	private TextView textViewZ = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.magnetic_activity);
		textViewX = (TextView) findViewById(R.id.MagneticTextViewX);
		textViewY = (TextView) findViewById(R.id.MagneticTextViewY);
		textViewZ = (TextView) findViewById(R.id.MagneticTextViewZ);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
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
			textViewX.setText("X方向磁场的分量\n          " + e.values[0] + "uT");
			textViewY.setText("Y方向磁场的分量\n          " + e.values[1] + "uT");
			textViewZ.setText("Z方向磁场的分量\n          " + e.values[2] + "uT");
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}
