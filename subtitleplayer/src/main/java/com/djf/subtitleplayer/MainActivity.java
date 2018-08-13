package com.djf.subtitleplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.djf.fileexplorer.FileExplorerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.btn_open_fileexplorer)
    Button btnOpenFileexplorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_open_fileexplorer)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open_fileexplorer:
                startActivity(new Intent(this, FileExplorerActivity.class));
                break;
        }
    }
}
