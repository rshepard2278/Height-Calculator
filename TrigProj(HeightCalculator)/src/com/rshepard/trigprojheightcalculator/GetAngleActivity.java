package com.rshepard.trigprojheightcalculator;

import java.text.DecimalFormat;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GetAngleActivity extends Activity implements SensorEventListener{

	private Camera mCamera;
    private CameraPreview mPreview;
    private CrossHairView crossHairView;
    private Button bottomButton;
    private EditText stepsInput;
    private TextView angleMeasure;
    
    private final float ACCEL_TO_ANGLE_CONVERSION = 4.5918367346938775510204081632653f;
	
	private SensorManager mgr;
	private Sensor accel;
	
	private float xValue;
	private float yValue;
	private float angle;
	private float angleInAccel;
	
	private float angleD;
	private float angleA;
	private int stepCount;
	private int clicks;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);

        mCamera = getCameraInstance();
        crossHairView = new CrossHairView(this);
        crossHairView.setBackgroundColor(Color.TRANSPARENT);
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        preview.addView(crossHairView);
        Toast.makeText(this, "Set Bottom Angle", Toast.LENGTH_LONG).show();
        
        bottomButton = (Button) findViewById(R.id.setAngleButton);
        bottomButton.setText("Set Angle Bottom");
        stepsInput = (EditText) findViewById(R.id.stepsInput);
        angleMeasure = (TextView) findViewById(R.id.angle_label);
        
        clicks = 0;
        angleA = 0;
        angleD = 0;
        stepCount = 0;
        
        initSensors();
    }
    
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }
    
    
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		 releaseCamera();     
	}

	private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch(event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			xValue = Math.abs(event.values[1]);
			yValue = Math.abs(event.values[2]);
			
			break;
		}
		
		angleInAccel = xValue - yValue;
		angle = (angleInAccel * ACCEL_TO_ANGLE_CONVERSION) -45;
		
		DecimalFormat df = new DecimalFormat("#.##");
		angle *= -1;
		angleMeasure.setText("Angle: " + df.format(angle));;
		crossHairView.refreshDrawableState();
	}

	public void onClickSetAngle(View view) {
		switch(clicks) {
		case 0:
			angleD = angle;
			clicks++;
	        Toast.makeText(this, "Aim at top of object and click set angle", Toast.LENGTH_LONG).show();
	        bottomButton.setText("Set Angle Top");
			break;
		case 1: 
			angleA = angle;
			clicks++;
			Toast.makeText(this, "Input step count and click ok", Toast.LENGTH_LONG).show();
			bottomButton.setText("OK");
			break;
		case 2:
			String stepString = String.valueOf(stepsInput.getText());
			if(stepString == null) {
				stepString = "0";
			}
			stepCount = Integer.parseInt(stepString);
			Intent goingBackToStart = new Intent();
			
			goingBackToStart.putExtra("angleA", angleA);
			goingBackToStart.putExtra("angleD", angleD);
			goingBackToStart.putExtra("steps", stepCount);
			
			setResult(RESULT_OK, goingBackToStart);
			finish();
			break;
			
		}
	}
	
	public void initSensors() {
		mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		xValue = 0;
		yValue = 0;
		angle = 0;
		angleInAccel = 0;

		mgr.registerListener(this, accel,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
}
