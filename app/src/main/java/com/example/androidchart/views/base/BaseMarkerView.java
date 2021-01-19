package com.example.androidchart.views.base;

import android.content.Context;
import android.widget.TextView;

import com.example.androidchart.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class BaseMarkerView extends MarkerView {

    protected TextView markerViewContent;

    protected ValueFormatter valueFormatter;

    protected DecimalFormat format;

    public BaseMarkerView(Context context, ValueFormatter valueFormatter) {
        super(context, R.layout.bar_marker_view);
        markerViewContent = findViewById(R.id.markerViewContent);
        this.valueFormatter = valueFormatter;
        this.format = new DecimalFormat("###.0");
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String x = format.format(e.getX());
        String y = format.format(e.getY());
        String content = String.format("(%s; %s)", x, y);
        markerViewContent.setText(content);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        float x =  - (getWidth() / 2);
        float y = - getHeight();
        return new MPPointF(x, y);
    }
}
