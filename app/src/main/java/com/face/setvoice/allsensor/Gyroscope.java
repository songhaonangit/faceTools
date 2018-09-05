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
 * 陀螺仪传感器界面
 * 
 * @author Gavin
 * 
 */
public class Gyroscope extends Activity {
	private TextView textViewX = null;
	private TextView textViewY = null;
	private TextView textViewZ = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gyroscope_activity);
		textViewX = (TextView) findViewById(R.id.GyroscopeTextViewX);
		textViewY = (TextView) findViewById(R.id.GyroscopeTextViewY);
		textViewZ = (TextView) findViewById(R.id.GyroscopeTextViewZ);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 最后一个参数是采样率，分别为最快，游戏，普通，用户界面
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
			textViewX.setText("沿X方向旋转角速度\n          " + e.values[0]);
			textViewY.setText("沿Y方向旋转角速度\n          " + e.values[1]);
			textViewZ.setText("沿Z方向旋转角速度\n          " + e.values[2]);
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {

		}
	};
}
