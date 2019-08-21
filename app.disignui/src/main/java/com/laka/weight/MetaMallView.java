package com.laka.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author:summer
 * @Date:2019/8/21
 * @Description:
 */
public class MetaMallView extends View {

    private float mCircleTwoX;
    private float mCircleTwoY;
    private float mCircleOneX;
    private float mCircleOneY;
    private float mCircleOneRadius;
    private Paint mPaint;
    private float mRadiusNormal = 10;
    private Path mPath = new Path();

    public MetaMallView(Context context) {
        super(context);
        init();
    }

    public MetaMallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MetaMallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);

        mCircleOneX = 200;
        mCircleOneY = 200;
        mCircleTwoX = 800;
        mCircleTwoY = 500;
        mCircleOneRadius = 100;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        metaBallVersion(canvas);
    }

    private void metaBallVersion(Canvas canvas) {
        float x = mCircleTwoX;
        float y = mCircleTwoY;
        float startX = mCircleOneX;
        float startY = mCircleOneY;

        float dx = x - startX;
        float dy = y - startY;
        //反三角函数，求目标角度对应的弧度，然后再根据得到的弧度进行三角函数的运算
        double a = Math.atan(dx / dy);
        float offsetX = (float) (mCircleOneRadius * Math.cos(a));
        float offsetY = (float) (mCircleOneRadius * Math.sin(a));

        float x1 = startX + offsetX;
        float y1 = startY - offsetY;

        float x2 = x + offsetX;
        float y2 = y - offsetY;

        float x3 = x - offsetX;
        float y3 = y + offsetY;

        float x4 = startX - offsetX;
        float y4 = startY + offsetY;

        float controlX = (startX + x) / 2;
        float controlY = (startY + y) / 2;

        mPath.reset();
        mPath.moveTo(x1, y1);
        mPath.quadTo(controlX, controlY, x2, y2);
        mPath.lineTo(x3, y3);
        mPath.quadTo(controlX, controlY, x4, y4);
        mPath.lineTo(x1, y1);


        canvas.drawCircle(mCircleOneX, mCircleOneY, mCircleOneRadius, mPaint);
        canvas.drawCircle(mCircleTwoX, mCircleTwoY, mCircleOneRadius, mPaint);

        canvas.drawLine(mCircleOneX, mCircleOneY, mCircleTwoX, mCircleTwoY, mPaint);
        canvas.drawLine(0, mCircleOneY, mCircleOneX + mRadiusNormal + 400, mCircleOneY, mPaint);
        canvas.drawLine(mCircleOneX, 0, mCircleOneX, mCircleOneY + mRadiusNormal + 50, mPaint);
        canvas.drawLine(x1, y1, x2, y2, mPaint);
        canvas.drawLine(x3, y3, x4, y4, mPaint);
        canvas.drawCircle(controlX, controlY, 5, mPaint);
        canvas.drawLine(mCircleTwoX, mCircleTwoY, mCircleTwoX, 0, mPaint);
        canvas.drawLine(x1, y1, x1, mCircleOneY, mPaint);

        canvas.drawPath(mPath, mPaint);
    }

}
