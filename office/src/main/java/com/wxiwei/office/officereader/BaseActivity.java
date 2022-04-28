package com.wxiwei.office.officereader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.docxmaster.docreader.OnActionCallback;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements OnActionCallback {
    protected T binding;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Theme_AppThemeDark);

        initLanguage();
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.executePendingBindings();
        initView();
        addEvent();
    }

    protected boolean setThemeApp() {
        return true;
    }

    protected void initLanguage() {

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void addEvent();

    private void performDataBinding() {

    }

    protected String[] getStoragePermission() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    protected boolean permissionGranted(String[] storagePermission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String s : storagePermission) {
            if (checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void callback(String key, Object... data) {

    }
}
