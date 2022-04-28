/*
 * 文件名称:          SSConstant.java
 *  
 * 编译器:            android2.2
 * 时间:              下午2:20:14
 */
package com.wxiwei.office.constant;

import android.graphics.Color;

/**
 * excel应用的常量类
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2011-11-8
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:         
 * <p>
 * <p>
 */
public final class SSConstant
{
    // 默认行高（像素值）
    public static final int DEFAULT_ROW_HEIGHT = 18;
    // 默认列宽（像素值）
    public static final int DEFAULT_COLUMN_WIDTH = 72;
    // 行标题默认宽度（像素值）
    public static final int DEFAULT_ROW_HEADER_WIDTH = 50;
    // 列标题默认高度（像素值）
    public static final int  DEFAULT_COLUMN_HEADER_HEIGHT = 30;
    // 标题填充颜色
    public static final int HEADER_FILL_COLOR = Color.parseColor("#F8F8F8");
    // 标题字符颜色
    public static final int HEADER_TEXT_COLOR = Color.BLACK;
    // header grid line color
    public static final int HEADER_GRIDLINE_COLOR = 0xffc7d1d9;
    // 网格线颜色
    public static final int GRIDLINE_COLOR = 0xffc7d1d9;
    // 标题的字号
    public static final int HEADER_TEXT_FONTSZIE = 16;
    //active color
    public static final int ACTIVE_COLOR = Color.parseColor("#35D977");

    public static final int ACTIVE_CELL_COLOR = Color.parseColor("#2E7D32");

    // 列宽用到的字符宽度
    public static final float COLUMN_CHAR_WIDTH = 6.0f;
    //
    public static final int SHEET_SPACETOBORDER = 2;
    //indent 
    public static final int INDENT_TO_PIXEL = 34;
    public static final float PAGE_MAX_ZOOM = 9f;
    public static final float PAGE_DEFAULT_ZOOM = 0.9f;
    public static final float PAGE_MIN_ZOOM = 0.9f;


}
