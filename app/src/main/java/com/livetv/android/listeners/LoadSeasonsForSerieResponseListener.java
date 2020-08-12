package com.livetv.android.listeners;

import com.livetv.android.model.Serie;

public interface LoadSeasonsForSerieResponseListener extends BaseResponseListener {
    void onSeasonsLoaded(Serie serie, int i);

    void onSeasonsLoadedError();
}
