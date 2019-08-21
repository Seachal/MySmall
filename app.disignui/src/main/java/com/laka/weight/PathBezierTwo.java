package com.laka.weight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @Author:summer
 * @Date:2019/8/20
 * @Description:
 */
public class PathBezierTwo extends View {

    private Paint mPathPaint;
    private Paint mCirclePaint;

    private int mStartPointX;
    private int mStartPointY;
    private int mEndPointX;
    private int mEndPointY;

    private int mMovePointX;
    private int mMovePointY;

    private int mControlPointX;
    private int mControlPointY;

    private Path mPath;

    public PathBezierTwo(Context context) {
        super(context);
    }

    public PathBezierTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(5);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPointX = 100;
        mStartPointY = 100;
        mEndPointX = 600;
        mEndPointY = 600;
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;
        mControlPointX = 500;
        mControlPointY = 0;
        mPath = new Path();
    }

    public PathBezierTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

//        mPath.moveTo(mStartPointX, mStartPointY);
//        mPath.quadTo(mControlPointX, mControlPointY, mEndPointX, mEndPointY);

        Path path1 = new Path();
        path1.moveTo(100, 100);
        path1.cubicTo(200, 50, 300, 200, 100, 400);
        mPath.addPath(path1);

        Path path2 = new Path();
        path2.moveTo(100, 400);
        path2.cubicTo(0, 700, 150, 1000, 500, 600);
        mPath.addPath(path2);

        canvas.drawPath(mPath, mPathPaint);
    }
}