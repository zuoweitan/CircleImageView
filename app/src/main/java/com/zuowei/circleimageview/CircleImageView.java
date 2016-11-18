package com.zuowei.circleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

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
    RectF rectF = new RectF();
    RectF layerRect;
    PorterDuffXfermode porterDuffXfermode;
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

    int radius;

    private void init() {
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                removeOnLayoutChangeListener(this);
                layerRect.set(0,0,getWidth(),getHeight());
                radius = Math.min(getWidth()/2,getHeight()/2);

            }
        });

        rectF.set(getWidth() / 2 - radius,getHeight() / 2 - radius ,
                getWidth() / 2 + radius,getHeight() / 2 + radius);

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
}
