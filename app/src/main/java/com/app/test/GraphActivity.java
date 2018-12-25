package com.app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.graph.line.LineGraph;

public class GraphActivity extends AppCompatActivity {

    private LineGraph mLineChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mLineChart = (LineGraph) findViewById(R.id.line_graph);
        mLineChart.setMinPoint(105);
        mLineChart.setMaxPoint(115);
    }
}
