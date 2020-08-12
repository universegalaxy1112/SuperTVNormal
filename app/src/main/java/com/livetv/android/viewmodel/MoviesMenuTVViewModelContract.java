package com.livetv.android.viewmodel;

import android.support.v7.widget.RecyclerView;
import com.livetv.android.model.Movie;
import com.livetv.android.model.Serie;

public interface MoviesMenuTVViewModelContract {

    public interface View extends Lifecycle.View {
        void onMovieAccepted(Movie movie);

        void onSearchSelected(boolean z);

        void onSerieAccepted(int i, Serie serie);

        void onShowAsGridSelected(Integer num);
    }

    public interface ViewModel extends Lifecycle.ViewModel {
        void showEpisodeLists(RecyclerView recyclerView, int i, int i2, int i3);

        void showMovieLists(RecyclerView recyclerView, int i);
    }
}
