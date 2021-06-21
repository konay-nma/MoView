package com.konay.moview.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.konay.moview.R;
import com.konay.moview.models.Slide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private final List<Slide> mList;
    private final Context mContex;

public SliderPagerAdapter(Context context, List<Slide> slides){
    this.mContex = context;
    this.mList = slides;
}
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContex.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View slideLayout = layoutInflater.inflate (R.layout.slide_item, null);
        ImageView slideImage = slideLayout.findViewById (R.id.slide_image);
        TextView slideTitle = slideLayout.findViewById (R.id.slide_title);
        // set image resource and title
        String imageUrl = mList.get (position).getImage ();
        slideTitle.setText (mList.get(position).getTitle ());
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(slideImage);
        container.addView (slideLayout); // add slide layout view to container
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size ();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView ((View)object);
    }
}
