package com.example.sensors1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
//  private static final String LOG_TAG = MainActivity.class.getSimpleName();
//  private EditText mMessageEditText;
//
  public static final String EXTRA_MESSAGE = "com.example.sensors1.extra.MESSAGE";

  private SensorManager sensorManager;
  private Sensor sensorAccelerometer;
  private Sensor sensorLight;

  TextView editText_x;
  TextView editText_y;
  TextView editText_z;
  TextView editText_light;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    editText_x = findViewById(R.id.editText_x);
    editText_y = findViewById(R.id.editText_y);
    editText_z = findViewById(R.id.editText_z);
    editText_light = findViewById(R.id.editText_light);

    if (sensorLight != null) {
        sensorManager.registerListener(MainActivity.this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    } else {
        editText_light.setText("Sensor not supported");
    }
  }

//  public void launchSecondActivity(View view) {
//    Log.d(LOG_TAG, "Button clicked!");
//    Intent intent = new Intent(this, SecondActivity.class);
//    String message = mMessageEditText.getText().toString();
//    intent.putExtra(EXTRA_MESSAGE, message);
//    startActivity(intent);
//  }

  protected void onResume() {
      super.onResume();
      sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }

  protected void onPause() {
      super.onPause();
      sensorManager.unregisterListener(this);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    Sensor sensor = event.sensor;
    if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        float sensorX = event.values[0];
        float sensorY = event.values[1];
        float sensorZ = event.values[2];

        editText_x.setText("X: " + sensorX);
        editText_y.setText("Y: " + sensorY);
        editText_z.setText("Z: " + sensorZ);
    } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
        editText_light.setText("Light Intensity: " + event.values[0]);
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
