package com.wisedrive.customerapp.commonclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DottedLineView extends View {

        private Paint mPaint;

        public DottedLineView(Context context) {
                super(context);
                init();
        }

        public DottedLineView(Context context, AttributeSet attrs) {
                super(context, attrs);
                init();
        }

        private void init() {
                mPaint = new Paint();
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
        }

        @Override
        protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaint);
        }
}

