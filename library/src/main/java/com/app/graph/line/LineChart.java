package com.app.graph.line;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LineChart extends View {

    private Context context;

    public LineChart(Context context) {
        super(context);
        init();
        this.context = context;
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        this.context = context;
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        this.context = context;
    }


    private int backgroundColor = Color.LTGRAY;
    private float divider = 10;
    private int xDivider = 10;
    private int yDivider = 10;

    private float W;
    private float H;

    Paint X_PAINT = new Paint();
    Paint POINT_PAINT = new Paint();
    Paint Y_AXIS_PAINT = new Paint();

    private void init() {
        X_PAINT.setColor(Color.BLACK);
        Y_AXIS_PAINT.setColor(Color.RED);
        Y_AXIS_PAINT.setStrokeWidth(3);

        POINT_PAINT.setColor(Color.BLACK);
        POINT_PAINT.setAntiAlias(true);
        POINT_PAINT.setStrokeWidth(2);
    }


    private Canvas canvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawColor(backgroundColor);
        W = canvas.getWidth();
        H = canvas.getHeight();
        divider = (MAX_VALUE + 1) - MIN_VALUE;
        drawLine();
        drawYAxis();
        drawXAxis();
        setData();
        drawAllPoints();

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();

                        handler.postDelayed(this, 2000);
                    }
                }, 2000);

            }
        });
    }

    Handler handler = new Handler();
    List<Float> plotPoint = new ArrayList<>();


    float PREV;
    float NEXT;

    private void createPoint() {

    }

    private void drawAllPoints() {
        float GAP_H = H / divider;
        float GAP_W = W / divider;
        Random rand = new Random();
        int n = rand.nextInt(Integer.valueOf((int) 30)) + (int) 1;
        String key = keyOnly.get(n);
        Float point = data.get(key);
        Log.i("DDDDDDDDD", "KEY : " + key + " : " + point);
        plotPoint.add(point);

        for (int i = 0; i < plotPoint.size(); i++) {
            PREV = plotPoint.get(i);
            W = W - 2 * GAP_W;
            canvas.drawLine(GAP_W, PREV, GAP_W, PREV, POINT_PAINT);
        }
    }

    private void drawXAxis() {
        // draw x-axis
        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;
        float H_X = H - EACH_SECTION_H;
        canvas.drawLine(0, H_X, W - EACH_SECTION_X, H_X, Y_AXIS_PAINT);
    }

    private float MIN_VALUE = 100;
    private float MAX_VALUE = 110;

    private float TIME = 30;


    private Map<String, Float> data = new LinkedHashMap<>();

    private List<String> keyOnly = new ArrayList<>();

    private void setData() {
        DecimalFormat df = new DecimalFormat("#.00");
        float VALUE_H = H / TIME;
        float PRICE_KEY = MIN_VALUE;
        float VALUE_PRICE_VALUE = H - VALUE_H;
        float IN = (MAX_VALUE - MIN_VALUE) / TIME;

        data.clear();
        for (int i = 0; i < TIME; i++) {
            String angleFormated = df.format(PRICE_KEY);
            data.put(angleFormated, VALUE_PRICE_VALUE);
            keyOnly.add(angleFormated);
            PRICE_KEY = PRICE_KEY + IN;
            VALUE_PRICE_VALUE = VALUE_PRICE_VALUE - VALUE_H;
        }

        mPricePaint.setColor(Color.BLACK);
        mPricePaint.setAntiAlias(true);
        mPricePaint.setTextSize(14);
        mPricePaint.setStyle(Paint.Style.FILL);

        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;
        float INCREAMENT = (MAX_VALUE - MIN_VALUE) / divider;
        float Z = MIN_VALUE;
        float S = H - EACH_SECTION_H;
        canvas.drawText(df.format(Z), W - EACH_SECTION_X + 3, S + 10, mPricePaint);
        for (int i = 0; i < divider; i++) {
            Z = Z + INCREAMENT;
            S = S - EACH_SECTION_H;
            canvas.drawText(df.format(Z), W - EACH_SECTION_X + 3, S + 10, mPricePaint);
        }
    }

    Paint mPricePaint = new Paint();

    private void drawYAxis() {
        // draw y-axis
        float EACH_SECTION_H = H / divider;
        float EACH_SECTION_X = W / divider;

        float W_Y = W - EACH_SECTION_X;
        canvas.drawLine(W_Y, 0, W_Y, H - EACH_SECTION_H, Y_AXIS_PAINT);

    }

    private void drawLine() {
        // Draw equal divider
        if (divider != 0) {
            float EACH_SECTION_H = H / divider;
            float EACH_SECTION_X = W / divider;

            // Draw horizontal y-axis
            float Y = 0;
            for (int i = 0; i < divider; i++) {
                Y = Y + EACH_SECTION_H;
                canvas.drawLine(0, Y, W - EACH_SECTION_X, Y, X_PAINT);
            }
            // Draw vertical x-axis
            float X = 0;
            for (int i = 0; i < divider; i++) {
                X = X + EACH_SECTION_X;
                canvas.drawLine(X, 0, X, H - EACH_SECTION_H, X_PAINT);
            }
        } else {
        }
    }
}
