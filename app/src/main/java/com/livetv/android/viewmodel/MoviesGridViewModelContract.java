package com.livetv.android.viewmodel;

import android.support.v7.widget.RecyclerView;
import com.livetv.android.model.Movie;
import com.livetv.android.model.Serie;

public interface MoviesGridViewModelContract {

    public interface View extends Lifecycle.View {
        void onMovieAccepted(Movie movie);

        void onSerieAccepted(Serie serie);
    }

    public interface ViewModel extends Lifecycle.ViewModel {
        void onConfigurationChanged();

        void showMovieList(RecyclerView recyclerView, int i, int i2, int i3, int i4);
    }
}
