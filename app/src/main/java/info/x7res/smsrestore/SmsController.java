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

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by x7 on 12/18/14.
 */
public class SmsController {
    private static final String TAG = "SmsController";
    private final Context context;

    public SmsController(Context context) {
        this.context = context;
    }

    public void restoreSmsFromSrcFile(String path) {
        Log.i(TAG, "path " + path);
        if (path.endsWith(".db")) {
            new RestoreSmsFromDbFileTask().execute(path);
        } else {
            Log.e(TAG, "file format not supported");
        }
    }

    private class RestoreSmsFromDbFileTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            SdcardDbHelper helper = new SdcardDbHelper(context, params[0]);
            Cursor cursor = helper.queryAllSms();
            int count = cursor.getCount();
            int progress = 0;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext(), progress++) {
                SmsModel model = SmsModel.createFromCursor(cursor);
                model.insertIntoSystem(context);
                this.publishProgress(count, progress);
            }
            return count;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, values[1] + " / " + values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Log.i(TAG, "restore completed, total " + result);
        }
    }
}
