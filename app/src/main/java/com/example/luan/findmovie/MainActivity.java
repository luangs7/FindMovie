package com.example.luan.findmovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luan.findmovie.adapter.RecycleFilmeAdapter;
import com.example.luan.findmovie.adapter.SliderMainAdapter;
import com.example.luan.findmovie.extras.RecyclerViewOnClickListenerHack;
import com.example.luan.findmovie.model.Filme;
import com.example.luan.findmovie.model.request.MainRequest;
import com.example.luan.findmovie.retrofit.ApiManager;
import com.example.luan.findmovie.retrofit.CustomCallback;
import com.example.luan.findmovie.retrofit.RequestInterfaceUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    RecycleFilmeAdapter adaptertodos;
    RecycleFilmeAdapter adapterfavoritos;
    RecycleFilmeAdapter adaptercurtidos;

    private TextView maistodos;
    private RecyclerView listtodos;
    private TextView maiscurtidos;
    private RecyclerView listcurtidos;
    private TextView maisfavoritos;
    private RecyclerView listfavoritos;
    private RelativeLayout relativefavoritos;
    SliderMainAdapter adapter;
    ViewPager slider;
    RelativeLayout categoria;

    @Override
    public void onCreate(Bundle savedInstanceState) {

//        Integer.parseInt("");
//        FirebaseCrash.log("Activity created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Filmes");
            toolbar.setTitleTextColor(Color.parseColor("#2d2d2d"));
            setSupportActionBar(toolbar);
        }


        maistodos = (TextView) findViewById(R.id.maistodos);
        listtodos = (RecyclerView) findViewById(R.id.listtodos);
        maisfavoritos = (TextView) findViewById(R.id.maisfavoritos);
        listfavoritos = (RecyclerView) findViewById(R.id.listfavoritos);
        maiscurtidos = (TextView) findViewById(R.id.maiscurtidos);
        listcurtidos = (RecyclerView) findViewById(R.id.listcurtidos);
        relativefavoritos = (RelativeLayout) findViewById(R.id.relativefavoritos);
        slider = (ViewPager) findViewById(R.id.viewPager);
        maistodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ListaProdutoActivity.class).putExtra("lista", "todos"));
            }
        });

        listtodos.setFocusable(false);


        maisfavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ListaProdutoActivity.class).putExtra("lista", "favoritos"));

            }
        });

        maiscurtidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ListaProdutoActivity.class).putExtra("lista", "curtidos"));

            }
        });

        adapter = new SliderMainAdapter(getBaseContext(), Arrays.asList(
                new String[]{"http://br.web.img3.acsta.net/newsv7/16/08/17/16/21/191576.jpg",
                        "https://i0.wp.com/media2.slashfilm.com/slashfilm/wp/wp-content/images/guardiansofthegalaxy2-teaserposter-frontpage.jpg",
                "https://i2.wp.com/www.seenit.co.uk/wp-content/uploads/The-Boss-Baby.jpg?fit=1200%2C422",
                "https://i.ytimg.com/vi/4xWOQrpTsBs/maxresdefault.jpg"}));
        slider.setAdapter(adapter);


        getpopular();
        getcurtidos();
        getfavoritos();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    public void hideKeybord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if ((slider.getCurrentItem() + 1) == (adapter.getCount())) {
                            slider.setCurrentItem(0, true);
                        } else {
                            slider.setCurrentItem(slider.getCurrentItem() + 1, true);
                        }

                    }
                });
            }
        }, 5000, 5000);

    }

    Handler handler = new Handler();
    Timer timer;

    @Override
    public void onStop() {
        super.onStop();
        timer.purge();
        timer.cancel();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    final static String api_key = "b5127d1016c968d30de8d0d6cc725a73";

    public void getpopular() {
        new ApiManager(getBaseContext())
                .getRetrofit()
                .create(RequestInterfaceUser.class)
                .getFilmesPopular(api_key, "1")
                .enqueue(new CustomCallback<MainRequest>(this, new CustomCallback.OnResponse<MainRequest>() {
                    @Override
                    public void onResponse(MainRequest response) {
                        Log.d("", "onResponse: ");
                            popular(response.getResults());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(Throwable t) {
                        getpopular();
                    }
                }));

    }

    public void getcurtidos() {
        new ApiManager(getBaseContext())
                .getRetrofit()
                .create(RequestInterfaceUser.class)
                .getFilmesVotados(api_key, "1")
                .enqueue(new CustomCallback<MainRequest>(this, new CustomCallback.OnResponse<MainRequest>() {
                    @Override
                    public void onResponse(MainRequest response) {
                        Log.d("", "onResponse: ");

                            votados(response.getResults());

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(Throwable t) {
                        getpopular();
                    }
                }));

    }

    public void getfavoritos(){
         RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this).build();
        final Realm realm = Realm.getInstance(realmConfig);

        List<Filme> favoritos = new ArrayList<>();
        favoritos = realm.where(Filme.class).findAll();

        if(favoritos.size() > 0){
            listfavoritos.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            listfavoritos.setLayoutManager(llm);
            adapterfavoritos = new RecycleFilmeAdapter(getBaseContext());
            listfavoritos.setAdapter(adapterfavoritos);
            adapterfavoritos.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
                @Override
                public void onClickListener(View view, int position, Object object) {
                    Filme filme = (Filme) object;
                    startActivity(new Intent(getBaseContext(), DetalhesProdutosActivity.class)
                            .putExtra("filme", filme));
                }
            });
            adapterfavoritos.replace(favoritos);
            relativefavoritos.setVisibility(View.VISIBLE);
        }else{
             relativefavoritos.setVisibility(View.GONE);   
        }

    }


    public void popular(List<Filme> todos) {
        listtodos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listtodos.setLayoutManager(llm);
        adaptertodos = new RecycleFilmeAdapter(getBaseContext());
        listtodos.setAdapter(adaptertodos);
        adaptertodos.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position, Object object) {
                Filme filme = (Filme) object;
                startActivity(new Intent(getBaseContext(), DetalhesProdutosActivity.class)
                        .putExtra("filme", filme));
            }
        });
        adaptertodos.replace(todos);
    }

    public void votados(List<Filme> todos) {
        listcurtidos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        listcurtidos.setLayoutManager(llm);
        adaptercurtidos = new RecycleFilmeAdapter(getBaseContext());
        listcurtidos.setAdapter(adaptercurtidos);
        adaptercurtidos.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position, Object object) {
                Filme filme = (Filme) object;
                startActivity(new Intent(getBaseContext(), DetalhesProdutosActivity.class)
                        .putExtra("filme", filme));
            }
        });
        adaptercurtidos.replace(todos);
    }

    @Override
    protected void onRestart() {
        getfavoritos();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        getfavoritos();
        super.onResume();
    }
}
