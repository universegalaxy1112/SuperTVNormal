package com.livetv.android.listeners;

import com.livetv.android.model.MovieCategory;

public interface LoadMoviesForCategoryResponseListener extends BaseResponseListener {
    void onMoviesForCategoryCompleted(MovieCategory movieCategory);

    void onMoviesForCategoryCompletedError(MovieCategory movieCategory);
}
