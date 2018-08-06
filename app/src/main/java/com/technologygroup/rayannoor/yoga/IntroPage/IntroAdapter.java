package com.technologygroup.rayannoor.yoga.IntroPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by mohamadHasan on 20/07/2017.
 */

public class IntroAdapter extends FragmentPagerAdapter {


    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(position);
            case 1:
                return IntroFragment.newInstance(position);
            case 2:
                return IntroFragment.newInstance(position);
            case 3:
                return IntroFragment.newInstance(position);
            case 4:
                return IntroFragment.newInstance(position);
            case 5:
                return IntroFragment.newInstance(position);
            default:
                return IntroFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

}