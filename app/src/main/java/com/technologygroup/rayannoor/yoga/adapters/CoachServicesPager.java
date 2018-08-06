package com.technologygroup.rayannoor.yoga.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.technologygroup.rayannoor.yoga.Coaches.bioFragment;
import com.technologygroup.rayannoor.yoga.Coaches.certificateFragment;
import com.technologygroup.rayannoor.yoga.Coaches.courseFragment;
import com.technologygroup.rayannoor.yoga.Coaches.educationFragment;
import com.technologygroup.rayannoor.yoga.Coaches.gymsFragment;
import com.technologygroup.rayannoor.yoga.Coaches.karyabiFragment;
import com.technologygroup.rayannoor.yoga.Coaches.resumeFragment;
import com.technologygroup.rayannoor.yoga.Coaches.teachesFragment;

/**
 * Created by Mohamad Hasan on 2/12/2018.
 */

public class CoachServicesPager extends FragmentStatePagerAdapter {

    private boolean calledFromPanel;
    private int idCoach;
    Bundle bundle;
    String Bio;

    public CoachServicesPager(FragmentManager fm, boolean calledFromPanel, int idCoach,String Bio) {
        super(fm);
        this.calledFromPanel = calledFromPanel;
        this.idCoach = idCoach;
        this.Bio=Bio;
        bundle = new Bundle();
        bundle.putBoolean("calledFromPanel", calledFromPanel);
        bundle.putInt("idCoach", idCoach);
        bundle.putString("Bio", Bio);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                resumeFragment res = new resumeFragment();
                res.setArguments(bundle);
                return res;

            case 1:
                bioFragment bio = new bioFragment();
                bio.setArguments(bundle);
                return bio;

            case 2:
                certificateFragment cer = new certificateFragment();
                cer.setArguments(bundle);
                return cer;

            case 3:
                educationFragment edu = new educationFragment();
                edu.setArguments(bundle);
                return edu;

            case 4:
                courseFragment course = new courseFragment();
                course.setArguments(bundle);
                return course;


                case 5:
                    gymsFragment gym = new gymsFragment();
                    gym.setArguments(bundle);
                    return gym;


            case 6:
                teachesFragment te = new teachesFragment();
                te.setArguments(bundle);
                return te;

                case 7:
                    karyabiFragment ka = new karyabiFragment();
                    ka.setArguments(bundle);
                return ka;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 8;
    }

}
