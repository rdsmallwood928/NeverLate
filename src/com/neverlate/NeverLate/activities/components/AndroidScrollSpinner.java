package com.neverlate.NeverLate.activities.components;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.neverlate.NeverLate.R;

import java.text.AttributedCharacterIterator;
import java.util.HashMap;

/**
 * Created by bigwood928 on 3/19/14.
 */
public class AndroidScrollSpinner extends View {

    HashMap<String, Bitmap> bitmaps = new HashMap<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;
    private Path mPath;
    private Paint mPaint;

    public AndroidScrollSpinner(Context context) {
        super(context);
        init();
    }

    public AndroidScrollSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void init() {
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(R.color.bluegrass));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(9);
    }

    private void drawSomeText(Canvas canvas) {
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLUE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.bluegrass));
        canvas.drawText("Bert", getWidth()/2 , getWidth()/2, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
