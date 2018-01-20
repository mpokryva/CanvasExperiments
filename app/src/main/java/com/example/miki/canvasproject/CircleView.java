package com.example.miki.canvasproject;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by Miki on 1/20/2018.
 */

public class CircleView extends View {

    private Paint mTextPaint;
    private float mTextHeight;
    private Paint mPiePaint;
    private Paint mShadowPaint;
    private float mScaleFactor;
    private ScaleGestureDetector mScaleDetector;
    private int numCircles;
    private final int NUM_CIRCLES = 5;
    private Random rand;
    private ArrayList<Circle> circles;
    private final int CENTER_RADIUS = 150;


    public CircleView(Context context) {
        super(context);
        init();
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rand = new Random();
        circles = new ArrayList<>();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        circles.add(new Circle(width/2, height/2, 150, true));
        for (int i = 0; i < NUM_CIRCLES; i++) {
            float x = 1;
            float y = 2;
            float radius = rand.nextFloat() * (Circle.MAX_RADIUS - Circle.MIN_RADIUS) + Circle.MIN_RADIUS;
            circles.add(new Circle(x, y, radius));
        }
//        for (int i = 0; i < circles.size(); i++) {
//            Circle[] c = new Circle[circles.size()];
//            circles.get(i).computePosition1(circles.toArray(c), circles.get(0).getX(), circles.get(0).getY());
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        recompute();
    }

    private void recompute() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((MainActivity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        circles.set(0, new Circle(width/2, height/2, CENTER_RADIUS, true));
        for (int i = 0; i < circles.size(); i++) {
            Circle[] c = new Circle[circles.size()];
            circles.get(i).computePosition1(circles.toArray(c), circles.get(0).getX(), circles.get(0).getY());
        }
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);
        if (mTextHeight == 0) {
            mTextHeight = mTextPaint.getTextSize();
        } else {
            mTextPaint.setTextSize(mTextHeight);
        }

        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.STROKE);
        mPiePaint.setStrokeWidth(5);
        mPiePaint.setTextSize(mTextHeight);

        mShadowPaint = new Paint(0);
        mShadowPaint.setColor(0xff101010);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

    }



//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        // Account for padding
//        float xpad = (float)(getPaddingLeft() + getPaddingRight());
//        float ypad = (float)(getPaddingTop() + getPaddingBottom());
//
//        // Account for the label
//        if (mShowText) xpad += mTextWidth;
//
//        float ww = (float)w - xpad;
//        float hh = (float)h - ypad;
//
//        // Figure out how big we can make the pie.
//        float diameter = Math.min(ww, hh);
//    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        circles.get(0).setX(getWidth()/2);
        circles.get(0).setY(getHeight()/2);
        recompute();
        for (int i = 0; i < circles.size(); i++) {
            int multiplier = 100;
//            float x = rand.nextFloat() * multiplier;
//            float y = rand.nextFloat() * multiplier;
            canvas.drawCircle(circles.get(i).getX(), circles.get(i).getY(), circles.get(i).getRadius(), mPiePaint);
        }
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            invalidate();
            return true;
        }
    }


}
