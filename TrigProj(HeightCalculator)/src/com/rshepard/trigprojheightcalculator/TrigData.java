package com.rshepard.trigprojheightcalculator;

import android.util.Log;

public class TrigData {
	
	private final double DEGREE_TO_RADIAN = 0.0174533f;
	private final double STRIDE_CONVERSION = 0.413;
	private static final String TAG = TrigData.class.getSimpleName();
	private double inchesPerStride; 

	private double lengthA;
	private double lengthB;
	private double lengthC;
	private double lengthD;
	private double lengthE;
	private double totalHeight;
	
	private double sinA;
	private double cosA;
	private double tanA;
	
	private double sinD;
	private double cosD;
	private double tanD;
	
	private double angleA;
	private double angleD;
	
	private double radianA;
	private double radianD;
	
	private int steps;
	
	private int userHeight;
	
	public TrigData() {
		
	}

	public TrigData calcData() {
		convertToRads();
		findLengthBFromSteps();
		assignTrigFunctions();
		calcLengths();
		return this;
	}
	
	private void convertToRads() {
		Log.d(TAG, "AngleA: " + String.valueOf(angleA));
		Log.d(TAG, "AngleD: " + String.valueOf(angleD));
		radianA = angleA * DEGREE_TO_RADIAN;
		radianD = angleD * DEGREE_TO_RADIAN;
	}
	
	private void findLengthBFromSteps() {
		inchesPerStride = userHeight * STRIDE_CONVERSION;
		lengthB = inchesPerStride * steps;
	}
	
	private void assignTrigFunctions() {
		sinA = Math.sin(radianA);
		cosA = Math.cos(radianA);
		tanA = Math.tan(radianA);
		
		sinD = Math.sin(radianD);
		cosD = Math.cos(radianD);
		tanD = Math.tan(radianD);
		Log.d(TAG, String.valueOf(sinD));
		Log.d(TAG, String.valueOf(cosD));
		Log.d(TAG, String.valueOf(tanD));
	}
	
	private void calcLengths() {
		lengthC = lengthB / cosA;
		lengthA = lengthB * tanA;
		
		lengthE = lengthB / cosD;
		lengthD = lengthB * tanD;
		Log.d(TAG, "lengthD: " + String.valueOf(lengthD));
		Log.d(TAG, "lengthE: " + String.valueOf(lengthE));
		totalHeight = lengthA + lengthD;
		
	}
	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	
	public double getLengthB() {
		return lengthB;
	}

	public double getAngleA() {
		return angleA;
	}

	public double getAngleD() {
		return angleD;
	}

	public double getLengthA() {
		return lengthA;
	}

	public double getLengthC() {
		return lengthC;
	}

	public double getLengthD() {
		Log.d(TAG, "Called from lengthD: " + String.valueOf(lengthD));
		return lengthD;
	}

	public double getLengthE() {
		Log.d(TAG, " Called from getlengthE: " + String.valueOf(lengthE));
		return lengthE;
	}

	public double getTotalHeight() {
		return totalHeight;
	}

	public double getSinA() {
		return sinA;
	}

	public double getCosA() {
		return cosA;
	}

	public double getTanA() {
		return tanA;
	}

	public double getSinD() {
		return sinD;
	}

	public double getCosD() {
		return cosD;
	}

	public double getTanD() {
		return tanD;
	}

	public void setAngleA(double angleA) {
		this.angleA = angleA;
	}

	public void setAngleD(double angleD) {
		this.angleD = angleD;
	}

	public int getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(int userHeight) {
		this.userHeight = userHeight;
	}


	
}
