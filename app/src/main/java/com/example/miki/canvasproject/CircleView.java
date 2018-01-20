package com.example.miki.canvasproject;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Miki on 1/20/2018.
 */

public class CircleView extends View {

    private Paint mTextPaint;
    private float mTextHeight;
    private Paint mPiePaint;
    private Paint mShadowPaint;

    public CircleView(Context context) {
        super(context);
        init();
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
        mPiePaint.setStyle(Paint.Style.FILL);
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

        // Draw the shadow
//        canvas.drawOval(
//                mShadowBounds,
//                mShadowPaint
//        );
        canvas.drawArc(getWidth()/2, getHeight()/2, 50, mPiePaint);

        // Draw the label text
//        canvas.drawText(mData.get(mCurrentItem).mLabel, mTextX, mTextY, mTextPaint);

        // Draw the pie slices
//        for (int i = 0; i < mData.size(); ++i) {
//            Item it = mData.get(i);
//            mPiePaint.setShader(it.mShader);
//            canvas.drawArc(mBounds,
//                    360 - it.mEndAngle,
//                    it.mEndAngle - it.mStartAngle,
//                    true, mPiePaint);
//        }

        // Draw the pointer
//        canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
//        canvas.drawCircle(mPointerX, mPointerY, mPointerSize, mTextPaint);
    }
}
