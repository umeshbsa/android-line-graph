package com.app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.app.graph.line.LineChart;

public class LineChartActivity extends AppCompatActivity {

    private LineChart mLineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        mLineChart.setMinPoint(105);
        mLineChart.setMaxPoint(115);
    }
}
