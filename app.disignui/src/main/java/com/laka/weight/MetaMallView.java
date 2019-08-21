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
    private float mRadiusNormal;
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
        mCircleOneRadius = 200;
        mRadiusNormal = 200;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //metaBallVersion1(canvas);
        metaBallVersion2(canvas);
    }

    /**
     * 切线拟合，对于小的圆，两圆圆心连线中点作为控制点，连接两圆上下切线，与圆的切点作为贝塞尔曲线的起始点
     * 以此来绘制贝塞尔曲线，并将画笔填充模式设置为全填充，即可实现QQ拖拽删除小红点的绘制了，但是对于大圆来说
     * 这样拟合的切换并不完美
     * */
    private void metaBallVersion1(Canvas canvas) {
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


    /**
     * 圆的切线拟合，通过三角函数计算合适的贝塞尔的起始点，控制点仍然是两圆圆点连线的中点，
     * 因为内赛尔曲线与圆相切，且起始点与控制点的连线与贝塞尔曲线起始点方向也是相切的，所以起始点的连线与圆的相切的
     * 再通过绘制辅助线，使用 Math 类里面的三角函数与反三角函数，即可推断出四个与圆相切的贝塞尔曲线的起始点坐标。
     * */
    private void metaBallVersion2(Canvas canvas) {
        float x = mCircleTwoX;
        float y = mCircleTwoY;
        float startX = mCircleOneX;
        float startY = mCircleOneY;
        float controlX = (startX + x) / 2;
        float controlY = (startY + y) / 2;

        float distance = (float) Math.sqrt((controlX - startX) * (controlX - startX) + (controlY - startY) * (controlY - startY));
        double a = Math.acos(mRadiusNormal / distance);

        double b = Math.acos((controlX - startX) / distance);
        float offsetX1 = (float) (mRadiusNormal * Math.cos(a - b));
        float offsetY1 = (float) (mRadiusNormal * Math.sin(a - b));
        float tanX1 = startX + offsetX1;
        float tanY1 = startY - offsetY1;

        double c = Math.acos((controlY - startY) / distance);
        float offsetX2 = (float) (mRadiusNormal * Math.sin(a - c));
        float offsetY2 = (float) (mRadiusNormal * Math.cos(a - c));
        float tanX2 = startX - offsetX2;
        float tanY2 = startY + offsetY2;

        double d = Math.acos((y - controlY) / distance);
        float offsetX3 = (float) (mRadiusNormal * Math.sin(a - d));
        float offsetY3 = (float) (mRadiusNormal * Math.cos(a - d));
        float tanX3 = x + offsetX3;
        float tanY3 = y - offsetY3;

        double e = Math.acos((x - controlX) / distance);
        float offsetX4 = (float) (mRadiusNormal * Math.cos(a - e));
        float offsetY4 = (float) (mRadiusNormal * Math.sin(a - e));
        float tanX4 = x - offsetX4;
        float tanY4 = y + offsetY4;

        mPath.reset();
        mPath.moveTo(tanX1, tanY1);
        mPath.quadTo(controlX, controlY, tanX3, tanY3);
        mPath.lineTo(tanX4, tanY4);
        mPath.quadTo(controlX, controlY, tanX2, tanY2);
        canvas.drawPath(mPath, mPaint);

        canvas.drawCircle(mCircleOneX, mCircleOneY, mRadiusNormal, mPaint);
        canvas.drawCircle(mCircleTwoX, mCircleTwoY, mRadiusNormal, mPaint);

        canvas.drawCircle(tanX1, tanY1, 5, mPaint);
        canvas.drawCircle(tanX2, tanY2, 5, mPaint);
        canvas.drawCircle(tanX3, tanY3, 5, mPaint);
        canvas.drawCircle(tanX4, tanY4, 5, mPaint);
        canvas.drawLine(mCircleOneX, mCircleOneY, mCircleTwoX, mCircleTwoY, mPaint);
        canvas.drawLine(0, mCircleOneY, mCircleOneX + mRadiusNormal + 400, mCircleOneY, mPaint);
        canvas.drawLine(mCircleOneX, 0, mCircleOneX, mCircleOneY + mRadiusNormal + 50, mPaint);
        canvas.drawLine(mCircleTwoX, mCircleTwoY, mCircleTwoX, 0, mPaint);
        canvas.drawCircle(controlX, controlY, 5, mPaint);
        canvas.drawLine(startX, startY, tanX1, tanY1, mPaint);
        canvas.drawLine(tanX1, tanY1, controlX, controlY, mPaint);
    }

}
