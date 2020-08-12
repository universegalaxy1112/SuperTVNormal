package com.livetv.android.viewmodel;

import com.livetv.android.databinding.MovieDetailsFragmentBinding;
import com.livetv.android.listeners.OptionsMenuOpenListener;
import com.livetv.android.model.Movie;

public interface MovieDetailsViewModelContract {

    public interface View extends Lifecycle.View {
        void onPlaySelected(Movie movie, boolean z);
    }

    public interface ViewModel extends Lifecycle.ViewModel {
        void showMovieDetails(Movie movie, MovieDetailsFragmentBinding movieDetailsFragmentBinding, OptionsMenuOpenListener optionsMenuOpenListener, int i);
    }
}
