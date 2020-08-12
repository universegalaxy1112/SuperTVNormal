package com.livetv.android.listeners;

import com.livetv.android.model.Season;

public interface LoadEpisodesForSerieResponseListener extends BaseResponseListener {
    void onEpisodesForSerieCompleted(Season season);
}
