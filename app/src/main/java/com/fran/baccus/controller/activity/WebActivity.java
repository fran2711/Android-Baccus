package com.fran.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.fran.baccus.controller.fragment.WebFragment;


public class WebActivity extends FragmentContainerActivity {

    public static final String EXTRA_WINE = "com.fran.baccus.controller.activity.WebActivity.extra_wine";


    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WebFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));

        WebFragment fragment = new WebFragment();
        fragment.setArguments(arguments);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return fragment;
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
