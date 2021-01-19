package com.example.androidchart.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.androidchart.R;
import com.example.androidchart.databinding.ActMainBinding;
import com.example.androidchart.injects.base.BaseActivity;

import org.androidannotations.annotations.EActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EActivity
public class MainActivity extends BaseActivity {

    protected ActMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.act_main);
    }
}