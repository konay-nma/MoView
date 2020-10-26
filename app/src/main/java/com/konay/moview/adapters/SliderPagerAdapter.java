package com.konay.moview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.konay.moview.R;
import com.konay.moview.models.Slide;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private List<Slide> mList;
    private Context mContex;

public SliderPagerAdapter(Context context, List<Slide> slides){
    this.mContex = context;
    this.mList = slides;
}
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContex.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View slideLayout = layoutInflater.inflate (R.layout.slide_item, null);
        ImageView slideImage = slideLayout.findViewById (R.id.slide_image);
        TextView slideTitle = slideLayout.findViewById (R.id.slide_title);
        // set image resource and title
        String imageUrl = mList.get (position).getImage ();
        slideTitle.setText (mList.get(position).getTitle ());
        Glide.with (mContex)
                .load (imageUrl)
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
