package com.app.graph.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.util.Random;

public class LineChart extends View {

    private int backgroundColor = Color.LTGRAY;
    private float divider;

    /**
     * Change this value to show another line graph points
     */
    private float MIN_VALUE = 100;
    private float MAX_VALUE = 110;
    private float W;
    private float H;
    private Canvas canvas;
    private Paint mXYLinePaint = new Paint();
    private Paint mXAxisPaint = new Paint();
    private Paint mYAxisPaint = new Paint();
    private Paint mXAxisTextPaint = new Paint();
    private Paint mYAxisTextPaint = new Paint();
    private Paint mLinePaint = new Paint();


    public LineChart(Context context) {
        super(context);
        init();
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mXYLinePaint.setColor(Color.BLACK);
        mXYLinePaint.setStrokeWidth(1);
        mXYLinePaint.setAntiAlias(true);
        mXYLinePaint.setDither(true);

        mXAxisPaint.setColor(Color.RED);
        mXAxisPaint.setStrokeWidth(2);
        mXAxisPaint.setAntiAlias(true);
        mXAxisPaint.setDither(true);

        mYAxisPaint.setColor(Color.RED);
        mYAxisPaint.setStrokeWidth(2);
        mYAxisPaint.setAntiAlias(true);
        mYAxisPaint.setDither(true);

        mXAxisTextPaint.setColor(Color.RED);
        mXAxisTextPaint.setAntiAlias(true);
        mXAxisTextPaint.setStrokeWidth(2);
        mXAxisTextPaint.setDither(true);
        mXAxisTextPaint.setStyle(Paint.Style.FILL);

        mYAxisTextPaint.setColor(Color.BLUE);
        mYAxisTextPaint.setAntiAlias(true);
        mYAxisTextPaint.setStrokeWidth(2);
        mYAxisTextPaint.setDither(true);
        mYAxisTextPaint.setStyle(Paint.Style.FILL);

        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setDither(true);
        mLinePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawColor(backgroundColor);
        W = getWidth();
        H = getHeight();
        divider = MAX_VALUE - MIN_VALUE;
        drawXYLine();
        drawXAxis();
        drawYAxis();
        drawXAxisText();
        drawYAxisText();
        drawLineWithTwoPoint();
    }

    /**
     * Draw x and y axis line with each rect
     */
    private void drawXYLine() {
        // Draw equal divider
        if (divider != 0) {
            float EACH_SECTION_H = H / divider;
            float EACH_SECTION_X = W / divider;

            // Draw horizontal y-axis
            float Y = 0;
            for (int i = 0; i < divider; i++) {
                Y = Y + EACH_SECTION_H;
                canvas.drawLine(0, Y, W - EACH_SECTION_X, Y, mXYLinePaint);
            }
            // Draw vertical x-axis
            float X = 0;
            for (int i = 0; i < divider; i++) {
                X = X + EACH_SECTION_X;
                canvas.drawLine(X, 0, X, H - EACH_SECTION_H, mXYLinePaint);
            }
        }
    }


    /**
     * Draw x axis line with text line
     */
    private void drawXAxis() {
        // draw x-axis
        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;
        float H_X = H - EACH_SECTION_H;
        canvas.drawLine(0, H_X, W - EACH_SECTION_X, H_X, mXAxisPaint);
    }


    /**
     * Draw y axis line with text line
     */
    private void drawYAxis() {
        // draw y-axis
        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;

        float W_Y = W - EACH_SECTION_X;
        canvas.drawLine(W_Y, 0, W_Y, H - EACH_SECTION_H, mYAxisPaint);
    }

    /**
     * Draw x axis text 15.00 14.00 ...
     */
    private void drawXAxisText() {
        DecimalFormat df = new DecimalFormat("#.00");
        float EACH_SECTION_H = H / divider;
        float W_GAP = W / divider;
        float f = divider;
        float X = 0;
        for (int i = 0; i < divider; i++) {
            canvas.drawText(df.format(f), X, H - EACH_SECTION_H + 20, mXAxisTextPaint);
            f = f - 1;
            X = X + W_GAP;
        }
    }

    /**
     * Draw y axis text 100.00 101.00
     */
    private void drawYAxisText() {
        DecimalFormat df = new DecimalFormat("#.00");
        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;
        float INCREAMENT = (MAX_VALUE - MIN_VALUE) / divider;
        float Z = MIN_VALUE;
        float S = H - EACH_SECTION_H;
        canvas.drawText(df.format(Z), W - EACH_SECTION_X + 3, S + 10, mXAxisTextPaint);
        for (int i = 0; i < divider; i++) {
            Z = Z + INCREAMENT;
            S = S - EACH_SECTION_H;
            canvas.drawText(df.format(Z), W - EACH_SECTION_X + 3, S + 10, mXAxisTextPaint);
        }
    }

    /**
     * Draw line by line with adjacent
     */
    private void drawLineWithTwoPoint() {
        float EACH_SECTION_H = H / divider;
        float W_GAP = W / divider;
        float STARTX = 0;
        float STARTY = 0;
        float ENDX = 0;
        float ENDY;
        for (int i = 0; i < divider - 1; i++) {
            Random rand = new Random();
            float random = EACH_SECTION_H + rand.nextFloat() * (H - 2 * EACH_SECTION_H);
            ENDX = ENDX + W_GAP;
            ENDY = random;
            canvas.drawLine(STARTX, STARTY, ENDX, ENDY, mXAxisPaint);
            STARTX = ENDX;
            STARTY = ENDY;
        }
    }

    public void setMaxPoint(float max) {
        MAX_VALUE = max;
    }

    public void setMinPoint(float min) {
        MIN_VALUE = min;
    }
}
