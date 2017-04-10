package com.example.luan.findmovie.extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.luan.findmovie.R;

/**
 * Created by luan on 08/04/2017.
 */

//Aqui eu criei uma classe que herda da classe criada pelo componente, assim posso customizar o scaletype da imagem

public class NewSliderView extends DefaultSliderView {

    public NewSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        target.setScaleType(ImageView.ScaleType.CENTER);
        bindEventAndShow(v, target);
        return super.getView();
    }
}
