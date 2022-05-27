package com.masterlibs.alldoclibs;

import static com.wxiwei.office.constant.MainConstant.INTENT_FILED_FILE_PATH;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wxiwei.office.officereader.BaseDocActivity;

import java.io.File;

public class DocReaderActivity extends BaseDocActivity {
    private TextView title;
    private AlertDialog dialog;

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
    public void errorLoadPdf(Throwable t) {

    }

    @Override
    public void showDialogLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Loading...");
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void dismissDialogLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public long getTimeLoading() {
        return 1500;
    }
}
