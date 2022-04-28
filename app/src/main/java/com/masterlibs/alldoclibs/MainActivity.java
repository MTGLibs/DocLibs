package com.masterlibs.alldoclibs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.readeFile).setOnClickListener(view -> DocReaderActivity.start(this,"/storage/emulated/0/Download/351_ Trần Thị Thu Hà_Đường lối quốc phòng và an ninh Đảng cộng sản Việt Nam done.doc"));
    }
}