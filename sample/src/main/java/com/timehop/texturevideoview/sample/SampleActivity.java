package com.timehop.texturevideoview.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.timehop.texturevideoview.TextureVideoView;

import java.io.IOException;

public class SampleActivity extends Activity implements View.OnClickListener,
        ActionBar.OnNavigationListener {

    // Video file url
    private static final String VIDEO_PATH = "mov_bbb.mp4";
    private TextureVideoView mTextureVideoView;
    private AssetFileDescriptor mAssetFileDescriptor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            mAssetFileDescriptor = getAssets().openFd(VIDEO_PATH);
            initView();
            initActionBar();
        } catch (IOException e) {
            new AlertDialog.Builder(this)
                    .setMessage("Unable to load video from assets")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
    }

    private void initView() {
        mTextureVideoView = (TextureVideoView) findViewById(R.id.cropTextureView);

        findViewById(R.id.btnPlay).setOnClickListener(this);
        findViewById(R.id.btnPause).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                mTextureVideoView.play();
                break;
            case R.id.btnPause:
                mTextureVideoView.pause();
                break;
            case R.id.btnStop:
                mTextureVideoView.stop();
                break;
        }
    }

    final int indexCropCenter = 0;
    final int indexCropTop = 1;
    final int indexCropBottom = 2;

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case indexCropCenter:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.CENTER_CROP);
                mTextureVideoView.setDataSource(mAssetFileDescriptor);
                mTextureVideoView.play();
                break;
            case indexCropTop:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.TOP);
                mTextureVideoView.setDataSource(mAssetFileDescriptor);
                mTextureVideoView.play();
                break;
            case indexCropBottom:
                mTextureVideoView.stop();
                mTextureVideoView.setScaleType(TextureVideoView.ScaleType.BOTTOM);
                mTextureVideoView.setDataSource(mAssetFileDescriptor);
                mTextureVideoView.play();
                break;
        }
        return true;
    }
}
