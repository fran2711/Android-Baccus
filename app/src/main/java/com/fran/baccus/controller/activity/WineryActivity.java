package com.fran.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.fran.baccus.controller.fragment.WineryFragment;


public class WineryActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE_INDEX = "com.fran.baccus.controller.activity.WineryActivity.EXTRA_WINE_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected Fragment createFragment() {
        return WineryFragment.newInstance(getIntent().getIntExtra(EXTRA_WINE_INDEX, 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}







