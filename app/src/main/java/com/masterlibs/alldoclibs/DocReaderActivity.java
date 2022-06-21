package com.masterlibs.alldoclibs;

import static com.wxiwei.office.constant.MainConstant.INTENT_FILED_FILE_PATH;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wxiwei.office.officereader.BaseDocActivity;
import com.wxiwei.office.wp.control.Word;
import com.wxiwei.office.wp.scroll.ScrollBarView;

import java.io.File;

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
    protected ScrollBarView getScrollBarView() {
        return findViewById(R.id.scrollBarView);
    }

    @Override
    protected void initView(String filePath) {
        try {
            title = findViewById(R.id.title);
            String path = getIntent().getStringExtra(INTENT_FILED_FILE_PATH);
            title.setText(new File(path).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void showPageToast() {
        Log.d("android_log", "showPageToast: ");
    }

    @Override
    protected void hidePageToast() {
        Log.d("android_log", "hidePageToast: ");
    }

    @Override
    protected void hideZoomToast() {
        Log.d("android_log", "hideZoomToast: ");
    }


    @Override
    public void pageChanged(int page, int pageCount) {
        super.pageChanged(page, pageCount);
        title.setText(page + "/" + pageCount);
        Log.d("TAG", "pageChanged: " + page + "__" + pageCount);
    }

    @Override
    public void changeZoom(int percent) {
        Log.d("android_log", "changeZoom: " + percent);
    }

    @Override
    public void onError(int errorCode) {

    }
}
