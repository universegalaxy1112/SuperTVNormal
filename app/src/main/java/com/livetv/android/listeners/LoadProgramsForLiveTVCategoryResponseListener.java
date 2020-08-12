package com.livetv.android.listeners;

import com.livetv.android.model.LiveTVCategory;

public interface LoadProgramsForLiveTVCategoryResponseListener extends BaseResponseListener {
    void onProgramsForLiveTVCategoriesCompleted();

    void onProgramsForLiveTVCategoryCompleted(LiveTVCategory liveTVCategory);

    void onProgramsForLiveTVCategoryError(LiveTVCategory liveTVCategory);
}
