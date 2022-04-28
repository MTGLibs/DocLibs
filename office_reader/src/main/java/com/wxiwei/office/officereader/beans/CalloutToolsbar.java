
package com.wxiwei.office.officereader.beans;

import com.wxiwei.office.constant.EventConstant;
import com.wxiwei.office.R;
import com.wxiwei.office.system.IControl;

import android.content.Context;
import android.util.AttributeSet;

public class CalloutToolsbar extends AToolsbar
{

    /**
     * 
     * @param context
     * @param attrs
     */
    public CalloutToolsbar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 
     * @param context
     * @param control
     */
    public CalloutToolsbar(Context context, IControl control)
    {
        super(context, control);
        init();
        toolsbarFrame.setBackgroundResource(R.drawable.sys_toolsbar_button_bg_normal);
    }

    private void init()
    {
        // 标签


        // 标签

    }
}
