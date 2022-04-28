package com.wxiwei.office.ss.util;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class StreamUtils {
    public static InputStream getInputStream(Context context, Uri uri) {
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        if (scheme.startsWith(ContentResolver.SCHEME_CONTENT)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (scheme.startsWith(ContentResolver.SCHEME_FILE)) {
            File f = new File(uri.getPath());
            if (f.exists()) {
                try {
                    return new FileInputStream(f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
