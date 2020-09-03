package com.livetv.android.view.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.livetv.android.R;
import com.livetv.android.view.exoplayernew.VideoPlayFragment;

public class VideoActivity extends AppCompatActivity {

    private VideoPlayFragment videoFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_activity);

        videoFragment = new VideoPlayFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.video_container, videoFragment).commit();

    }

    @Override
    public void onNewIntent(Intent intent) {
        videoFragment.onNewIntent(intent);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || videoFragment.dispatchKeyEvent(event);
    }

}
