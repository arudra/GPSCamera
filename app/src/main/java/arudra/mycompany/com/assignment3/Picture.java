package arudra.mycompany.com.assignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


public class Picture extends android.support.v4.app.Fragment implements SensorEventListener
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private android.hardware.Camera mCamera; //Camera Device Instance
    private Preview mPreview; //Camera Preview

    private SensorManager sensorManager;
    private double newAccel, defaultAccel;


    public static Picture newInstance(String param1, String param2) {
        Picture fragment = new Picture();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Picture() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        newAccel = SensorManager.GRAVITY_EARTH;
        defaultAccel = SensorManager.GRAVITY_EARTH;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) { return inflater.inflate(R.layout.picture_fragment, container, false); }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) { }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            takePicture(event);
        }
    }

    private void takePicture (SensorEvent event)
    {
        float[] values = event.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        double accSqRoot = Math.sqrt(x * x + y * y + z * z);
        defaultAccel = newAccel;
        newAccel = accSqRoot;
        double delta = newAccel - defaultAccel;

        if (delta > 10)
        {
            //Stop Detection
            sensorManager.unregisterListener(this);
            Toast.makeText(getActivity().getApplicationContext(), "Taking a Picture in 1 second", Toast.LENGTH_SHORT).show();

            //Run on another thread which will delay by one second
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    try {
                        mCamera.takePicture(null, null, null, new PhotoHandler(getActivity().getApplicationContext()));
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }, 1000);


            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onResume()
    {
        super.onResume();
        openCamera();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("Resume", "Starting camera");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        closeCamera();
        sensorManager.unregisterListener(this);
        Log.d("Pause", "Stopping camera");
    }

    public static android.hardware.Camera getCameraInstance()
    {
        android.hardware.Camera c = null;
        try { c = android.hardware.Camera.open(); }
        catch (Exception e) { e.printStackTrace(); }
        return c;
    }

    private void openCamera ()
    {
        //Start the Preview
        mCamera = getCameraInstance();
        if (mCamera != null) {
            //Create Preview and set it as content for fragment
            mPreview = new Preview(getActivity(), getActivity().getApplicationContext(), mCamera);
            FrameLayout preview = (FrameLayout) getActivity().findViewById(R.id.surface);
            preview.addView(mPreview);
            Log.d("Camera","Camera Opened");
        }
    }

    private void closeCamera()
    {
        try {
            if(mCamera != null){
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                Log.d("Camera","Camera Closed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Interrupted while closing camera", e);
        }
    }
}
