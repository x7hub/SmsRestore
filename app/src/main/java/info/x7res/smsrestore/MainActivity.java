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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by x7 on 12/18/14.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private final int REQUEST_PICK_FILE = 0x1;
    private TextView textviewHello;
    private Button buttonSelectDbFile;
    private Button buttonRestore;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewHello = (TextView) findViewById(R.id.textview_hello);
        buttonSelectDbFile = (Button) findViewById(R.id.button_select_db_file);
        buttonRestore = (Button) findViewById(R.id.button_restore);
        buttonSelectDbFile.setOnClickListener(this);
        buttonRestore.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_select_db_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType("*/*");
                try {
                    startActivityForResult(Intent.createChooser(intent, null), REQUEST_PICK_FILE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.e(TAG, "no file manager installed");
                }
                break;
            case R.id.button_restore:
                SmsController controller = new SmsController(this);
                controller.restoreSmsFromSrcFile(path);
                break;
            default:
                Log.w(TAG, "onClick default");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_FILE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    this.path = uri.getPath();
                    textviewHello.setText(this.path);
                    buttonRestore.setVisibility(View.VISIBLE);
                }
                break;
            default:
                Log.w(TAG, "onActivityResult default");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
