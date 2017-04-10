package com.example.luan.findmovie.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.luan.findmovie.R;



public class SliderMainAdapter extends PagerAdapter {

    public List<String> images;
    public LayoutInflater inflater;
    public Context context;
    private OnSliderClickListener onSliderClickListener;

    public OnSliderClickListener getOnSliderClickListener() {
        return onSliderClickListener;
    }

    public void setOnSliderClickListener(OnSliderClickListener onSliderClickListener) {
        this.onSliderClickListener = onSliderClickListener;
    }


    public SliderMainAdapter(Context context, List<String> images){
        this.context = context;
        this.images = images;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        if(images == null || images.size() == 0)
            return 0;
        else
            return images.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final  int position) {
        ViewPager pager = (ViewPager) container;
        View view = LayoutInflater.from(context).inflate(R.layout.slider_image_adapter,null);
        final ProgressBar progressBar =  (ProgressBar) view.findViewById(R.id.progressBar2);
        final ImageView thumb =  (ImageView) view.findViewById(R.id.imageView6);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSliderClickListener != null) {
                    onSliderClickListener.onClick(thumb, position);
                }
            }
        });

        //final ImageView thumb = new ImageView(context);
        pager.addView(view);
        if (images.get(position) != null && images.get(position).length() > 0) {

                Picasso.with(context).load(images.get(position))
                        .fit().centerCrop()
                        .into(thumb, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setIndeterminate(false);
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBar.setIndeterminate(false);
                                progressBar.setVisibility(View.GONE);
                            }
                        });

        }else{

            progressBar.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            // ((ViewPager) container).removeView((ImageView) object);
            ((ViewPager) container).removeView((View) object);
        }catch (Exception ex){
            Log.e("Slider", "destroyItem: ",ex );
        }
    }

    public interface OnSliderClickListener{
        public void onClick(View v, int position);
    }
}
