package com.example.holys.light;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.holys.light.Tabs.CultureFrag;
import com.example.holys.light.Tabs.LifeStyleFrag;
import com.example.holys.light.Tabs.ScienceFrag;
import com.example.holys.light.Tabs.SportFrag;
import com.example.holys.light.Tabs.TabsFragment;

/**
 * Created by holys on 2/23/2016.
 */
public class TabsAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public TabsAdapter (FragmentManager fm, int numbTabs) {
        super(fm);
        this.numTabs = numbTabs;
    }

    @Override
    public int getCount () {
        return numTabs;
    }

    @Override
    public Fragment getItem (int position) {
        switch (position)
        {
            case 0:
                return new LifeStyleFrag();
            case 1:
                return new ScienceFrag();
            case 2:
                return new SportFrag();
            case 3:
                return new CultureFrag();
            case 4:
                 return new TabsFragment();
            default:
                return null;
        }

    }
}
