package com.app.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.app.graph.bargraph.BarChartSurfaceView;

public class BarGraphActivity extends AppCompatActivity {

    private LinearLayout mBarChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        mBarChart = (LinearLayout) findViewById(R.id.linear_bar_chart);
        BarChartSurfaceView surfaceView = new BarChartSurfaceView(this);
        mBarChart.addView(surfaceView);

        surfaceView.setBackgroundColor(Color.parseColor("#ff00ff"));
    }
}
