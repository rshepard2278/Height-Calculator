package com.rshepard.trigprojheightcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends Activity {
	
	private static final String TAG = StartActivity.class.getSimpleName();
	private final String DEGREE  = "\u00b0";
	
	private TextView sinAvalue;
	private TextView cosAvalue;
	private TextView tanAvalue;
	
	private TextView sinDvalue;
	private TextView cosDvalue;
	private TextView tanDvalue;
	
	private TextView lengthAvalue;
	private TextView lengthBvalue;
	private TextView lengthCvalue;
	private TextView lengthDvalue;
	private TextView lengthEvalue;
	
	private TextView angleAvalue;
	private TextView angleDvalue;
	
	private TextView heightValue;
	private EditText userHeight;
	
	private Button calcHeightButton;
	
	private TrigData returnData;
	
	private int userHeightValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		initWidgets();
		returnData = new TrigData();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initWidgets() {
		sinAvalue = (TextView) findViewById(R.id.sin_value);
		cosAvalue = (TextView) findViewById(R.id.cos_value);
		tanAvalue = (TextView) findViewById(R.id.tan_value);
		
		sinDvalue = (TextView) findViewById(R.id.sind_value);
		cosDvalue = (TextView) findViewById(R.id.cosd_value);
		tanDvalue = (TextView) findViewById(R.id.tand_value);
		
		lengthAvalue = (TextView) findViewById(R.id.lengthA_value);
		lengthBvalue = (TextView) findViewById(R.id.lengthB_value);
		lengthCvalue = (TextView) findViewById(R.id.lengthc_value);
		lengthDvalue = (TextView) findViewById(R.id.lengthD_value);
		lengthEvalue = (TextView) findViewById(R.id.lengthE_value);
		
		angleAvalue = (TextView) findViewById(R.id.angleAvalue);
		angleDvalue = (TextView) findViewById(R.id.angleDvalue);
		
		userHeight = (EditText) findViewById(R.id.user_height);
		
		heightValue = (TextView) findViewById(R.id.heightValue);
		
		calcHeightButton = (Button) findViewById(R.id.calcHeightButton);

	
	}
	
	public void onClickCalcHeight(View view) {
		userHeightValue = Integer.parseInt(String.valueOf(userHeight.getText()));
		returnData.setUserHeight(userHeightValue);
		Intent getAngleScreen = new Intent(this, GetAngleActivity.class);
		final int result = 1;
		startActivityForResult(getAngleScreen, result);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		returnData.setAngleA(data.getFloatExtra("angleA", 0));
		returnData.setAngleD(data.getFloatExtra("angleD", 0));
		returnData.setSteps(data.getIntExtra("steps", 0));
		returnData = returnData.calcData();
		setValues();
	}
	
	private void setValues() {
		String incheStr = " in.";
		initWidgets();
		sinAvalue.setText(trimString(String.valueOf(returnData.getSinA())));
		cosAvalue.setText(trimString(String.valueOf(returnData.getCosA())));
		tanAvalue.setText(trimString(String.valueOf(returnData.getTanA())));
		
		sinDvalue.setText(trimString(String.valueOf(returnData.getSinD())));
		cosDvalue.setText(trimString(String.valueOf(returnData.getCosD())));
		tanDvalue.setText(trimString(String.valueOf(returnData.getTanD())));
		
		lengthAvalue.setText(trimString(String.valueOf(returnData.getLengthA())) + incheStr);
		lengthBvalue.setText(trimString(String.valueOf(returnData.getLengthB())) + incheStr);
		lengthCvalue.setText(trimString(String.valueOf(returnData.getLengthC())) + incheStr);
		lengthDvalue.setText(trimString(String.valueOf(returnData.getLengthD())) + incheStr);
		lengthEvalue.setText(trimString(String.valueOf(returnData.getLengthE())) + incheStr);
		
		double height = Math.round(returnData.getTotalHeight());
		int feet = (int) height / 12;
		int inches = (int) height % 12;
		String inchVal = trimString(String.valueOf(inches));
		String feetVal = trimString(String.valueOf(feet));
		
		heightValue.setText(feetVal + "ft. " + inchVal + "in.");
		

		angleAvalue.setText(trimString(String.valueOf(returnData.getAngleA())) + DEGREE);
		
		angleDvalue.setText(trimString(String.valueOf(returnData.getAngleD())) + DEGREE);
		
	}
	
	private String trimString(String str) {
		String formatStr = "";
		if(str.length() > 4) {
			for(int i = 0; i < 4; i++) {
				formatStr += str.charAt(i);
			}
			return formatStr;
		} else {
			return str;
		}		
	}

	public void onClickResetValues(View view) {}
}
