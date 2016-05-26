package com.rshepard.trigprojheightcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CrossHairView extends View{

	
	private Paint paint;

	
    public CrossHairView(Context context) {
         super(context);
         paint = new Paint();  
    }

    @Override
    protected void onDraw(Canvas canvas) {
       // TODO Auto-generated method stub
       super.onDraw(canvas);
       int x = getWidth();
       int y = getHeight();
       int middleX = x/2;
       int middleY = y/2;
   
       paint.setColor(Color.WHITE);
       paint.setStrokeWidth(5);


       canvas.drawLine(middleX -100, middleY, middleX + 100, middleY, paint);
       canvas.drawLine(middleX, middleY - 100, middleX, middleY + 100, paint);
   }
}
