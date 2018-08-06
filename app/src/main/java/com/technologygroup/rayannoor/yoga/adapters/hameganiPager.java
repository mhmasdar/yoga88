package com.technologygroup.rayannoor.yoga.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.technologygroup.rayannoor.yoga.Coaches.bioFragment;
import com.technologygroup.rayannoor.yoga.Coaches.certificateFragment;
import com.technologygroup.rayannoor.yoga.Coaches.educationFragment;
import com.technologygroup.rayannoor.yoga.Coaches.resumeFragment;
import com.technologygroup.rayannoor.yoga.hameganiFragment1;
import com.technologygroup.rayannoor.yoga.hameganiFragment2;
import com.technologygroup.rayannoor.yoga.hameganiFragment3;
import com.technologygroup.rayannoor.yoga.hameganiFragment4;

public class hameganiPager extends FragmentStatePagerAdapter {




    public hameganiPager(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                hameganiFragment1 res = new hameganiFragment1();

                return res;

            case 1:
                hameganiFragment2 bio = new hameganiFragment2();

                return bio;

            case 2:
                hameganiFragment3 cer = new hameganiFragment3();

                return cer;

            case 3:
                hameganiFragment4 edu = new hameganiFragment4();

                return edu;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}

