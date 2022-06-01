package com.masterlibs.alldoclibs;

import static com.wxiwei.office.constant.MainConstant.INTENT_FILED_FILE_PATH;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.wxiwei.office.common.ISlideShow;
import com.wxiwei.office.officereader.BaseDocActivity;
import com.wxiwei.office.pg.control.PGControl;

import java.io.File;
import java.util.List;

public class DocReaderActivity extends BaseDocActivity {
    private TextView title;

    public static void start(Context context, String path) {
        Intent starter = new Intent(context, DocReaderActivity.class);
        starter.putExtra(INTENT_FILED_FILE_PATH, path);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reader;
    }

    @Override
    protected FrameLayout getFrameLayoutDoc() {
        return findViewById(R.id.app_frame);
    }

    @Override
    protected void initView() {
        try {
            title = findViewById(R.id.title);
            String path = getIntent().getStringExtra(INTENT_FILED_FILE_PATH);
            title.setText(new File(path).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void addEvent() {
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void pageChanged(int page, int pageCount) {
        title.setText(page + "/" + pageCount);
        Log.d("TAG", "pageChanged: " + page + "__" + pageCount);
    }

    @Override
    public void getSlideImages(List<Bitmap> bitmaps) {
        Log.d("TAG", "getSlideImages: ");
    }

    @Override
    public void error(int i) {
        super.error(i);
    }
}
