package com.technologygroup.rayannoor.yoga.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.technologygroup.rayannoor.yoga.Gyms.aboutFragment;
import com.technologygroup.rayannoor.yoga.Gyms.HonourFragment;
import com.technologygroup.rayannoor.yoga.Gyms.clipFragment;
import com.technologygroup.rayannoor.yoga.Gyms.coachsFragment;
import com.technologygroup.rayannoor.yoga.Gyms.coursesFragment;
import com.technologygroup.rayannoor.yoga.Gyms.galleryFragment;
import com.technologygroup.rayannoor.yoga.Gyms.notifFragment;
import com.technologygroup.rayannoor.yoga.Gyms.workTimeFragment;
import com.technologygroup.rayannoor.yoga.Notification.notifsFragment;

/**
 * Created by Mohamad Hasan on 3/5/2018.
 */

public class GymServicesPager extends FragmentStatePagerAdapter {

    private boolean calledFromPanel;
    private int idGym;
    Bundle bundle;

    public GymServicesPager(FragmentManager fm, boolean calledFromPanel, int idGym) {
        super(fm);

        this.calledFromPanel = calledFromPanel;
        this.idGym = idGym;

        bundle = new Bundle();
        bundle.putBoolean("calledFromPanel", calledFromPanel);
        bundle.putInt("idGym", idGym);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                HonourFragment hno = new HonourFragment();
                hno.setArguments(bundle);
                return hno;

                case 1:
                    aboutFragment about = new aboutFragment();
                    about.setArguments(bundle);
                    return about;

            case 2:

                clipFragment gal = new clipFragment();
                gal.setArguments(bundle);
                return gal;

            case 3:
                galleryFragment gym = new galleryFragment();
                gym.setArguments(bundle);
                return gym;

            case 4:
                coachsFragment coa = new coachsFragment();
                coa.setArguments(bundle);
                return coa;

            case 5:
                coursesFragment cer = new coursesFragment();
                cer.setArguments(bundle);
                return cer;

            case 6:
                workTimeFragment info = new workTimeFragment();
                info.setArguments(bundle);
                return info;

            case 7:
                notifFragment notif = new notifFragment();
                notif.setArguments(bundle);
                return notif;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 8;
    }

}

