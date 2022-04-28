/*
 * 文件名称:          FileReaderThread.java
 *
 * 编译器:            android2.2
 * 时间:              下午8:11:02
 */
package com.wxiwei.office.system;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wxiwei.office.constant.MainConstant;
import com.wxiwei.office.fc.doc.DOCReader;
import com.wxiwei.office.fc.doc.DOCXReader;
import com.wxiwei.office.fc.doc.TXTReader;
import com.wxiwei.office.fc.pdf.PDFReader;
import com.wxiwei.office.fc.ppt.PPTReader;
import com.wxiwei.office.fc.ppt.PPTXReader;
import com.wxiwei.office.fc.xls.XLSReader;
import com.wxiwei.office.fc.xls.XLSXReader;


/**
 * 读取文档线程
 * <p>
 * <p>
 * Read版本:        Read V1.0
 * <p>
 * 作者:            ljj8494
 * <p>
 * 日期:            2012-3-12
 * <p>
 * 负责人:          ljj8494
 * <p>
 * 负责小组:
 * <p>
 * <p>
 */
public class FileReaderThread extends Thread {

    /**
     * @param activity
     * @param handler
     * @param filePath
     * @param encoding
     */
    public FileReaderThread(IControl control, Handler handler, String filePath, String encoding, Uri uri) {
        this.control = control;
        this.handler = handler;
        this.filePath = filePath;
        this.encoding = encoding;
        this.uri = uri;
    }

    /**
     *
     */
    public void run() {
        // show progress
        Message msg = new Message();
        msg.what = MainConstant.HANDLER_MESSAGE_SHOW_PROGRESS;
        handler.handleMessage(msg);

        msg = new Message();
        msg.what = MainConstant.HANDLER_MESSAGE_DISMISS_PROGRESS;
        try {
            IReader reader;
            String fileName = filePath.toLowerCase();
            // doc
            if (fileName.endsWith(MainConstant.FILE_TYPE_DOC)
                    || fileName.endsWith(MainConstant.FILE_TYPE_DOT)) {
                Log.e("datcoi", "run: DOC");
                reader = new DOCReader(control, filePath, uri);
            }
            // docx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_DOCX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_DOTX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_DOTM)) {
                Log.e("datcoi", "run: DOCX");
                reader = new DOCXReader(control, filePath, uri);
            }
            //
            else if (fileName.endsWith(MainConstant.FILE_TYPE_TXT)) {
                Log.e("datcoi", "run: TXT");
                reader = new TXTReader(control, filePath, encoding, uri);
            }
            // xls
            else if (fileName.endsWith(MainConstant.FILE_TYPE_XLS)
                    || fileName.endsWith(MainConstant.FILE_TYPE_XLT)) {
                Log.e("datcoi", "run: XLS");
                reader = new XLSReader(control, filePath, uri);
            }
            // xlsx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_XLSX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_XLTX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_XLTM)
                    || fileName.endsWith(MainConstant.FILE_TYPE_XLSM)) {
                Log.e("datcoi", "run: XLSX");
                reader = new XLSXReader(control, filePath, uri);
            }
            // ppt
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PPT)
                    || fileName.endsWith(MainConstant.FILE_TYPE_POT)) {
                Log.e("datcoi", "run: PPT");
                reader = new PPTReader(control, filePath, uri);
            }
            // pptx
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PPTX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_PPTM)
                    || fileName.endsWith(MainConstant.FILE_TYPE_POTX)
                    || fileName.endsWith(MainConstant.FILE_TYPE_POTM)) {
                Log.e("datcoi", "run: PPTX");
                reader = new PPTXReader(control, filePath, uri);
            }
            // PDF document
            else if (fileName.endsWith(MainConstant.FILE_TYPE_PDF)) {
                Log.e("datcoi", "run: PDF");
                reader = new PDFReader(control, filePath);
            }
            // other
            else {
                Log.e("datcoi", "run: TXT2");
                reader = new TXTReader(control, filePath, encoding, uri);
            }
            // 把IReader实例传出
            Message mesReader = new Message();
            mesReader.obj = reader;
            mesReader.what = MainConstant.HANDLER_MESSAGE_SEND_READER_INSTANCE;
            handler.handleMessage(mesReader);


            msg.obj = reader.getModel();
            reader.dispose();
            msg.what = MainConstant.HANDLER_MESSAGE_SUCCESS;
            //handler.handleMessage(msg);
        } catch (OutOfMemoryError eee) {
            Log.e("datcoi", "run: OutOfMemory");
            msg.what = MainConstant.HANDLER_MESSAGE_ERROR;
            msg.obj = eee;
        } catch (Exception ee) {
            ee.printStackTrace();
            Log.e("datcoi", "run: Exception");
            msg.what = MainConstant.HANDLER_MESSAGE_ERROR;
            msg.obj = ee;
        } catch (AbortReaderError ee) {
            Log.e("datcoi", "run: Abort");
            msg.what = MainConstant.HANDLER_MESSAGE_ERROR;
            msg.obj = ee;
        } finally {
            Log.e("datcoi", "run: Finally");
            handler.handleMessage(msg);
            control = null;
            handler = null;
            encoding = null;
            filePath = null;
        }
    }

    //
    private String encoding;
    //
    private String filePath;
    //
    private Handler handler;
    //
    private IControl control;
    //
    private Uri uri;
}
