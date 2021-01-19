package com.example.androidchart.views.utils;

import android.content.Context;

import com.example.androidchart.R;
import com.example.androidchart.utils.ThemeUtils;
import com.example.androidchart.views.base.BaseMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class ChartFactory {


    public LineChart getLineChart(Context context) {
        int colorOnSurface = ThemeUtils.getThemeColor(context, R.attr.colorOnSurface);

        LineChart lineChart = new LineChart(context);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraBottomOffset(16f);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(colorOnSurface);
        xAxis.setAxisLineColor(colorOnSurface);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawLabels(false);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(100);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextSize(14f);
        yAxisLeft.setTextColor(colorOnSurface);
        yAxisLeft.setAxisLineColor(colorOnSurface);

        return lineChart;
    }

    public BarChart getBarChart(Context context) {
        int colorOnSurface = ThemeUtils.getThemeColor(context, R.attr.colorOnSurface);

        BarChart barChart = new BarChart(context);
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setExtraBottomOffset(16);
        barChart.setScaleEnabled(false);

        BaseMarkerView markerView = new BaseMarkerView(context, null);
        markerView.setChartView(barChart);
        barChart.setMarker(markerView);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(colorOnSurface);
        xAxis.setAxisLineColor(colorOnSurface);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setTextSize(14f);
        barChart.getAxisLeft().setAxisLineColor(colorOnSurface);
        barChart.getAxisLeft().setTextColor(colorOnSurface);

        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawLabels(false);

        return barChart;
    }



}
