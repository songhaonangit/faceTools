package com.face.setvoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.face.setvoice.allsensor.MainSensorActivity;
import com.face.setvoice.allsensor.WelcomeActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar seekBar;
    private TextView textView;
    TextView textView2;
    Switch switch1;
    Button bt_sensor;
    private static final String TAG = "DEV_TEST";
    String commod1 = "echo 1 > /sys/devices/platform/dev_ctl/relay_status"+"\n";
    String commod0 = "echo 0 > /sys/devices/platform/dev_ctl/relay_status"+"\n";
    AudioManager am;
    private boolean shortPress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar =  findViewById(R.id.seek_progress);
        seekBar.setOnSeekBarChangeListener(this);
        textView = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        bt_sensor = findViewById(R.id.bt_sensor);
        bt_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
            }
        });
        switch1 =  findViewById(R.id.switch_1);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    writeSysFile(commod1);
                } else {
                    writeSysFile(commod0);
                }
            }
        });
        //songhaonan add 20180511 end
        am = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        //获取系统最大音量
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(maxVolume);
        //获取当前音量
        int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setProgress(currentVolume);

        showSeekBar();
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            //设置系统音量
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            seekBar.setProgress(currentVolume);
            textView.setText("Voice:" + Integer.toString(progress));
            Log.d("songhaonan", "-------onProgressChanged-------"+progress);
        }



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d("songhaonan", "-------开始滑动！");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d("songhaonan", "-------停止滑动！");
    }

    public void showSeekBar(){
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //获取系统最大音量
        int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.d("songhaonan", "-------maxVolume-------"+maxVolume);
        seekBar.setMax(maxVolume);
        //获取当前音量
        int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d("songhaonan", "-------currentVolume-------"+currentVolume);
        seekBar.setProgress(currentVolume);
        seekBar.setVisibility(View.VISIBLE);

        textView.setVisibility(View.VISIBLE);
        textView.setText("Value:" + Integer.toString(currentVolume));

    }
    public static void writeSysFile(String commod){

        Process p = null;
        DataOutputStream os = null;

        //  String commod = "echo 1 > /sys/devices/platform/dev_ctl/cam_mode\n";
        //String commod = "echo \n";
        try {
            Log.w("songhaonan", "start write 1 to system file ---> " );
            p = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(p.getOutputStream());
            os.write(commod.getBytes());
            os.flush();

            os.writeBytes("exit\n");
            os.flush();
            Runtime.getRuntime().exec(commod);
            Log.d("songhaonan", "end write 1 to system file ---> " );
        } catch (IOException e) {
            Log.e("songhaonan", " can't write " +e.getMessage());
            e.printStackTrace();
        } finally {
            if(p != null){
                p.destroy();
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String read(String sys_path){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = null; // 此处进行读操作
            try {
                process = runtime.exec("cat " + sys_path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line ;
            while (null != (line = br.readLine())) {
                Log.w(MainActivity.TAG, "read data ---> " + line);
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(MainActivity.TAG, "*** ERROR *** Here is what I know: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("QQQQ", "-----------------------" + keyCode);
        textView2.setText("onKeyUp---keycode-----" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_F2) {
            Log.i("QQQQ", "----------ring--onKeyUp---keycode-----------" + keyCode);

            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_F1) {
            Log.i("QQQQ", "----------phone---onKeyUp                                                                                                                                                                                                                                         ---keycode----------" + keyCode);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        textView2.setText("onKeyDown---keycode-----" + keyCode);
        Log.i("QQQQ", "-----------------------" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_F2) {
            Log.i("QQQQ", "----------ring-onKeyDown---keycode------------" + keyCode);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_F1) {
            Log.i("QQQQ", "----------phone--onKeyDown---keycode-----------" + keyCode);
            return true;
        }

        if (keyCode == 445) {
            if(event.getAction() == KeyEvent.ACTION_DOWN) {
                event.startTracking(); //只有执行了这行代码才会调用onKeyLongPress
                if (event.getRepeatCount() == 0) {
                    shortPress = true;//短按事件逻辑
                }
                return true;
            }
        }

        if (keyCode == 446) {
            if(event.getAction() == KeyEvent.ACTION_DOWN) {
                event.startTracking(); //只有执行了这行代码才会调用onKeyLongPress
                if (event.getRepeatCount() == 0) {
                    shortPress = true;//短按事件逻辑
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == 445) {
            //长按事件的逻辑
            Log.i("QQQQ", "-----onKeyLongPress---keycode------------------" + keyCode);
            textView2.setText("onKeyLongPress---keycode-----" + keyCode);
            return true;
        }
        if (keyCode == 446) {
            //长按事件的逻辑
            Log.i("QQQQ", "-----onKeyLongPress---keycode------------------" + keyCode);
            textView2.setText("onKeyLongPress---keycode-----" + keyCode);
            return true;
        }
        return false;

    }
}
