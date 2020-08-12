package com.livetv.android.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.livetv.android.R;
import com.livetv.android.databinding.AccountDetailsFragmentBinding;
import com.livetv.android.listeners.DialogListener;
import com.livetv.android.listeners.StringRequestListener;
import com.livetv.android.model.User;
import com.livetv.android.utils.Connectivity;
import com.livetv.android.utils.DataManager;
import com.livetv.android.utils.Device;
import com.livetv.android.utils.Dialogs;
import com.livetv.android.utils.networking.NetManager;
import com.livetv.android.utils.networking.WebConfig;
import com.livetv.android.viewmodel.AccountDetailsViewModelContract.View;
import com.livetv.android.viewmodel.AccountDetailsViewModelContract.ViewModel;

public class AccountDetailsViewModel implements ViewModel {
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableBoolean isTV = new ObservableBoolean(Device.canTreatAsBox());
    private final Activity mActivity;
    /* access modifiers changed from: private */
    public View viewCallback;

    public AccountDetailsViewModel(Activity activity) {
        this.mActivity = activity;
    }

    public void onViewResumed() {
    }

    public void onViewAttached(@NonNull Lifecycle.View viewCallback2) {
        this.viewCallback = (View) viewCallback2;
    }

    public void onViewDetached() {
        this.viewCallback = null;
    }

    public void onGoToMenu(android.view.View view) {
        this.viewCallback.onError();
    }

    public void onCloseSession(android.view.View view) {
        if (Device.canTreatAsBox()) {
            Dialogs.showTwoButtonsDialog(this.mActivity, (int) R.string.accept, (int) R.string.cancel, (int) R.string.end_session_message, (DialogListener) new DialogListener() {
                public void onAccept() {
                    AccountDetailsViewModel.this.closeSession();
                }

                public void onCancel() {
                }
            });
        } else {
            closeSession();
        }
    }

    /* access modifiers changed from: private */
    public void closeSession() {
        if (Connectivity.isConnected()) {
            this.isLoading.set(true);
            String theUser = DataManager.getInstance().getString("theUser", "");
            if (!TextUtils.isEmpty(theUser)) {
                NetManager.getInstance().makeStringRequest(WebConfig.removeUserURL.replace("{USER}", ((User) new Gson().fromJson(theUser, User.class)).getName()), new StringRequestListener() {
                    public void onCompleted(String response) {
                        if (response.toLowerCase().contains("success")) {
                            AccountDetailsViewModel.this.viewCallback.onCloseSessionSelected();
                        }
                    }

                    public void onError() {
                        AccountDetailsViewModel.this.isLoading.set(false);
                    }
                });
                return;
            }
            return;
        }
        this.viewCallback.onCloseSessionNoInternet();
    }

    public void showAccountDetails(AccountDetailsFragmentBinding accountDetailsFragmentBinding) {
        String theUser = DataManager.getInstance().getString("theUser", "");
        if (!TextUtils.isEmpty(theUser)) {
            accountDetailsFragmentBinding.setUser((User) new Gson().fromJson(theUser, User.class));
        } else {
            this.viewCallback.onError();
        }
    }
}
