package com.example.sensors1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ClipData.newIntent;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
  private static final String LOG_TAG = MainActivity.class.getSimpleName();
  public static final String EXTRA_MESSAGE = "com.example.sensors1.extra.MESSAGE";

  private SensorManager sensorManager;
  private Sensor sensorAccelerometer;
  private Sensor sensorLight;
  private Sensor sensorPressure;
  private Sensor sensorGeo;

  TextView editText_x;
  TextView editText_y;
  TextView editText_z;
  TextView editText_light;
  TextView editText_pressure;
  Button getGPS;
  TextView editText_lat;
  TextView editText_lon;

  @SuppressLint("CutPasteId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    editText_x = findViewById(R.id.editText_x);
    editText_y = findViewById(R.id.editText_y);
    editText_z = findViewById(R.id.editText_z);
    editText_light = findViewById(R.id.editText_light);
    editText_pressure = findViewById(R.id.editText_magnetic);
    editText_lat = findViewById(R.id.editText_lat);
    editText_lon = findViewById(R.id.editText_lon);


    if (sensorLight != null) {
        sensorManager.registerListener(MainActivity.this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    } else {
        editText_light.setText("Sensor not supported");
    }

    if (sensorPressure != null) {
      sensorManager.registerListener(MainActivity.this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
    } else {
      editText_pressure.setText("Sensor not supported");
    }

    getGPS = (Button) findViewById(R.id.getGPSBtn);
    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    getGPS.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        GPSTracker g = new GPSTracker(getApplicationContext());
        Location l = g.getLocation();
        if (l != null) {
          double lat = l.getLatitude();
          double lon = l.getLongitude();
//          Toast.makeText(getApplicationContext(), "LAT: "+lat+"LONG: "+lon, Toast.LENGTH_LONG).show();
          editText_lat.setText(String.format("Lat: %.6s", lat));
          editText_lon.setText(String.format("Lon: %.6s", lon));
        }
      }
    });
  }

  public void launchSecondActivity(View view) {
    Log.d(LOG_TAG, "Button clicked!");
    Intent intent = new Intent(this, SecondActivity.class);
    String message = "O aparelho foi movido bruscamente";
    intent.putExtra(EXTRA_MESSAGE, message);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

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

        editText_x.setText(String.format("X: %s", sensorX));
        editText_y.setText(String.format("Y: %s", sensorY));
        editText_z.setText(String.format("Z: %s", sensorZ));

        if (Math.abs(sensorX) > 12f || Math.abs(sensorY) > 12f || Math.abs(sensorZ) > 12f) {
//          Toast.makeText(this, "moveeeeeeeeeu", Toast.LENGTH_LONG).show();
          launchSecondActivity(findViewById(R.id.bottom));
        }
    } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
      editText_light.setText(String.format("Luminosidade: %s", event.values[0]));
    } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
      editText_pressure.setText(String.format("Campo Magnético: %s", event.values[0]));
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
