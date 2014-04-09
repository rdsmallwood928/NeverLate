package com.neverlate.NeverLate.activities.components.AndroidScrollSpinner;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.neverlate.NeverLate.R;

import java.util.*;

/**
 * Created by bigwood928 on 3/19/14.
 */
public class AndroidScrollSpinner extends View implements AndroidScrollModel.AndroidScrollModelListener {

    HashMap<String, Bitmap> bitmaps = new HashMap<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;
    private Path mPath;
    private Paint mPaint;
    private AndroidScrollModel model = new AndroidScrollModel(Collections.emptyList());
    private float[] textWidths;
    private Rect textBounds = new Rect(0,0,0,0);
    private GestureDetector mDetector;
    private float dragOffset = 0;
    private float textHeight;
    private float upperSpacing;
    private static final int EXTRA_DRAWS_MODEL_OFFSET = 2;

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
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(1);

        mDetector = new GestureDetector(this.getContext(), new GestureListener(this));
    }

    public void setModel(List<Object> model) {
        this.model = new AndroidScrollModel(model);
        this.model.addListener(this);
    }

    private void setDragOffset(float dragOffset) {
        this.dragOffset = dragOffset;
    }

    private void addToDragOffset(float distanceX) {
        this.dragOffset += distanceX;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        mPath.lineTo(getWidth(), 0);
        mPaint.setTextSize(getHeight()/3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.counter_text_bg));
        Object selection = model.getSelectedItem();
        if(selection!=null) {
            mPaint.setColor(getResources().getColor(R.color.white));
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(1);
            Object previous = model.getObjectXIndexesAway(-1);
            Object next = model.getObjectXIndexesAway(1);

            String selectionString = selection.toString();
            mPaint.getTextBounds(selectionString, 0, selectionString.length(), textBounds);
            int halfWidth = getWidth() / 2;
            float widthOffset = halfWidth -((textBounds.right-textBounds.left)/2);
            textHeight = Math.abs(textBounds.top-textBounds.bottom);
            int height = getHeight();
            float heightExtraSplit = (height -(textHeight))/2;
            float middleHeightOffset =  textHeight + heightExtraSplit;
            canvas.drawTextOnPath(selectionString, mPath, widthOffset, middleHeightOffset+dragOffset, mPaint);

            String previousString = previous.toString();
            mPaint.getTextBounds(previousString, 0, previousString.length(), textBounds);
            widthOffset = halfWidth -((textBounds.right-textBounds.left)/2);
            float heightOffset = textHeight + 2;
            canvas.drawTextOnPath(previousString, mPath, widthOffset, heightOffset+dragOffset, mPaint);

            upperSpacing = middleHeightOffset-textHeight*2-2;

            String nextString = next.toString();
            mPaint.getTextBounds(nextString, 0, nextString.length(), textBounds);
            widthOffset = halfWidth -((textBounds.right-textBounds.left)/2);
            heightOffset = height -2;
            canvas.drawTextOnPath(nextString, mPath, widthOffset, heightOffset+dragOffset, mPaint);

            if(dragOffset > 0) {
                int numOfpreviousToDraw = (int) Math.ceil(dragOffset/(textHeight + upperSpacing));
                for(int i = 0; i<numOfpreviousToDraw;i++) {
                    previousString = model.getObjectXIndexesAway((i+ EXTRA_DRAWS_MODEL_OFFSET)*-1).toString();
                    mPaint.getTextBounds(previousString, 0, previousString.length(), textBounds);
                    widthOffset = halfWidth -((textBounds.right-textBounds.left)/2);
                    heightOffset = 0- upperSpacing;
                    if(i >= 1) {
                        heightOffset -= (textHeight+upperSpacing) * (i);
                    }
                    canvas.drawTextOnPath(previousString, mPath, widthOffset, heightOffset+dragOffset,mPaint);
                }
            } else if(dragOffset < 0) {
                int numOfNextsToDraw = (int) Math.ceil(Math.abs(dragOffset) / (textHeight + upperSpacing));
                for(int i = 0; i<numOfNextsToDraw; i++) {
                    nextString = model.getObjectXIndexesAway(i+EXTRA_DRAWS_MODEL_OFFSET).toString();
                    mPaint.getTextBounds(nextString, 0, nextString.length(), textBounds);
                    widthOffset = halfWidth - ((textBounds.right-textBounds.left)/2);
                    heightOffset = height + textHeight + upperSpacing;
                    if(i >= 1) {
                        heightOffset += (textHeight+upperSpacing) * (i);
                    }
                    canvas.drawTextOnPath(nextString, mPath, widthOffset, heightOffset+dragOffset, mPaint);
                }
            }


            mPaint.setColor(getResources().getColor(R.color.red));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(height /30);
            canvas.drawRect(0, middleHeightOffset-textHeight-10, getWidth(), middleHeightOffset+10, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void onSelectedObjectChanged(Object selectedObject) {
        invalidate();
    }

    private Timer onReleasedTimer = null;
    private volatile boolean draggedAgain = false;
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(onReleasedTimer != null) {
                onReleasedTimer = new Timer("releaseTimer");
                onReleasedTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(!draggedAgain) {
                            setSelectedObjectAfterFinishedDragging();
                        }
                        onReleasedTimer = null;
                    }
                }, 250);
            }
        }
        return true;
    }

    private void setSelectedObjectAfterFinishedDragging() {
        int selectionIndex = Math.round(dragOffset/(textHeight + upperSpacing))*-1;
        model.setSelectedObject(model.getObjectXIndexesAway(selectionIndex));
        setDragOffset(0);
    }

    public Object getSelectedItem() {
        return model.getSelectedItem();
    }

    /**
     * Extends {@link GestureDetector.SimpleOnGestureListener} to provide custom gesture
     * processing.
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private AndroidScrollSpinner spinner;

        public GestureListener(AndroidScrollSpinner spinner) {
            this.spinner = spinner;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(!draggedAgain) {
                draggedAgain = true;
            }
            spinner.addToDragOffset(distanceX);
            invalidate();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }


    }
}
