/*
 * 文件名称:          AbstractControl.java
 *  
 * 编译器:            android2.2
 * 时间:              下午1:59:01
 */
package com.wxiwei.office.system;

import com.wxiwei.office.common.ICustomDialog;
import com.wxiwei.office.common.IOfficeToPicture;
import com.wxiwei.office.common.ISlideShow;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;


public abstract class AbstractControl implements IControl
{

    @ Override
    public void layoutView(int x, int y, int w, int h)
    {

    }

    @ Override
    public void actionEvent(int actionID, Object obj)
    {
    }

    @ Override
    public Object getActionValue(int actionID, Object obj)
    {
        return null;
    }

    @ Override
    public int getCurrentViewIndex()
    {
    	return -1;
    }
    
    @ Override
    public View getView()
    {
        return null;
    }

    @ Override
    public Dialog getDialog(Activity activity, int id)
    {
        return null;
    }

    @ Override
    public IMainFrame getMainFrame()
    {
        return null;
    }
    
    @ Override
    public Activity getActivity()
    {
        return null;
    }

    @ Override
    public IFind getFind()
    {
        return null;
    }
    
    @ Override
    public void dispose()
    {
    }

    @ Override
    public boolean isAutoTest()
    {
        return false;
    }

    @ Override
    public IOfficeToPicture getOfficeToPicture()
    {
        return null;
    }
    /**
     * 
     */
    public ICustomDialog getCustomDialog()
    {
        return null;
    }
    
    /**
     * 
     */
    public boolean isSlideShow()
    {
        return false;
    }
    /**
     * 
     * @return
     */
    public ISlideShow getSlideShow()
    {
        return null;
    }
    
    @ Override
    public boolean openFile(String filePath)
    {
        return false;
    }
    
    @ Override
    public IReader getReader()
    {
        return null;
    }
    
    
    @Override
    public byte getApplicationType()
    {
        return  -1;
    }

}
