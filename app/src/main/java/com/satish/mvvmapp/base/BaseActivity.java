package com.satish.mvvmapp.base;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.satish.mvvmapp.R;
import com.satish.mvvmapp.utils.NoConnectivityEvent;
import com.satish.mvvmapp.utils.UnauthorizedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    public void setContentView(final int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);

        FrameLayout activityContainer = view.findViewById(R.id.activity_content_holder);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(view);
        ButterKnife.bind(this);
    }

    public void enableToolbarBackNavigation() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showNoConnectivitySnack() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(rootView, getString(R.string.no_internet), Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void handleApiError(Throwable throwable) {
        // TODO - handle network error
        Timber.e("Network error:  %s", throwable.getMessage());
    }

    @Subscribe
    public final void onNoConnectivityEvent(NoConnectivityEvent e) {
        showNoConnectivitySnack();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public final void onUnauthorizedEvent(UnauthorizedEvent e) {
        handleUnauthorizedEvent();
    }

    protected void handleUnauthorizedEvent() {
        // TODO - handle 403
        // logout();
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
