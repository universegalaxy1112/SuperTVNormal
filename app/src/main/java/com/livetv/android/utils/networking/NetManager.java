package com.livetv.android.utils.networking;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.livetv.android.LiveTvApplication;
import com.livetv.android.listeners.LoadEpisodesForSerieResponseListener;
import com.livetv.android.listeners.LoadMoviesForCategoryResponseListener;
import com.livetv.android.listeners.LoadProgramsForLiveTVCategoryResponseListener;
import com.livetv.android.listeners.LoadSeasonsForSerieResponseListener;
import com.livetv.android.listeners.LoadSubCategoriesResponseListener;
import com.livetv.android.listeners.StringRequestListener;
import com.livetv.android.managers.VideoStreamManager;
import com.livetv.android.model.LiveTVCategory;
import com.livetv.android.model.MainCategory;
import com.livetv.android.model.MovieCategory;
import com.livetv.android.model.Season;
import com.livetv.android.model.Serie;
import com.livetv.android.model.VideoStream;
import com.livetv.android.utils.Device;
import com.livetv.android.utils.networking.services.LiveTVServicesManual;
import com.livetv.android.utils.networking.services.UTF8StringRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetManager {
    private static NetManager m_NetMInstante;
    private RequestQueue queue = Volley.newRequestQueue(LiveTvApplication.getAppContext());
    private RequestQueue searchQueue = Volley.newRequestQueue(LiveTvApplication.getAppContext());

    public static NetManager getInstance() {
        if (m_NetMInstante == null) {
            m_NetMInstante = new NetManager();
        }
        return m_NetMInstante;
    }

    private NetManager() {
    }

    public void makeStringRequest(String url, final StringRequestListener stringRequestListener) {
        this.queue.add(new UTF8StringRequest(0, url, new Listener<String>() {
            public void onResponse(String response) {
                Log.d("liveTV", "Response is " + response);
                stringRequestListener.onCompleted(response);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                stringRequestListener.onError();
            }
        }));
    }

    public String makeSyncStringRequest(String url) {
        return makeSyncStringRequest(url, 10);
    }

    public String makeSyncStringRequest(String url, int timeOutSeconds) {
        RequestFuture<String> future = RequestFuture.newFuture();
        this.queue.add(new UTF8StringRequest(0, url, future, future));
        try {
            return (String) future.get((long) timeOutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            return null;
        }
    }

    public String makeSearchStringRequest(String url, int timeOutSeconds) {
        RequestFuture<String> future = RequestFuture.newFuture();
        UTF8StringRequest stringRequest = new UTF8StringRequest(0, url, future, future);
        this.searchQueue.cancelAll((RequestFilter) new RequestFilter() {
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        this.searchQueue.add(stringRequest);
        try {
            return (String) future.get((long) timeOutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            return null;
        }
    }

    public void performLogin(String usr, String pss, StringRequestListener stringRequestListener) {
        LiveTVServicesManual.performLogin(usr, pss, stringRequestListener).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Subscriber<Boolean>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }
            public void onNext(Boolean result) {
            }
        });
    }

    public void addRecent(String type, String cve, final StringRequestListener stringRequestListener) {
        LiveTVServicesManual.addRecent(type, cve, stringRequestListener)
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean result) {

                    }
                });
    }

    public void performGetCode(StringRequestListener stringRequestListener) {
        LiveTVServicesManual.performGetCode(stringRequestListener).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Subscriber<Boolean>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }

            public void onNext(Boolean result) {
            }
        });
    }

    public void performLoginCode(String code, StringRequestListener stringRequestListener) {
        LiveTVServicesManual.performLoginCode(code, stringRequestListener).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Subscriber<Boolean>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }

            public void onNext(Boolean result) {
            }
        });
    }

    public void performChangePassCode(String user, String password, String code, StringRequestListener stringRequestListener) {
        LiveTVServicesManual.performChangePassCode(user, password, code, stringRequestListener).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Subscriber<Boolean>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }

            public void onNext(Boolean result) {
            }
        });
    }

    public Observable<List<? extends VideoStream>> searchVideo(MainCategory mainCategory, String pattern) {
        return LiveTVServicesManual.searchVideo(mainCategory, pattern, 45).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void performCheckForUpdate(StringRequestListener stringRequestListener) {
        LiveTVServicesManual.performCheckForUpdate(stringRequestListener).delay(2, TimeUnit.SECONDS, Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Subscriber<Boolean>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }

            public void onNext(Boolean result) {
            }
        });
    }

    public void retrieveLiveTVPrograms(MainCategory mainCategory, final LoadProgramsForLiveTVCategoryResponseListener liveTVCategoryResponseListener) {
        LiveTVServicesManual.getLiveTVCategories(mainCategory).subscribe( new Subscriber<List<LiveTVCategory>>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
                liveTVCategoryResponseListener.onError();
            }

            public void onNext(List<LiveTVCategory> liveTVCategories) {
                if (liveTVCategories == null || liveTVCategories.size() == 0) {
                    liveTVCategoryResponseListener.onError();
                    return;
                }
                List<Observable<LiveTVCategory>> observableList = new ArrayList<>();
                for (LiveTVCategory cat : liveTVCategories) {
                    observableList.add(LiveTVServicesManual.getProgramsForLiveTVCategory(cat));
                }
                VideoStreamManager.getInstance().resetLiveTVCategory(liveTVCategories.size());
                Observable.mergeDelayError(observableList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( new Observer<LiveTVCategory>() {
                    public void onCompleted() {
                        liveTVCategoryResponseListener.onProgramsForLiveTVCategoriesCompleted();
                    }

                    public void onError(Throwable e) {
                        liveTVCategoryResponseListener.onProgramsForLiveTVCategoryError(null);
                    }

                    public void onNext(LiveTVCategory liveTVCategory) {
                        liveTVCategoryResponseListener.onProgramsForLiveTVCategoryCompleted(liveTVCategory);
                    }
                });
            }
        });
    }

    public void retrieveSubCategories(final MainCategory mainCategory, final LoadSubCategoriesResponseListener subCategoriesResponseListener) {
        LiveTVServicesManual.getSubCategories(mainCategory).subscribe( new Subscriber<List<MovieCategory>>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
                subCategoriesResponseListener.onSubCategoriesLoadedError();
            }

            public void onNext(List<MovieCategory> movieCategories) {
                subCategoriesResponseListener.onSubCategoriesLoaded(mainCategory, movieCategories);
            }
        });
    }

    public void retrieveMoviesForSubCategory(final MainCategory mainCategory, final MovieCategory movieCategory, final LoadMoviesForCategoryResponseListener listener, int timeOut) {
        LiveTVServicesManual.getMoviesForSubCat(mainCategory.getModelType(), movieCategory.getCatName(), timeOut).subscribe( new Subscriber<List<? extends VideoStream>>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
                movieCategory.setErrorLoading(true);
                listener.onMoviesForCategoryCompletedError(movieCategory);
            }

            public void onNext(List<? extends VideoStream> movies) {
                for (VideoStream video : movies) {
                    if (!(video instanceof Serie)) {
                        break;
                    }
                    ((Serie) video).setMovieCategoryIdOwner(movieCategory.getId());
                }
                if (Device.canTreatAsBox() && movieCategory.getCatName().contains("ettings")) {
                    movieCategory.setCatName("");
                }
                mainCategory.getMovieCategory(movieCategory.getId()).setMovieList(movies);
                listener.onMoviesForCategoryCompleted(mainCategory.getMovieCategory(movieCategory.getId()));
            }
        });
    }

    public void retrieveSeasons(final Serie serie, final LoadSeasonsForSerieResponseListener seriesListener) {
        LiveTVServicesManual.getSeasonsForSerie(serie).subscribe( new Subscriber<Integer>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
                seriesListener.onError();
            }

            public void onNext(Integer seasonCount) {
                seriesListener.onSeasonsLoaded(serie, seasonCount.intValue());
            }
        });
    }

    public void retrieveEpisodesForSerie(Serie serie, final Season season, final LoadEpisodesForSerieResponseListener episodesForSerieResponseListener) {
        LiveTVServicesManual.getEpisodesForSerie(serie, Integer.valueOf(season.getPosition() + 1)).subscribe( new Subscriber<List<? extends VideoStream>>() {
            public void onCompleted() {
            }

            public void onError(Throwable e) {
                episodesForSerieResponseListener.onError();
            }

            public void onNext(List<? extends VideoStream> movies) {
                season.setEpisodeList(movies);
                episodesForSerieResponseListener.onEpisodesForSerieCompleted(season);
            }
        });
    }
}
