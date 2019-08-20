package com.laka.weight;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @Author:summer
 * @Date:2019/8/20
 * @Description:
 */
public class BezierEvaluator implements TypeEvaluator {

    private PointF mControlPoint;

    public BezierEvaluator(PointF controlPoint) {
        this.mControlPoint = controlPoint;
    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //return BezierUtil.CalculateBezierPointForQuadratic(t, startValue, mControlPoint, endValue);
        return null;
    }
}
