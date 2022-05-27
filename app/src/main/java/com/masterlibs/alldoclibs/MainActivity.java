package com.masterlibs.alldoclibs;

import android.os.Bundle;

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
        ///storage/emulated/0/UnRAR/FileDecompressed/Compressed_20220415_1335/JD-Android-VNT.pdf
        findViewById(R.id.readeFile).setOnClickListener(view -> {
            DocReaderActivity.start(this, "/storage/emulated/0/Download/1.xlsx");
            Dialog.start(this);
        });
//        findViewById(R.id.readeFile).setOnClickListener(view -> DocReaderActivity.
//                start(this,"/storage/emulated/0/UnRAR/FileDecompressed/Compressed_20220415_1335/JD-Android-VNT.pdf"));
    }
}