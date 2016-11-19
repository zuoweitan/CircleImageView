package com.zuowei.circleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;

import static android.R.attr.radius;
import static android.R.attr.width;

/**
 * 项目名称：CircleImageView
 * 类描述：
 * 创建人：zuowei
 * 创建时间：16-11-14 上午10:35
 * 修改人：zuowei
 * 修改时间：16-11-14 上午10:35
 * 修改备注：
 */
public class CircleImageView extends ImageView {
    Path circlePath = new Path();
    Paint paint = new Paint();
    Paint layerPaint = new Paint();
    RectF layerRect;
    PorterDuffXfermode porterDuffXfermode;
    float radius;
    public CircleImageView(Context context) {
        super(context);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                removeOnLayoutChangeListener(this);
                layerRect.set(getPaddingLeft(),getPaddingTop(),getWidth() - getPaddingRight(),getHeight() - getPaddingBottom());
                Rect bounds = getDrawable().getBounds();
                int vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
                int vheight = getHeight() - getPaddingTop() - getPaddingBottom();
                float[] scaleParams = getScaleParams();
                radius = min(vwidth / 2f,vheight / 2f,bounds.centerX() * scaleParams[0],bounds.centerY() * scaleParams[1]);
                if (getScaleType() == ScaleType.MATRIX) {
                    setImageMatrix(getNewMatrix(bounds.left, bounds.top, bounds.right, bounds.bottom));//support matrix
                }
            }
        });

        layerRect = new RectF();

        layerPaint.setXfermode(null);

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    private float[] getScaleParams() {
        Matrix imageMatrix = getImageMatrix();
        float[] martixValues = new float[9];
        imageMatrix.getValues(martixValues);
        return new float[]{martixValues[0],martixValues[4]};
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(layerRect, layerPaint,Canvas.CLIP_SAVE_FLAG);//clear background
            circlePath.addCircle(layerRect.centerX(),layerRect.centerY(),radius, Path.Direction.CCW);
            canvas.clipPath(circlePath);
            circlePath.reset();
            circlePath.addCircle(layerRect.centerX(),layerRect.centerY(),radius-1f, Path.Direction.CCW);//Antialiasing
                canvas.save(Canvas.ALL_SAVE_FLAG);
                    paint.setXfermode(porterDuffXfermode);
                    paint.setAntiAlias(true);
                    super.onDraw(canvas);
                    canvas.drawPath(circlePath,paint);
                canvas.restore();
        canvas.restore();
    }

    private Matrix getNewMatrix(int left,int top,int right,int bottom) {
        float scale;
        float dx = 0;
        float dy = 0;
        int vwidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int vheight = getHeight() - getPaddingTop() - getPaddingBottom();
        int width = right - left;
        int height = bottom - top;

        Matrix matrix = new Matrix();
        matrix.set(null);

        if (width * vheight > vwidth * height) {
            scale = vheight / (float) height;
            dx = (vwidth - width * scale) * 0.5f;
        } else {
            scale = vwidth / (float) height;
            dy = (vheight - height * scale) * 0.5f;
        }

        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f) + left, (int) (dy + 0.5f) + top);

        return matrix;
    }

    private float min_r(float... values){
        if (values.length == 1){
            return values[0];
        }
        float [] newValues = Arrays.copyOf(values,values.length - 1);
        newValues[newValues.length - 1] = Math.min(values[values.length - 1],values[values.length - 2]);
        return min_r(newValues);
    }

    private float min(float... values){
        if (values.length <= 0){
            throw new IndexOutOfBoundsException("values is empty");
        }

        float min = values[0];
        for (float value : values){
            min = Math.min(value,min);
        }

        return min;
    }
}
