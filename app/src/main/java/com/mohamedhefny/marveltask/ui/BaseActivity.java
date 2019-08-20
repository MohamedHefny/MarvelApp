package com.mohamedhefny.marveltask.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    protected final int HIDE_STATUS_BAR = WindowManager.LayoutParams.FLAG_FULLSCREEN;
    protected final int TRANSPARENT_STATUS_BAR = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    protected void enableFullScreen(int flags, int mask){
        getWindow().setFlags(flags, mask);
    }
}
