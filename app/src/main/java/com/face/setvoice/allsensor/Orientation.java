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
 * 方向传感器界面
 * 
 * @author Gavin
 * 
 */
public class Orientation extends Activity {
	private TextView textViewX = null;
	private TextView textViewY = null;
	private TextView textViewZ = null;
	private TextView textViewA = null;
	private TextView textViewB = null;
	private TextView textViewC = null;
	private SensorManager sensorManager = null;
	private Sensor sensor = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orientation_activity);
		textViewX = (TextView) findViewById(R.id.orientationTextViewX);
		textViewY = (TextView) findViewById(R.id.orientationTextViewY);
		textViewZ = (TextView) findViewById(R.id.orientationTextViewZ);
		textViewA = (TextView) findViewById(R.id.orientationTextViewA);
		textViewB = (TextView) findViewById(R.id.orientationTextViewB);
		textViewC = (TextView) findViewById(R.id.orientationTextViewC);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			textViewX.setText("X轴方向的角度" + event.values[0] + "度");
			textViewY.setText("Y轴方向的角度" + event.values[1] + "度");
			textViewZ.setText("Z轴方向的角度" + event.values[2] + "度");
			String orientationString = "";
			String topBottomString = "";
			String leftRightString = "";
			if (event.values[0] == 0) {
				orientationString = "正北";
			} else if (event.values[0] == 90) {
				orientationString = "正东";
			} else if (event.values[0] == 180) {
				orientationString = "正南";
			} else if (event.values[0] == 270) {
				orientationString = "正西";
			} else if (event.values[0] < 90 && event.values[0] > 0) {
				orientationString = "北偏东" + event.values[0] + "度";
			} else if (event.values[0] < 180 && event.values[0] > 90) {
				orientationString = "南偏东" + (180 - event.values[0]) + "度";
			} else if (event.values[0] < 270 && event.values[0] > 180) {
				orientationString = "南偏西" + (event.values[0] - 180) + "度";
			} else if (event.values[0] < 360 && event.values[0] > 270) {
				orientationString = "北偏西" + (360 - event.values[0]) + "度";
			}
			if (event.values[1] == 0) {
				topBottomString = "手机头部或底部没有翘起";
			} else if (event.values[1] > 0) {
				topBottomString = "底部向上翘起" + event.values[1] + "度";
			} else if (event.values[1] < 0) {
				topBottomString = "顶部向上翘起" + Math.abs(event.values[1]) + "度";
			}
			if (event.values[2] == 0) {
				leftRightString = "手机左侧或右侧没有翘起";
			} else if (event.values[2] > 0) {
				leftRightString = "右侧向上翘起" + event.values[2] + "度";
			} else if (event.values[2] < 0) {
				leftRightString = "左侧向上翘起" + Math.abs(event.values[2]) + "度";
			}
			textViewA.setText("手机头部指向\n          " + orientationString);
			textViewB.setText("手机头部或底部翘起的角度:\n          " + topBottomString);
			textViewC.setText("手机左侧或右侧翘起的角度:\n          " + leftRightString);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

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

}
