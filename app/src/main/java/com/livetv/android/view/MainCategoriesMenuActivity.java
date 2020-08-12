package com.livetv.android.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.livetv.android.R;
import com.livetv.android.utils.Screen;

public class MainCategoriesMenuActivity extends BaseActivity {
    private MainCategoriesMenuFragment mainCategoriesMenuFragment;

    public BaseFragment getFragment() {
        return this.mainCategoriesMenuFragment;
    }

    public Fragment getTVFragment() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.main_categories_menu_activity);
        this.mainCategoriesMenuFragment = new MainCategoriesMenuFragment();
        getSupportFragmentManager().beginTransaction().add((int) R.id.main_categories_menu_container,  this.mainCategoriesMenuFragment).commit();
        Screen.SetScreenDimensions(this);
    }
}
