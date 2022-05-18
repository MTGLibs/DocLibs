package com.masterlibs.alldoclibs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        try {
        //            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        //            intent.addCategory("android.intent.category.DEFAULT");
        //            intent.setData(Uri.parse(String.format("package:%s", this.getPackageName())));
        //            this.startActivityForResult(intent, 1);
        //        } catch (Exception e) {
        //            Intent intent = new Intent();
        //            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
        //            this.startActivityForResult(intent, 1);
        //        }
        //sdcard/Download/DS CẤM THI HỆ CQ  KỲ 2 NĂM 2021-2022 - gốc.xlsx
        ///storage/emulated/0/UnRAR/FileDecompressed/Compressed_20220415_1335/JD-Android-VNT.pdf
        findViewById(R.id.readeFile).setOnClickListener(view -> DocReaderActivity.start(this, "sdcard/Download/DS CẤM THI HỆ CQ  KỲ 2 NĂM 2021-2022 - gốc.xlsx"));
//        findViewById(R.id.readeFile).setOnClickListener(view -> DocReaderActivity.
//                start(this,"/storage/emulated/0/UnRAR/FileDecompressed/Compressed_20220415_1335/JD-Android-VNT.pdf"));
    }
}