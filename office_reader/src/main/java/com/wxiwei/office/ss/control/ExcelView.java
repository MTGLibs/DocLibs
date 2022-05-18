package com.wxiwei.office.ss.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxiwei.office.R;
import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.ss.model.baseModel.Sheet;
import com.wxiwei.office.ss.model.baseModel.Workbook;
import com.wxiwei.office.ss.sheetbar.SheetBar;
import com.wxiwei.office.ss.view.SheetView;
import com.wxiwei.office.system.IControl;

import java.util.Vector;

/* loaded from: classes2.dex */
public class ExcelView extends RelativeLayout {
    private SheetBar bar;
    private IControl control;
    private boolean isDefaultSheetBar = true;
    private Spreadsheet ss;

    public ExcelView(Context context, String str, Workbook workbook, IControl iControl) {
        super(context);
        this.control = iControl;
        this.ss = new Spreadsheet(context, str, workbook, iControl, this);
        addView(this.ss, new LayoutParams(-1, -1));
    }

    private void changeButtonSelection(int i2) {
        LinearLayout linearLayout = (LinearLayout) ((HorizontalScrollView) findViewById(R.id.hsvButtons)).findViewById(R.id.llButtons);
        int i3 = 0;
        while (i3 < linearLayout.getChildCount()) {
            ((TextView) linearLayout.getChildAt(i3)).setBackground(getContext().getResources().getDrawable(i3 == i2 ? R.drawable.bg_sheet_button_selected : R.drawable.bg_sheet_button_unselected));
            i3++;
        }
    }

    private void initSheetbar() {
        if (this.isDefaultSheetBar) {
            LayoutInflater from = LayoutInflater.from(getContext());
            View inflate = from.inflate(R.layout.layout_sheet_buttons, (ViewGroup) null, false);
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.addRule(12);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.llButtons);
            Vector vector = (Vector) this.control.getActionValue(EventConstant.SS_GET_ALL_SHEET_NAME, null);
            int size = vector.size();
            int i2 = 0;
            while (i2 < size) {
                TextView textView = (TextView) from.inflate(R.layout.layout_sheet_button_item, (ViewGroup) null, false);
                textView.setText((String) vector.get(i2));
                linearLayout.addView(textView);
                textView.setBackground(getContext().getResources().getDrawable(i2 == 0 ? R.drawable.bg_sheet_button_selected : R.drawable.bg_sheet_button_unselected));
                int finalI = i2;
                textView.setOnClickListener(new OnClickListener() { // from class: b.o.a.f.a.a
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ExcelView.this.a(finalI, view);
                    }
                });
                i2++;
            }
            addView(inflate, layoutParams);
        }
    }

    public /* synthetic */ void a(int i2, View view) {
        this.control.actionEvent(EventConstant.SS_SHOW_SHEET, Integer.valueOf(i2));
        changeButtonSelection(i2);
    }

    public void dispose() {
        this.control = null;
        Spreadsheet spreadsheet = this.ss;
        if (spreadsheet != null) {
            spreadsheet.dispose();
        }
    }

    public int getBottomBarHeight() {
        boolean z = this.isDefaultSheetBar;
        return this.control.getMainFrame().getBottomBarHeight();
    }

    public int getCurrentViewIndex() {
        return this.ss.getCurrentSheetNumber();
    }

    public SheetView getSheetView() {
        return this.ss.getSheetView();
    }

    public Spreadsheet getSpreadsheet() {
        return this.ss;
    }

    public void init() {
        this.ss.init();
        initSheetbar();
    }

    public void removeSheetBar() {
        this.isDefaultSheetBar = false;
    }

    public void showSheet(int i2) {
        this.ss.showSheet(i2);
        if (this.isDefaultSheetBar) {
            changeButtonSelection(i2);
        } else {
            this.control.getMainFrame().doActionEvent(EventConstant.SS_CHANGE_SHEET, Integer.valueOf(i2));
        }
    }

    public void showSheet(String str) {
        this.ss.showSheet(str);
        Sheet sheet = this.ss.getWorkbook().getSheet(str);
        if (sheet != null) {
            int sheetIndex = this.ss.getWorkbook().getSheetIndex(sheet);
            if (!this.isDefaultSheetBar) {
                this.control.getMainFrame().doActionEvent(EventConstant.SS_CHANGE_SHEET, Integer.valueOf(sheetIndex));
            }
        }
    }
}
