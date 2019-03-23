package com.satish.mvvmapp.ui.home;

import com.satish.mvvmapp.base.BaseViewModel;
import com.satish.mvvmapp.data.AppDatabase;
import com.satish.mvvmapp.network.WebService;
import com.satish.mvvmapp.network.model.MenuItem;
import com.satish.mvvmapp.network.model.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends BaseViewModel<MainNavigator> {

    public MainActivityViewModel(AppDatabase appDatabase, WebService webService) {
        super(appDatabase, webService);
    }

    public void syncMenu() {
        getCompositeDisposable().add(
                getMoviesObservable().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<List<Movie>, ObservableSource<Movie>>() {

                            @Override
                            public ObservableSource<Movie> apply(List<Movie> movies) throws Exception {
                                return Observable.fromIterable(movies);
                            }
                        })
                        .flatMap(new Function<Movie, ObservableSource<List<MenuItem>>>() {

                            @Override
                            public ObservableSource<List<MenuItem>> apply(Movie movie) throws Exception {
                                return getMenuItesmObservable(movie.getTitle());
                            }
                        })
                        .subscribe()
        );
    }

    public Observable<List<Movie>> getMoviesObservable() {
        return getWebClient().getMovies()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<MenuItem>> getMenuItesmObservable(String title) {
        return getWebClient().getMenu()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getMenuItems() {
        // fetching from db
        getNavigator().onMenuItemsReceived(getAppDatabase().getMenuItems());

        getCompositeDisposable().add(
                getWebClient().getMenu()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(o -> setIsLoading(true))
                        .doFinally(() -> setIsLoading(false))
                        .subscribe(menuItems -> getNavigator().onMenuItemsReceived(menuItems),
                                throwable -> Timber.e(throwable, "Get menu items error!"))
        );

        /*
        setIsLoading(true);
        Call<List<MenuItem>> call = getWebClient().getMenu();
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, final Response<List<MenuItem>> response) {
                setIsLoading(false);
                if (!response.isSuccessful()) {
                    return;
                }
                // storing menu items
                getAppDatabase().insertMenuItems(response.body());

                new Handler().postDelayed(() -> {
                    getNavigator().onMenuItemsReceived(response.body());
                }, 2000);
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                setIsLoading(false);
                getNavigator().onApiError(t);
            }
        });*/
    }

    public void getMovies() {
        getCompositeDisposable().add(
                getWebClient().getMovies()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(o -> setIsLoading(true))
                        .doFinally(() -> setIsLoading(false))
                        .subscribe(movies -> getNavigator().onMoviesReceived(movies),
                                throwable -> Timber.e(throwable, "Get Movies error!"))
        );


        /*call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                setIsLoading(false);
                if (!response.isSuccessful()) {
                    return;
                }

                getNavigator().onMoviesReceived(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                setIsLoading(false);
            }
        });*/
    }

    public void loadAnimals() {
        Observable<String> animalsObservable = Observable.just("Ant", "Buffalo", "Bee", "Cat", "Dog", "Fox");

        DisposableObserver<String> animalObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Timber.e("onNext: %s", s);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("onNext: %s", e.getMessage());
            }

            @Override
            public void onComplete() {
                Timber.e("onComplete");
            }
        };

        getCompositeDisposable().add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(animalObserver));
    }
}
