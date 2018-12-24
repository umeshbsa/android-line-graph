package com.app.graph.bargraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BarChartSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder = null;
    private Thread thread = null;
    private Canvas canvas = null;
    private ScaleGestureDetector SGD;
    private Context context;
    private boolean isSingleTouch;
    private float width, height = 0;
    private float scale = 1f;
    private float minScale = 1f;
    private float maxScale = 5f;
    int left, top, right, bottom;


    public BarChartSurfaceView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BarChartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BarChartSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        setFocusable(true);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        setZOrderOnTop(true);

        setOnTouchListener(new MyTouchListeners());
        SGD = new ScaleGestureDetector(context, new ScaleListener());
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (width == 0 && height == 0) {
            width = BarChartSurfaceView.this.getWidth();
            height = BarChartSurfaceView.this.getHeight();
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

    }

    private class MyTouchListeners implements View.OnTouchListener {

        float dX, dY;

        MyTouchListeners() {
            super();
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            SGD.onTouchEvent(event);
            if (event.getPointerCount() > 1) {
                isSingleTouch = false;
            } else {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    isSingleTouch = true;
                }
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = BarChartSurfaceView.this.getX() - event.getRawX();
                    dY = BarChartSurfaceView.this.getY() - event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (isSingleTouch) {
                        BarChartSurfaceView.this.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        checkDimension(BarChartSurfaceView.this);
                    }
                    break;
                default:
                    return true;
            }
            return true;
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.e("onGlobalLayout: ", scale + " " + width + " " + height);
            scale *= detector.getScaleFactor();
            scale = Math.max(minScale, Math.min(scale, maxScale));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (width * scale), (int) (height * scale));
            BarChartSurfaceView.this.setLayoutParams(params);
            checkDimension(BarChartSurfaceView.this);
            return true;
        }
    }

    private void checkDimension(View vi) {
        if (vi.getX() > left) {
            vi.animate()
                    .x(left)
                    .y(vi.getY())
                    .setDuration(0)
                    .start();
        }

        if ((vi.getWidth() + vi.getX()) < right) {
            vi.animate()
                    .x(right - vi.getWidth())
                    .y(vi.getY())
                    .setDuration(0)
                    .start();
        }

        if (vi.getY() > top) {
            vi.animate()
                    .x(vi.getX())
                    .y(top)
                    .setDuration(0)
                    .start();
        }

        if ((vi.getHeight() + vi.getY()) < bottom) {
            vi.animate()
                    .x(vi.getX())
                    .y(bottom - vi.getHeight())
                    .setDuration(0)
                    .start();
        }

        run();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void run() {
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(backgroundColor);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(100 + scale, 100 + scale, 40 + scale, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);

    }


    private int backgroundColor = -1;

    public int getBackgroundColor() {
        if (this.backgroundColor == -1) {
            this.backgroundColor = Color.parseColor("#ffffff");
        }
        return this.backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
