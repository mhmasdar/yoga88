package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadHasan on 20/11/2017.
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private LayoutInflater inflater;
    private Context context;
    private List<Integer> images = new ArrayList<>();


    public SlidingImage_Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);

        if (position == 0)
            Glide.with(context).load(R.drawable.home1).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

        else if (position == 1)
            Glide.with(context).load(R.drawable.home2).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);

        else if (position == 2)
            Glide.with(context).load(R.drawable.home3).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);


        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //click listener
            }
        });

        return imageLayout;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
