package com.masterlibs.alldoclibs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wxiwei.office.officereader.BaseDocActivity;

public class Dialog extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, Dialog.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
