package com.example.androidchart.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.androidchart.core.MainFragmentViewModel;
import com.example.androidchart.databinding.FrgMainBinding;
import com.example.androidchart.injects.base.BaseFragment;
import com.example.androidchart.views.utils.ChartFactory;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EFragment
public class MainFragment extends BaseFragment {

    protected FrgMainBinding binding;

    protected MainFragmentViewModel viewModel;

    protected LineChart lineChart;
    protected BarChart barChart;

    protected int selectedIndex = -1;

    protected SeekBar.OnSeekBarChangeListener onSeekBarChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (barChart == null) {
                initBarChartData();
            }

            if (selectedIndex > -1) {
                updateBarChartData(selectedIndex, seekBar.getProgress());
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    protected OnChartValueSelectedListener onChartValueSelected = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {

            selectedIndex = barChart.getData().getDataSetByIndex(0).getEntryIndex((BarEntry) e);

            binding.seekBar.setProgress((int) e.getY(), true);
        }

        @Override
        public void onNothingSelected() {
            selectedIndex = -1;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMainBinding.inflate(inflater, container, false);

        insertLineChart();
        initLineChartData();

        binding.seekBar.setOnSeekBarChangeListener(onSeekBarChange);
        binding.seekBar.setProgress(47);

        return binding.getRoot();
    }

    public void insertLineChart() {
        ChartFactory chartFactory = new ChartFactory();
        lineChart = chartFactory.getLineChart(requireContext());

        ConstraintLayout parentLayout = (ConstraintLayout) binding.getRoot();
        lineChart.setId(parentLayout.getChildCount());

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, 0);
        layoutParams.setMargins(0, 0, 0, 0);
        parentLayout.addView(lineChart, 0, layoutParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parentLayout);

        constraintSet.connect(lineChart.getId(), ConstraintSet.TOP, binding.topGuideline.getId(), ConstraintSet.TOP);
        constraintSet.connect(lineChart.getId(), ConstraintSet.START, binding.leftGuideline.getId(), ConstraintSet.START);
        constraintSet.connect(lineChart.getId(), ConstraintSet.END, binding.rightGuideline.getId(), ConstraintSet.END);

        constraintSet.applyTo(parentLayout);
    }

    public void insertBarChart() {
        ChartFactory chartFactory = new ChartFactory();
        barChart = chartFactory.getBarChart(requireContext());
        barChart.setOnChartValueSelectedListener(onChartValueSelected);

        ConstraintLayout parentLayout = (ConstraintLayout) binding.getRoot();
        barChart.setId(parentLayout.getChildCount());

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, 0);
        layoutParams.setMargins(0, 0, 0, 0);
        parentLayout.addView(barChart, parentLayout.getChildCount() - 1, layoutParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parentLayout);

        constraintSet.connect(lineChart.getId(), ConstraintSet.BOTTOM, barChart.getId(), ConstraintSet.TOP);

        constraintSet.connect(barChart.getId(), ConstraintSet.TOP, lineChart.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(barChart.getId(), ConstraintSet.START, binding.leftGuideline.getId(), ConstraintSet.START);
        constraintSet.connect(barChart.getId(), ConstraintSet.END, binding.rightGuideline.getId(), ConstraintSet.END);
        constraintSet.connect(barChart.getId(), ConstraintSet.BOTTOM, binding.seekBar.getId(), ConstraintSet.TOP);

        constraintSet.connect(binding.seekBar.getId(), ConstraintSet.TOP, barChart.getId(), ConstraintSet.BOTTOM);

        constraintSet.applyTo(parentLayout);

    }

    public void initLineChartData() {

        List<Entry> entries = new ArrayList<>();

        int[] ys = {12, 18, 34, 47, 61, 79};

        for (int i = 0; i < 10; i++) {
            float value = (float) (Math.random() * 81) + 10;
            entries.add(new Entry(i, value));
        }

        LineDataSet set;

        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set.setValues(entries);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set = new LineDataSet(entries, "Data Set 1");
            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set.setDrawValues(false);
            set.setDrawCircles(false);
            set.setLineWidth(4f);
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            List<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            LineData data = new LineData(dataSets);

            lineChart.setData(data);
        }

        lineChart.invalidate();
        lineChart.animateX(1500);
    }

    public void initBarChartData() {

        insertBarChart();

        List<BarEntry> entries = new ArrayList<>();
        int[] ys = {12, 18, 34, 47, 61, 79};

        for (int i = 0; i < 6; i++) {
            entries.add(new BarEntry(i, ys[i]));
        }

        BarDataSet set;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(entries, "Data Set 1");
            set.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set.setDrawValues(false);

            List<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.5f);

            barChart.setData(data);
            barChart.setFitBars(true);
        }

        barChart.invalidate();

        barChart.animateY(1500);

    }

    public void updateBarChartData(int index, int newY) {
        BarDataSet dataSet = (BarDataSet) barChart.getBarData().getDataSetByIndex(0);
        BarEntry entry = dataSet.getEntryForIndex(index);
        entry.setY(newY);
        barChart.getBarData().notifyDataChanged();
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }

    @Override
    public void initObservers() {

    }

    @Override
    public void onDestroyView() {
        barChart.setOnChartValueSelectedListener(null);
        binding.seekBar.setOnSeekBarChangeListener(null);
        super.onDestroyView();
    }
}
