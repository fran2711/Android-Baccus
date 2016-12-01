package com.fran.baccus.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fran.baccus.R;
import com.fran.baccus.controller.fragment.WineListFragment;
import com.fran.baccus.controller.fragment.WineryFragment;


public class WineListActivity extends AppCompatActivity implements WineListFragment.OnWineSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        FragmentManager fm = getSupportFragmentManager();

        if (findViewById(R.id.list) != null) {
            Fragment listFragment = fm.findFragmentById(R.id.list);
            if (listFragment == null) {
                listFragment = new WineListFragment();
                fm.beginTransaction().add(R.id.list, listFragment).commit();
            }
        }

        if (findViewById(R.id.winery) != null) {
            Fragment wineryFragment = fm.findFragmentById(R.id.winery);
            if (wineryFragment == null) {
                wineryFragment = WineryFragment.newInstance(PreferenceManager.getDefaultSharedPreferences(this).getInt(WineryFragment.PREF_LAST_WINE_INDEX, 0));
                fm.beginTransaction().add(R.id.winery, wineryFragment).commit();
            }
        }
    }

    @Override
    public void onWineSelected(int wineIndex) {
        WineryFragment wineryFragment = (WineryFragment) getSupportFragmentManager().findFragmentById(R.id.winery);

        if (wineryFragment != null) {
            wineryFragment.changeWine(wineIndex);
        }
        else {
            Intent wineryIntent = new Intent(this, WineryActivity.class);
            wineryIntent.putExtra(WineryActivity.EXTRA_WINE_INDEX, wineIndex);
            startActivity(wineryIntent);
        }
    }
}
