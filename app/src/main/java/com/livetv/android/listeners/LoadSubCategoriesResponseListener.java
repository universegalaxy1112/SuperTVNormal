package com.livetv.android.listeners;

import com.livetv.android.model.MainCategory;
import com.livetv.android.model.MovieCategory;
import java.util.List;

public interface LoadSubCategoriesResponseListener extends BaseResponseListener {
    void onSubCategoriesLoaded(MainCategory mainCategory, List<MovieCategory> list);

    void onSubCategoriesLoadedError();
}
