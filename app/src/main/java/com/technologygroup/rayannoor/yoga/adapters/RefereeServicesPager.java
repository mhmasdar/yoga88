package com.technologygroup.rayannoor.yoga.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.technologygroup.rayannoor.yoga.referees.refBioFragment;
import com.technologygroup.rayannoor.yoga.referees.refCertificateFragment;
import com.technologygroup.rayannoor.yoga.referees.refCourseFragment;
import com.technologygroup.rayannoor.yoga.referees.refEducationFragment;
import com.technologygroup.rayannoor.yoga.referees.refResumeFragment;

public class RefereeServicesPager extends FragmentStatePagerAdapter {
    Bundle bundle;

    public RefereeServicesPager(FragmentManager fm,boolean calledFromPanel,int idCoach ,String Bio) {
        super(fm);
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
                refResumeFragment res = new refResumeFragment();
                res.setArguments(bundle);
                return res;

            case 1:
                refBioFragment bio = new refBioFragment();
                bio.setArguments(bundle);
                return bio;

            case 2:
                refCertificateFragment cer = new refCertificateFragment();
                cer.setArguments(bundle);
                return cer;

            case 3:
                refEducationFragment edu = new refEducationFragment();
                edu.setArguments(bundle);
                return edu;

            case 4:
                refCourseFragment course = new refCourseFragment();
                course.setArguments(bundle);
                return course;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}

