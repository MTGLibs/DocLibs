package com.masterlibs.alldoclibs;

import static com.wxiwei.office.constant.MainConstant.INTENT_FILED_FILE_PATH;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wxiwei.office.officereader.BaseDocActivity;

import java.io.File;

public class DocReaderActivity extends BaseDocActivity {
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
        TextView title = findViewById(R.id.title);
        String path = getIntent().getStringExtra(INTENT_FILED_FILE_PATH);
        title.setText(new File(path).getName());
    }

    @Override
    protected void addEvent() {
        findViewById(R.id.iv_back).setOnClickListener(v -> {
            finish();
        });
    }

}
