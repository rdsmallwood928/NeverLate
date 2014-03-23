package com.neverlate.NeverLate.activities.components;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.View;

/**
 * Created by bigwood928 on 3/19/14.
 */
public class AndroidScrollSpinner extends View {


    public AndroidScrollSpinner(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawSomeText(canvas);
        canvas.restore();

    }

    private void drawSomeText(Canvas canvas) {
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLUE);
        canvas.drawTextOnPath("Bert", new Path(), 0.0f,0.0f, titlePaint);
    }

    private void drawBackground(Canvas canvas) {
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.RED);
        Bitmap background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas.drawBitmap(background, 0, 0, backgroundPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
