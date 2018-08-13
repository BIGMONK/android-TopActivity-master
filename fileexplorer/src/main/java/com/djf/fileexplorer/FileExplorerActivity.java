
package com.djf.fileexplorer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.IOException;


public class FileExplorerActivity extends AppCompatActivity {
    private Settings mSettings;
    private static final String TAG = "FileExplorerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mSettings == null) {
            mSettings = new Settings(this);
        }

        String lastDirectory = mSettings.getLastDirectory();
        Log.d(TAG, "onCreate: " + lastDirectory);
        if (!TextUtils.isEmpty(lastDirectory) && new File(lastDirectory).isDirectory()) {
            doOpenDirectory(lastDirectory, false);
        } else {
            doOpenDirectory("/", false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FileExplorerEvents.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileExplorerEvents.getBus().unregister(this);
    }

    private void doOpenDirectory(String path, boolean addToBackStack) {
        Log.d(TAG, "doOpenDirectory: path:" + path + "   addToBackStack:" + addToBackStack);
        Fragment newFragment = FileListFragment.newInstance(path);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body, newFragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Subscribe
    public void onClickFile(FileExplorerEvents.OnClickFile event) {
        File f = event.mFile;
        Log.d(TAG, "onClickFile: " + f.getPath());
        try {
            f = f.getAbsoluteFile();
            Log.d(TAG, "onClickFile1: " + f);
            f = f.getCanonicalFile();
            Log.d(TAG, "onClickFile2: " + f);
            if (TextUtils.isEmpty(f.toString())) {
                f = new File("/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onClickFile3: " + f.getAbsolutePath() + "  " + f.getTotalSpace());
        if (f.isDirectory()) {
            String path = f.toString();
            mSettings.setLastDirectory(path);
            doOpenDirectory(path, true);
        } else if (f.exists()) {
            Toast.makeText(this, f.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
