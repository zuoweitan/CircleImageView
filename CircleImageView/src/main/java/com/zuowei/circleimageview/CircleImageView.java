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
                layerRect.set(0,0,getWidth(),getHeight());
                radius = Math.min(getWidth()/2,getHeight()/2);
                Rect bounds = getDrawable().getBounds();
                setImageMatrix(getNewMatrix(bounds.left,bounds.top,bounds.right,bounds.bottom));//support matrix
            }
        });

        layerRect = new RectF();

        layerPaint.setXfermode(null);

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
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
            circlePath.addCircle(layerRect.centerX(),layerRect.centerY(),radius-1, Path.Direction.CCW);//Antialiasing
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
        int width = right - left;
        int height = bottom - top;

        Matrix matrix = new Matrix();
        matrix.set(null);

        if (width * getHeight() > getWidth() * height) {
            scale = getHeight() / (float) height;
            dx = (getWidth() - width * scale) * 0.5f;
        } else {
            scale = getWidth() / (float) height;
            dy = (getHeight() - height * scale) * 0.5f;
        }

        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f) + left, (int) (dy + 0.5f) + top);

        return matrix;
    }
}
