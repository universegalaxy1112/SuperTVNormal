package com.livetv.android.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.livetv.android.R;
import com.livetv.android.listeners.DialogListener;
import com.livetv.android.utils.Connectivity;
import com.livetv.android.utils.DataManager;
import com.livetv.android.utils.Dialogs;
import com.livetv.android.utils.networking.services.HttpRequest;
import com.livetv.android.viewmodel.Lifecycle;
import com.livetv.android.viewmodel.SplashViewModel;
import com.livetv.android.viewmodel.SplashViewModelContract;

import java.io.File;

public class SplashFragment extends BaseFragment implements SplashViewModelContract.View {
    public static final int BLOCKED_OR_NEVER_ASKED = 2;
    public static final int DENIED = 1;
    public static final int GRANTED = 0;
    static final int INSTALL_REQUEST_CODE = 1;
    private static final int REQUEST_STORAGE_STATE = 1;
    boolean denyAll = false;
    /* access modifiers changed from: private */
    public ProgressDialog downloadProgress;
    private boolean isInit = false;
    private int serieId;
    /* access modifiers changed from: private */
    public SplashViewModel splashViewModel;
    private String updateLocation = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpRequest.getInstance().trustAllHosts();
        this.splashViewModel = new SplashViewModel();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged() {
    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.splash_fragment, container, false);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        if (!this.isInit) {
            this.splashViewModel.login();
            this.isInit = true;
        }
    }

    /* access modifiers changed from: protected */
    public Lifecycle.ViewModel getViewModel() {
        return this.splashViewModel;
    }

    /* access modifiers changed from: protected */
    public Lifecycle.View getLifecycleView() {
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        Dialogs.showCantGoBackToast();
        return true;
    }

    public void onLoginCompleted(boolean success) {
        if(success){
            launchActivity(MainCategoriesMenuActivity.class);
            finishActivity();
        }
        else{
            if(Connectivity.isConnected()){
                launchActivity(LoginActivity.class);
                finishActivity();
            }
            else{

                noInternetConnection(new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishActivity();
                    }
                });
            }
        }
    }

    public void onCheckForUpdateCompleted(boolean hasNewVersion, final String location) {
        this.updateLocation = location;
        if (hasNewVersion) {
            Resources res = getResources();
            Dialogs.showTwoButtonsDialog((Activity) getActivity(), (int) R.string.download, (int) R.string.cancel, getResources().getString(R.string.new_version_available), (DialogListener) new DialogListener() {
                public void onAccept() {
                    if (SplashFragment.this.getPermissionStatus("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        SplashFragment.this.requestStoragePermission();
                    } else if (Connectivity.isConnected()) {
                        SplashFragment.this.downloadProgress = new ProgressDialog(SplashFragment.this.getActivity());
                        SplashFragment.this.downloadProgress.setProgressStyle(1);
                        SplashFragment.this.downloadProgress.setMessage("Downloading");
                        SplashFragment.this.downloadProgress.setIndeterminate(false);
                        SplashFragment.this.downloadProgress.setCancelable(false);
                        SplashFragment.this.downloadProgress.show();
                        SplashFragment.this.splashViewModel.downloadUpdate(location, SplashFragment.this.downloadProgress);
                    } else {
                        SplashFragment.this.goToNoConnectionError();
                    }
                }

                public void onCancel() {
                    SplashFragment.this.getActivity().finish();
                }
            });
            return;
        }
        launchActivity(MainCategoriesMenuActivity.class);
        getActivity().finish();
    }

    public void onDownloadUpdateCompleted(String location) {
        this.downloadProgress.dismiss();
        try
        {
            File file = new File(location);
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri downloaded_apk = getFileUri(getActivity(), file);
                intent = new Intent(Intent.ACTION_VIEW).setDataAndType(downloaded_apk,
                        "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else {
                intent = new Intent("android.intent.action.INSTALL_PACKAGE");
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            intent.putExtra("android.intent.extra.RETURN_RESULT", false);
            intent.putExtra("android.intent.extra.INSTALLER_PACKAGE_NAME", getActivity().getPackageName());
            finishActivity();
            startActivity(intent);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    Uri getFileUri(Context context, File file) {
        return FileProvider.getUriForFile(context,
                "com.uni.julio.supertv.fileprovider"
                , file);
    }
    public void onDownloadUpdateError(int error) {
        this.downloadProgress.dismiss();
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SplashFragment.this.getActivity().finish();
            }
        };
        switch (error) {
            case 1:
                Dialogs.showOneButtonDialog((Activity) getActivity(), (int) R.string.verify_unknown_sources, listener);
                return;
            default:
                Dialogs.showOneButtonDialog((Activity) getActivity(), (int) R.string.new_version_generic_error_message, listener);
                return;
        }
    }

    /* access modifiers changed from: private */
    public void goToNoConnectionError() {
        noInternetConnection(new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SplashFragment.this.launchActivity(LoginActivity.class);
                SplashFragment.this.getActivity().finish();
            }
        });
    }

    public int getPermissionStatus(String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(getActivity(), androidPermissionName) == 0) {
            return 0;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), androidPermissionName) || !DataManager.getInstance().getBoolean("storagePermissionRequested", false)) {
            return 1;
        }
        return 2;
    }

    public boolean requestStoragePermission() {
        if (VERSION.SDK_INT < 23 || getPermissionStatus("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        this.denyAll = false;
        int accept = R.string.accept;
        int message = R.string.permission_storage;
        if (getPermissionStatus("android.permission.WRITE_EXTERNAL_STORAGE") == 2) {
            this.denyAll = true;
            accept = R.string.config;
            message = R.string.permission_storage_config;
        }
        Dialogs.showTwoButtonsDialog((Activity) getActivity(), accept, (int) R.string.cancel, message, (DialogListener) new DialogListener() {
            @TargetApi(23)
            public void onAccept() {
                if (!SplashFragment.this.denyAll) {
                    DataManager.getInstance().saveData("storagePermissionRequested", Boolean.valueOf(true));
                    SplashFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                    return;
                }
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", SplashFragment.this.getActivity().getPackageName(), null));
                SplashFragment.this.startActivityForResult(intent, 4168);
            }

            public void onCancel() {
                SplashFragment.this.finishActivity();
                Process.killProcess(Process.myPid());
            }
        });
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 4168) {
            return;
        }
        if (getPermissionStatus("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            downloadUpdate(this.updateLocation);
        } else {
            requestStoragePermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }
        if (getPermissionStatus("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            downloadUpdate(this.updateLocation);
        } else {
            requestStoragePermission();
        }
    }

    private void downloadUpdate(String location) {
        if (Connectivity.isConnected()) {
            this.downloadProgress = new ProgressDialog(getActivity());
            this.downloadProgress.setProgressStyle(1);
            this.downloadProgress.setMessage("Downloading");
            this.downloadProgress.setIndeterminate(false);
            this.downloadProgress.setCancelable(false);
            this.downloadProgress.show();
            this.splashViewModel.downloadUpdate(location, this.downloadProgress);
            return;
        }
        goToNoConnectionError();
    }
}
