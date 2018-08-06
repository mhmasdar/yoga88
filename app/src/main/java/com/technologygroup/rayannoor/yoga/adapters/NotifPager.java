package com.technologygroup.rayannoor.yoga.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.technologygroup.rayannoor.yoga.Notification.KaryabFragment;
import com.technologygroup.rayannoor.yoga.Notification.notifsFragment;
import com.technologygroup.rayannoor.yoga.Notification.NewsFragment;

public class NotifPager extends FragmentStatePagerAdapter {



    public NotifPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                NewsFragment edu = new NewsFragment();
                return edu;

            case 1:
                notifsFragment res = new notifsFragment();
                return res;

            case 2:
                KaryabFragment gym = new KaryabFragment();
                return gym;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
