package com.livetv.android.viewmodel;

import com.livetv.android.databinding.AccountDetailsFragmentBinding;

public interface AccountDetailsViewModelContract {

    public interface View extends com.livetv.android.viewmodel.Lifecycle.View {
        void onCloseSessionNoInternet();

        void onCloseSessionSelected();

        void onError();
    }

    public interface ViewModel extends com.livetv.android.viewmodel.Lifecycle.ViewModel {
        void showAccountDetails(AccountDetailsFragmentBinding accountDetailsFragmentBinding);
    }
}
