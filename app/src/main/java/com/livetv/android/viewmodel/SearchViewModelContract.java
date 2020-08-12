package com.livetv.android.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import com.livetv.android.model.Movie;
import com.livetv.android.model.Serie;

public interface SearchViewModelContract {

    public interface View extends Lifecycle.View {
        void closeKeyboard();

        void onMovieAccepted(Movie movie);

        void onSerieAccepted(Serie serie);
    }

    public interface ViewModel extends Lifecycle.ViewModel {
        void onConfigurationChanged();

        void showMovieList(RecyclerView recyclerView, EditText editText, boolean z);
    }
}
