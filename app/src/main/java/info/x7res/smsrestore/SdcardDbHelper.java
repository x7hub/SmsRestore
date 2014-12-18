/**
 * The MIT License (MIT)

 Copyright (c) 2014 x7

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 */

package info.x7res.smsrestore;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;

/**
 * open db on sdcard
 * Created by x7 on 12/18/14.
 */
public class SdcardDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SdcardDbHelper";
    private static final String DATABASE_NAME = "mmssms.db";
    private static final int DATABASE_VERSION = 1;

    public SdcardDbHelper(Context context, String filePath) {
        super(new SdcardDbContext(context, filePath), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "oldVersion " + oldVersion + " newVersion " + newVersion);
    }

    public Cursor queryAllSms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("sms", null, null, null, null, null, "date", null);
        Log.i(TAG, "count " + cursor.getCount());
        return cursor;
    }

    public void test() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("sms", null, null, null, null, null, "date", "10");
        Log.i(TAG, "count " + c.getCount());
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Log.i(TAG, "body " + c.getString(c.getColumnIndex("body")));
        }
        c.close();
    }

    /**
     * open db file on sdcard
     */
    private static class SdcardDbContext extends ContextWrapper {
        private String filePath;

        public SdcardDbContext(Context context, String filePath) {
            super(context);
            this.filePath = filePath;
        }

        @Override
        public File getDatabasePath(String name) {
            Log.i(TAG, filePath);
            File dbFile = new File(filePath);
            if (!dbFile.exists()) {
                Log.w(TAG, "no file");
                return null;
            }
            Log.d(TAG, "db open !");
            return dbFile;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                                   SQLiteDatabase.CursorFactory factory) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                                   SQLiteDatabase.CursorFactory factory,
                                                   DatabaseErrorHandler errorHandler) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        }

    }
}
