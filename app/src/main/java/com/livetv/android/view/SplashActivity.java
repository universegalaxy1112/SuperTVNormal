package com.livetv.android.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.livetv.android.R;
import com.livetv.android.utils.Device;
import com.livetv.android.utils.Tracking;
import com.livetv.android.utils.networking.services.HttpRequest;

public class SplashActivity extends BaseActivity {
    private SplashFragment splashFragment;

    public BaseFragment getFragment() {
        return this.splashFragment;
    }

    public Fragment getTVFragment() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Device.setHDMIStatus();
        HttpRequest.getInstance().trustAllHosts();
        setContentView((int) R.layout.splash_activity);
        this.splashFragment = new SplashFragment();
        getSupportFragmentManager().beginTransaction().add((int) R.id.splash_container,  this.splashFragment).commit();

    }
}
