package com.livetv.android.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import com.livetv.android.R;
import com.livetv.android.databinding.AccountDetailsFragmentBinding;
import com.livetv.android.utils.DataManager;
import com.livetv.android.utils.Dialogs;
import com.livetv.android.viewmodel.AccountDetailsViewModel;
import com.livetv.android.viewmodel.AccountDetailsViewModelContract;
import com.livetv.android.viewmodel.Lifecycle;

public class AccountDetailsFragment extends BaseFragment implements AccountDetailsViewModelContract.View, OnFocusChangeListener {
    private AccountDetailsFragmentBinding accountDetailsFragmentBinding;
    private AccountDetailsViewModel accountDetailsViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.accountDetailsViewModel = new AccountDetailsViewModel(getActivity());
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged() {
        setupUI();
    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.accountDetailsFragmentBinding = (AccountDetailsFragmentBinding) DataBindingUtil.inflate(inflater, R.layout.account_details_fragment, container, false);
        this.accountDetailsFragmentBinding.setAccountDetailsVM(this.accountDetailsViewModel);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.accountDetailsFragmentBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) "Mi Cuenta");
        if (this.accountDetailsFragmentBinding.closeSetting != null) {
            this.accountDetailsFragmentBinding.closeSetting.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(android.view.View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.setSelected(true);
                    } else {
                        v.setSelected(false);
                    }
                }
            });
        }
        if (this.accountDetailsFragmentBinding.endSessionButton != null) {
            this.accountDetailsFragmentBinding.endSessionButton.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(android.view.View v, boolean hasFocus) {
                    if (hasFocus) {
                        v.setSelected(true);
                    } else {
                        v.setSelected(false);
                    }
                }
            });
        }
        this.accountDetailsViewModel.showAccountDetails(this.accountDetailsFragmentBinding);
        return this.accountDetailsFragmentBinding.getRoot();
    }

    private void setupUI() {
        ViewGroup viewGroup = (ViewGroup) getView();
        viewGroup.removeAllViewsInLayout();
        this.accountDetailsFragmentBinding = (AccountDetailsFragmentBinding) DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.account_details_fragment, viewGroup, false);
        this.accountDetailsFragmentBinding.setAccountDetailsVM(this.accountDetailsViewModel);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.accountDetailsFragmentBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) "Mi Cuenta");
        this.accountDetailsViewModel.showAccountDetails(this.accountDetailsFragmentBinding);
        viewGroup.addView(this.accountDetailsFragmentBinding.getRoot());
    }

    /* access modifiers changed from: protected */
    public Lifecycle.ViewModel getViewModel() {
        return this.accountDetailsViewModel;
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
        finishActivity();
        return true;
    }

    public void onFocusChange(android.view.View v, boolean hasFocus) {
        if (!v.isSelected()) {
            v.setSelected(true);
        } else {
            v.setSelected(false);
        }
    }

    public void onCloseSessionSelected() {
        DataManager.getInstance().saveData("theUser", "");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishActivity();
    }

    public void onCloseSessionNoInternet() {
        Dialogs.showOneButtonDialog((Activity) getActivity(), (int) R.string.no_connection_title, (int) R.string.close_session_no_internet, (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }

    public void onError() {
        finishActivity();
    }
}
