package com.example.luan.findmovie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.luan.findmovie.extras.NewSliderView;
import com.example.luan.findmovie.extras.ProgressTask;
import com.example.luan.findmovie.extras.Utils;
import com.example.luan.findmovie.model.Filme;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetalhesProdutosActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private com.daimajia.slider.library.SliderLayout slider;
    private com.daimajia.slider.library.Indicators.PagerIndicator customindicator;
    private com.daimajia.slider.library.Indicators.PagerIndicator customindicator2;
    private android.widget.RelativeLayout activitydetalhesprodutos;
    private TextView nome;
    private TextView categoria;
    private TextView lancamento;
    private TextView linguagem;
    private TextView rating;
    private TextView descricao;
    private Button btnTrailer;
    private Button btnFav;

    Filme filmeSelect;
    Realm realm;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produtos);


        this.categoria = (TextView) findViewById(R.id.categoria);
        this.nome = (TextView) findViewById(R.id.nome);
        this.lancamento = (TextView) findViewById(R.id.data);
        this.linguagem = (TextView) findViewById(R.id.linguagem);
        this.rating = (TextView) findViewById(R.id.rating);
        this.descricao = (TextView) findViewById(R.id.descricao);
        this.activitydetalhesprodutos = (RelativeLayout) findViewById(R.id.activity_detalhes_produtos);
        this.customindicator2 = (PagerIndicator) findViewById(R.id.custom_indicator2);
        this.customindicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        this.slider = (SliderLayout) findViewById(R.id.slider);
        this.toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        this.btnTrailer = (Button) findViewById(R.id.btnTrailer);
        this.btnFav = (Button) findViewById(R.id.btnFav);


        if (toolbar != null) {
            toolbar.setTitle("");
            toolbar.setTitleTextColor(Color.parseColor("#2d2d2d"));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            setSupportActionBar(toolbar);
        }


        slider.stopAutoCycle();
        slider.setPresetTransformer("Default");
        filmeSelect = getIntent().getParcelableExtra("filme");

        sliderImage(filmeSelect.getPosterPath());

        //inicio uma instancia do Realm
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this).build();
        realm = Realm.getInstance(realmConfig);



        //<------------ Listeners ------------------>

        customindicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG", "ID: " + v.getId());
            }
        });


        //chama a minha classe de progress
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ProgressTask progressTask = new ProgressTask(DetalhesProdutosActivity.this,filmeSelect);
                progressTask.execute();
            }
        });

       

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ProgressTask progressTask = new ProgressTask(DetalhesProdutosActivity.this,filmeSelect);
                progressTask.execute();
            }
        });

        //vejo se esse filme já está favoritado ou não!
        // e em cada caso, eu trato de maneira diferente o click do botão

        Filme filmeRealm = realm.where(Filme.class).equalTo("id", filmeSelect.getId()).findFirst();

        if (filmeRealm != null) {
            // Exists
            btnFav.setText("DESFAVORITAR");
            btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                realm.beginTransaction();
                realm.deleteFromRealm(filmeSelect);
                realm.commitTransaction();
                Toast.makeText(DetalhesProdutosActivity.this, "Item foi tirado de sua lista de favoritos!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(DetalhesProdutosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });
        } else {
            // Not exist
            btnFav.setText("FAVORITAR");

            btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                realm.beginTransaction();
                realm.copyToRealm(filmeSelect);
                realm.commitTransaction();
                Toast.makeText(DetalhesProdutosActivity.this, "Favoritado com sucesso!", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(DetalhesProdutosActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });   
        }


       


//        chama o metodo que seta os dados na tela
        setData(filmeSelect);


    }

     


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    public void sliderImage(String path){
//            Como é apenas um imagem, seto ela para o componente
//        caso fosse mais de uma, haveria aqui um for

            NewSliderView aux = new NewSliderView(DetalhesProdutosActivity.this);
            aux.image(path);
            aux.setScaleType(BaseSliderView.ScaleType.CenterInside);
            aux.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    slider.stopAutoCycle();
                }
            });
            slider.addSlider(aux);


    }


    public void setData(Filme filme){
        nome.setText(filme.getTitle());
        categoria.setText(filme.getOriginalTitle());
        rating.setText(filme.getVoteAverage());
        lancamento.setText(Utils.FormatDate(filme.getReleaseDate()));
        linguagem.setText(filme.getOriginalLanguage());
        descricao.setText(filme.getOverview());

    }
}
