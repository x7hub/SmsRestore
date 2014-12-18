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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by commander on 12/18/14.
 */
public class SmsModel {
    private static final String TAG = "SmsModel";
    public String address;
    public String person;
    public String date;
    public String protocol;
    public String read;
    public String status;
    public String type;
    public String reply_path_present;
    public String body;
    public String service_center;
    public String locked;
    public String error_code;
    public String seen;
    public String delivery_status;
    public String sequence_time;

    public static SmsModel createFromCursor(final Cursor cursor) {
        SmsModel s = new SmsModel();
        s.address = cursor.getString(cursor.getColumnIndex("address"));
        s.person = cursor.getString(cursor.getColumnIndex("person"));
        s.date = cursor.getString(cursor.getColumnIndex("date"));
        s.protocol = cursor.getString(cursor.getColumnIndex("protocol"));
        s.read = cursor.getString(cursor.getColumnIndex("read"));
        s.status = cursor.getString(cursor.getColumnIndex("status"));
        s.type = cursor.getString(cursor.getColumnIndex("type"));
        s.reply_path_present = cursor.getString(cursor.getColumnIndex("reply_path_present"));
        s.body = cursor.getString(cursor.getColumnIndex("body"));
        s.service_center = cursor.getString(cursor.getColumnIndex("service_center"));
        s.locked = cursor.getString(cursor.getColumnIndex("locked"));
        s.error_code = cursor.getString(cursor.getColumnIndex("error_code"));
        s.seen = cursor.getString(cursor.getColumnIndex("seen"));
        s.delivery_status = cursor.getString(cursor.getColumnIndex("delivery_status"));
        s.sequence_time = cursor.getString(cursor.getColumnIndex("sequence_time"));
        return s;
    }

    public void insertIntoSystem(Context context) {
        ContentValues values = new ContentValues();
        values.put("address", this.address);
        values.put("person", this.person);
        values.put("date", this.date);
        values.put("protocol", this.protocol);
        values.put("read", this.read);
        values.put("status", this.status);
        values.put("type", this.type);
        values.put("reply_path_present", this.reply_path_present);
        values.put("body", this.body);
        values.put("service_center", this.service_center);
        values.put("locked", this.locked);
        values.put("error_code", this.error_code);
        values.put("seen", this.seen);
        values.put("delivery_status", this.delivery_status);
        values.put("sequence_time", this.sequence_time);
        context.getContentResolver().insert(Uri.parse("content://sms"), values);
    }

    public void test() {
        Log.i(TAG, "body " + this.body);
    }
}
