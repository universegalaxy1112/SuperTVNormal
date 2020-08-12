package com.livetv.android.viewmodel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import com.livetv.android.viewmodel.Lifecycle.View;
import com.livetv.android.viewmodel.MoviesMenuTVViewModelContract.ViewModel;

public class MoviesMenuTVViewModel implements ViewModel {
    public void onViewResumed() {
    }

    public void onViewAttached(@NonNull View viewCallback) {
    }

    public void onViewDetached() {
    }

    public void showMovieLists(RecyclerView categoriesRecyclerview, int mainCategoryPosition) {
    }

    public void showEpisodeLists(RecyclerView categoriesRecyclerview, int mainCategoryId, int movieCategoryId, int serieId) {
    }
}
