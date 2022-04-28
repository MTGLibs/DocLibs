package com.masterlibs.alldoclibs;

import static com.wxiwei.office.constant.MainConstant.INTENT_FILED_FILE_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.wxiwei.office.officereader.DocReaderActivity;
import com.wxiwei.office.officereader.FileListActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, DocReaderActivity.class);
        intent.putExtra(INTENT_FILED_FILE_PATH, "/storage/emulated/0/Download/351_ Trần Thị Thu Hà_Đường lối quốc phòng và an ninh Đảng cộng sản Việt Nam done.doc");
        startActivity(intent);
    }
}