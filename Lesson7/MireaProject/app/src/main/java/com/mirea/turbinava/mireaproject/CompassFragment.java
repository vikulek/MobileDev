package com.mirea.turbinava.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CompassFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] gravity;
    private float[] geomagnetic;

    private TextView azimuthText;
    private TextView logicHintText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, container, false);

        azimuthText = view.findViewById(R.id.azimuthText);
        logicHintText = view.findViewById(R.id.logicHintText);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;

        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                float azimuthRad = orientation[0];
                float azimuthDeg = (float) Math.toDegrees(azimuthRad);
                if (azimuthDeg < 0) {
                    azimuthDeg += 360;
                }

                azimuthText.setText("Азимут: " + (int) azimuthDeg + "°");

                // Пример логической задачи:
                if (azimuthDeg >= 345 || azimuthDeg <= 15) {
                    logicHintText.setText("Вы смотрите на север — ищите мох на северной стороне!");
                } else if (azimuthDeg >= 75 && azimuthDeg <= 105) {
                    logicHintText.setText("Вы смотрите на восток — здесь встаёт солнце.");
                } else if (azimuthDeg >= 165 && azimuthDeg <= 195) {
                    logicHintText.setText("Вы смотрите на юг — жаркая сторона!");
                } else if (azimuthDeg >= 255 && azimuthDeg <= 285) {
                    logicHintText.setText("Вы смотрите на запад — здесь садится солнце.");
                } else {
                    logicHintText.setText("Ориентируйтесь по природе!");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}