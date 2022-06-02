/**
 * Copyright 2016 Bartosz Schiller
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.barteksc.pdfviewer.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.print.PrintManager;

import androidx.annotation.RequiresApi;
import androidx.print.PrintHelper;

import com.github.barteksc.pdfviewer.PrintDocumentAdapter;
import com.shockwave.pdfium.PdfPasswordException;
import com.shockwave.pdfium.PdfiumCore;
import com.tom_roush.pdfbox.pdmodel.common.PDPageLabelRange;
import com.tom_roush.pdfbox.pdmodel.interactive.action.PDWindowsLaunchParams;
import com.wxiwei.office.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private FileUtils() {
        // Prevents instantiation
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void printPdfFile(Context context, String path) {
        try {
            Uri uri = Uri.fromFile(new File(path));
            new PdfiumCore(context).newDocument(context.getContentResolver().openFileDescriptor(uri, PDPageLabelRange.STYLE_ROMAN_LOWER));
            if (PrintHelper.systemSupportsPrint()) {
                @SuppressLint("WrongConstant") PrintManager printManager = (PrintManager) context.getSystemService(PDWindowsLaunchParams.OPERATION_PRINT);
                StringBuilder sb = new StringBuilder();
                sb.append(context.getString(R.string.app_name));
                sb.append(" Document");
                if (printManager != null) {
                    printManager.print(sb.toString(), new PrintDocumentAdapter(context, uri), null);
                    return;
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File fileFromAsset(Context context, String assetName) throws IOException {
        File outFile = new File(context.getCacheDir(), assetName + "-pdfview.pdf");
        if (assetName.contains("/")) {
            outFile.getParentFile().mkdirs();
        }
        copy(context.getAssets().open(assetName), outFile);
        return outFile;
    }

    public static void copy(InputStream inputStream, File output) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(output);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
    }
}
