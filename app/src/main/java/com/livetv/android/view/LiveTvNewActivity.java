package com.livetv.android.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.livetv.android.databinding.ActivityLivetvnewBinding;
import com.livetv.android.listeners.LiveProgramSelectedListener;
import com.livetv.android.model.LiveProgram;
import com.livetv.android.utils.Device;
import com.livetv.android.utils.Tracking;
import com.livetv.android.view.exoplayer.VideoActivity;
import com.livetv.android.view.exoplayer.VideoFragment;
import com.livetv.android.view.exoplayernew.VideoPlayFragment;
import com.livetv.android.viewmodel.Lifecycle;
import com.livetv.android.viewmodel.LiveTVViewModel;
import com.livetv.android.viewmodel.LiveTVViewModelContract;
import com.livetv.android.R;

public class LiveTvNewActivity extends AppCompatActivity implements LiveProgramSelectedListener, LiveTVViewModelContract.View{
    private VideoPlayFragment videoPlayFragment;
    private LiveTVViewModel liveTVViewModel;
    ActivityLivetvnewBinding activityLiveBinding;
    public boolean isHandlingEvent = false;

    protected Lifecycle.ViewModel getViewModel() {
        return liveTVViewModel;
    }


    protected Lifecycle.View getLifecycleView() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoPlayFragment=new VideoPlayFragment();
        videoPlayFragment.hidePlayBack();
        videoPlayFragment.showNoChannel();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.exo_player,videoPlayFragment,"Frag_top_tag");
        transaction.commit();
        liveTVViewModel=new LiveTVViewModel(this);
        activityLiveBinding = DataBindingUtil.setContentView(this, R.layout.activity_livetvnew);
        activityLiveBinding.setLiveTVFragmentVM(liveTVViewModel);
        final LinearLayout exo_player_virtual = activityLiveBinding.exoPlayerVirtual;
        ViewTreeObserver vto = exo_player_virtual.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                exo_player_virtual.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = exo_player_virtual.getMeasuredHeight();
                ViewGroup.LayoutParams params = exo_player_virtual.getLayoutParams();
                params.width = height * 16/9;
                exo_player_virtual.setLayoutParams(params);
                initExoplayerSize(height * 16/9, height);
            }
        });
        liveTVViewModel.showProgramList(activityLiveBinding);

    }

    private void initExoplayerSize(final int width, final int height) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        int margin = (int)(getResources().getDisplayMetrics().density*16);
        layoutParams.setMargins(margin,margin,20,20);
        activityLiveBinding.exoPlayer.setLayoutParams(layoutParams);
        activityLiveBinding.exoPlayer.bringToFront();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getViewModel() != null)
            getViewModel().onViewAttached(getLifecycleView());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }

    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishActivity();
            return true;
        }

        if(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT){
            liveTVViewModel.fullScreen(null);
            return false;
        }

        if(keyCode==KeyEvent.KEYCODE_DPAD_CENTER){
            liveTVViewModel.toggleTitle(videoPlayFragment);
            return false;
        }

        if(keyCode==KeyEvent.KEYCODE_DPAD_LEFT){
            liveTVViewModel.showCategories(null);
            liveTVViewModel.minimize(null);
            return false;
        }


        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Device.isHDMIStatusSet) {
            Device.setHDMIStatus();
        }
        Tracking.getInstance(this).onStart();
    }

    @Override
    public void onLiveProgramSelected(LiveProgram liveProgram, int programPosition) {
        String[] uris = new String[] {liveProgram.getStreamUrl()};
        String movieUrl = liveProgram.getStreamUrl()/*.replace(".mkv.mkv", ".mkv").replace(".mp4.mp4", ".mp4")*/;
        String extension = liveProgram.getStreamUrl().substring(movieUrl.lastIndexOf(".") + 1);
        String[] extensions = new String[] {extension};
        Intent launchIntent = new Intent(this, VideoActivity.class);
        launchIntent.putExtra(VideoPlayFragment.URI_LIST_EXTRA, uris)
                .putExtra(VideoPlayFragment.EXTENSION_LIST_EXTRA, extensions)
                .putExtra("title",liveProgram.getTitle())
                .putExtra("icon_url", liveProgram.getIconUrl())
                .setAction(VideoPlayFragment.ACTION_VIEW_LIST);
        videoPlayFragment = (VideoPlayFragment)getSupportFragmentManager().findFragmentById(R.id.exo_player);
        if(videoPlayFragment == null) return;
        videoPlayFragment.onNewIntent(launchIntent);
        videoPlayFragment.onStart();
        videoPlayFragment.onResume();
        videoPlayFragment.hideNoChannel();
    }

    @Override
    protected void onPause() {
        Tracking.getInstance(this).setAction("IDLE");
        Tracking.getInstance(this).onStop();
        super.onPause();
    }

    @Override
    public void onProgramAccepted(LiveProgram liveProgram) {
        onLiveProgramSelected(liveProgram,0);
    }

}
