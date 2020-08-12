package com.livetv.android.viewmodel;

import android.support.annotation.NonNull;
import com.livetv.android.listeners.LoadProgramsForLiveTVCategoryResponseListener;
import com.livetv.android.listeners.LoadSeasonsForSerieResponseListener;
import com.livetv.android.listeners.LoadSubCategoriesResponseListener;
import com.livetv.android.managers.VideoStreamManager;
import com.livetv.android.model.LiveTVCategory;
import com.livetv.android.model.MainCategory;
import com.livetv.android.model.ModelTypes;
import com.livetv.android.model.MovieCategory;
import com.livetv.android.model.Season;
import com.livetv.android.model.Serie;
import com.livetv.android.utils.networking.NetManager;
import com.livetv.android.viewmodel.LoadingMoviesViewModelContract.View;
import com.livetv.android.viewmodel.LoadingMoviesViewModelContract.ViewModel;
import java.util.List;

public class LoadingMoviesViewModel implements ViewModel, LoadSubCategoriesResponseListener, LoadSeasonsForSerieResponseListener, LoadProgramsForLiveTVCategoryResponseListener {
    private NetManager netManager = NetManager.getInstance();
    private VideoStreamManager videoStreamManager = VideoStreamManager.getInstance();
    private View viewCallback;

    public void onViewResumed() {
    }

    public void onViewAttached(@NonNull Lifecycle.View viewCallback2) {
        this.viewCallback = (View) viewCallback2;
    }

    public void onViewDetached() {
        this.viewCallback = null;
    }

    public void loadSubCategories(int mainCategoryPosition) {
        if (this.videoStreamManager.getMainCategory(mainCategoryPosition).getModelType() == ModelTypes.LIVE_TV_CATEGORIES) {
            this.netManager.retrieveLiveTVPrograms(this.videoStreamManager.getMainCategory(mainCategoryPosition), this);
        } else if (this.videoStreamManager.getMainCategory(mainCategoryPosition).getMovieCategories().size() > 0) {
            this.viewCallback.onSubCategoriesForMainCategoryLoaded();
        } else {
            this.netManager.retrieveSubCategories(this.videoStreamManager.getMainCategory(mainCategoryPosition), this);
        }
    }

    public void loadSeasons(int mainCategoryId, int movieCategoryId, int serieId) {
        Serie serie = (Serie) VideoStreamManager.getInstance().getMainCategory(mainCategoryId).getMovieCategory(movieCategoryId).getMovie(serieId);
        if (mainCategoryId == 6) {
            serie.addSeason(0, new Season());
            this.viewCallback.onSeasonsForSerieLoaded();
        } else if (serie.getSeasonCount() > 0) {
            this.viewCallback.onSeasonsForSerieLoaded();
        } else {
            this.netManager.retrieveSeasons(serie, this);
        }
    }

    public void onError() {
        this.viewCallback.onError();
    }

    public void onSubCategoriesLoaded(MainCategory mainCategory, List<MovieCategory> movieCategories) {

        this.videoStreamManager.getMainCategory(mainCategory.getId()).setMovieCategories(movieCategories);
        this.viewCallback.onSubCategoriesForMainCategoryLoaded();
    }

    public void onSubCategoriesLoadedError() {
        this.viewCallback.onSubCategoriesForMainCategoryLoadedError();
    }

    public void onSeasonsLoaded(Serie serie, int seasonCount) {
        for (int i = 0; i < seasonCount; i++) {
            serie.addSeason(i, new Season());
        }
        this.viewCallback.onSeasonsForSerieLoaded();
    }

    public void onSeasonsLoadedError() {
        this.viewCallback.onSeasonsForSerieLoadedError();
    }

    public void onProgramsForLiveTVCategoriesCompleted() {
        this.viewCallback.onProgramsForLiveTVCategoriesLoaded();
    }

    public void onProgramsForLiveTVCategoryCompleted(LiveTVCategory liveTVCategory) {
        this.videoStreamManager.setLiveTVCategory(liveTVCategory);
    }

    public void onProgramsForLiveTVCategoryError(LiveTVCategory liveTVCategory) {
        this.viewCallback.onProgramsForLiveTVCategoriesLoadedError();
    }
}
