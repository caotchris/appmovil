package com.sunmi.printerhelper.activity;


import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ucot.R;
import com.sunmi.printerhelper.BaseApp;

public abstract class BaseActivity extends AppCompatActivity {
    BaseApp baseApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApp = (BaseApp) getApplication();
    }

}
