package com.livetv.android.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.livetv.android.databinding.ActivityLivetvnewBinding;
import com.livetv.android.model.LiveProgram;

public interface LiveTVViewModelContract {

    public interface View extends Lifecycle.View {

        void onProgramAccepted(LiveProgram liveProgram);

    }

    public interface ViewModel extends Lifecycle.ViewModel {

        void showProgramList(ActivityLivetvnewBinding activityLiveBinding);

    }
}
