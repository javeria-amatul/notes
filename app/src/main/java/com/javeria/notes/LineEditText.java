package com.javeria.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class LineEditText extends AppCompatEditText {

    private Rect mRect;
    private Paint mPaint;

    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFFFD966); // Color of the lines on paper

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = (this.getHeight());
        int lineHeight = getLineHeight();
        int totalLines = height/lineHeight;
        Rect rect = mRect;
        Paint paint = mPaint;
        int baseline = getLineBounds(0,rect);
        for(int i = 0; i< totalLines; i++){
            canvas.drawLine(rect.left, baseline+1,rect.right, baseline+1, paint );
            baseline += lineHeight;
        }
    }
}
