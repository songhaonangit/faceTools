package com.face.setvoice.allsensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.face.setvoice.R;

public class ListViewActivity extends Activity {
	private ListView listView;
	private SensorManager sensorManager;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	
	private List<Sensor> list = null;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter listItemAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		textView1 = (TextView) findViewById(R.id.MaintextView1);
		textView2 = (TextView) findViewById(R.id.MaintextView2);
		textView3 = (TextView) findViewById(R.id.MaintextView3);
		
		listView = (ListView) findViewById(R.id.MainListView);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		list =sensorManager.getSensorList(Sensor.TYPE_ALL);
		ArrayList<Sensor> tempSensors=new ArrayList<Sensor>();
		for(Sensor s:list){
			tempSensors.add(s);
		}
		ArrayList<Integer> arrayIndex = new ArrayList<Integer>();//编号
		for(int i =0;i<tempSensors.size();i++){
			String string = tempSensors.get(i).getName();
			if (!(string.toLowerCase().contains("accelerometer")
					|| string.toLowerCase().contains("orientation")
					|| string.toLowerCase().contains("magnetic")
					|| string.toLowerCase().contains("gyroscope")
					|| string.toLowerCase().contains("light")
					|| string.toLowerCase().contains("pressure")
					|| string.toLowerCase().contains("temperature")
					|| string.toLowerCase().contains("proximity")
					|| string.toLowerCase().contains("gravity")
					|| string.toLowerCase().contains("linear")
					|| string.toLowerCase().contains("rotation"))) {
				arrayIndex.add(i);
			}
		}
		for(Integer i:arrayIndex){
			tempSensors.remove(i);
		}
		String sensorName[] = new String[tempSensors.size()];
		for (int i = 0; i < tempSensors.size(); i++) {
			String string = ((Sensor) tempSensors.get(i)).getName();
			if (string.toLowerCase().contains("accelerometer")) {
				sensorName[i] = "加速度传感器";
			} else if (string.toLowerCase().contains("orientation")) {
				sensorName[i] = "方向传感器";
			} else if (string.toLowerCase().contains("magnetic")) {
				sensorName[i] = "磁力传感器";
			} else if (string.toLowerCase().contains("gyroscope")) {
				sensorName[i] = "陀螺仪";
			} else if (string.toLowerCase().contains("light")) {
				sensorName[i] = "光线传感器";
			} else if (string.toLowerCase().contains("pressure")) {
				sensorName[i] = "压力传感器";
			} else if (string.toLowerCase().contains("temperature")
					&& !string.toLowerCase().contains("ambient")) {
				sensorName[i] = "设备温度传感器";
			} else if (string.toLowerCase().contains("temperature")
					&& string.toLowerCase().contains("ambient")) {
				sensorName[i] = "环境温度传感器";
			} else if (string.toLowerCase().contains("proximity")) {
				sensorName[i] = "近距离感应传感器";
			} else if (string.toLowerCase().contains("gravity")) {
				sensorName[i] = "重力传感器";
			} else if (string.toLowerCase().contains("linear")) {
				sensorName[i] = "线性加速度传感器";
			} else if (string.toLowerCase().contains("rotation")) {
				sensorName[i] = "旋转矢量传感器";
			}else {
				sensorName[i] = "未知的传感器类型";
			}
		}
		for (int i = 0; i < tempSensors.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemText1", "设备名称：" + tempSensors.get(i).getName());
			map.put("ItemText2", "供应商："+tempSensors.get(i).getVendor());
			map.put("ItemText3", "识别率："+tempSensors.get(i).getResolution());
			map.put("ItemText4", "功率："+tempSensors.get(i).getPower()+"mA");
			map.put("ItemTitle", sensorName[i]);
			listItem.add(map);
			listItemAdapter = new SimpleAdapter(this, listItem,// ����Դ
					R.layout.list_item,// ListItem��XMLʵ��
					new String[] { "ItemTitle", "ItemText1","ItemText2","ItemText3","ItemText4" }, new int[] {
							R.id.ItemTitle, R.id.ItemText1 , R.id.ItemText2,   R.id.ItemText3, R.id.ItemText4});
		}

		Build build = new Build();
		textView1.setText("当前设备型号：" + build.MODEL);
		textView2.setText("检测出传感器"+tempSensors.size()+"个");
		textView3.setText("点击以查看传感器数据");
		listView.setAdapter(listItemAdapter); //显示数据
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String string = list.get(arg2).getName();
				Intent intent = new Intent();
				if (string.toLowerCase().contains("accelerometer")) {
					intent.setClass(getApplicationContext(),
							Accelerometer.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("orientation")) {
					intent.setClass(getApplicationContext(), Orientation.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("magnetic")) {
					intent.setClass(getApplicationContext(), Magnetic.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("gyroscope")) {
					intent.setClass(getApplicationContext(), Gyroscope.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("light")) {
					intent.setClass(getApplicationContext(), Light.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("pressure")) {
					intent.setClass(getApplicationContext(), Pressure.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("temperature")
						&& !string.toLowerCase().contains("ambient")) {
					intent.setClass(getApplicationContext(), Temperature.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("temperature")
						&& string.toLowerCase().contains("ambient")) {
					intent.setClass(getApplicationContext(),
							AmbientTemperature.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("proximity")) {
					intent.setClass(getApplicationContext(), Proximity.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("gravity")) {
					intent.setClass(getApplicationContext(), Gravity.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("linear")) {
					intent.setClass(getApplicationContext(),
							LinearAccelerometer.class);
					startActivity(intent);
				} else if (string.toLowerCase().contains("rotation")) {
					intent.setClass(getApplicationContext(), Rotation.class);
					startActivity(intent);
				}
			}
		});
	}
}
