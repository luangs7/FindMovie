package com.example.luan.findmovie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.luan.findmovie.adapter.RecycleFilmeListaAdapter;
import com.example.luan.findmovie.extras.EndlessRecyclerViewScrollListener;
import com.example.luan.findmovie.extras.RecyclerViewOnClickListenerHack;
import com.example.luan.findmovie.model.Filme;
import com.example.luan.findmovie.model.request.MainRequest;
import com.example.luan.findmovie.retrofit.ApiManager;
import com.example.luan.findmovie.retrofit.CustomCallback;
import com.example.luan.findmovie.retrofit.RequestInterfaceUser;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ListaProdutoActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private android.support.design.widget.AppBarLayout appBarLayout;
    private android.support.v7.widget.RecyclerView list;
    private android.widget.RelativeLayout activitylistaproduto;
    RecycleFilmeListaAdapter adapter;
    String tipoLista = "";
//    List<Filme> filmes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);
        this.activitylistaproduto = (RelativeLayout) findViewById(R.id.activity_lista_produto);
        this.list = (RecyclerView) findViewById(R.id.list);
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        list.setHasFixedSize(true);
        adapter = new RecycleFilmeListaAdapter(this);
        GridLayoutManager llm = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        llm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(adapter.getItemViewType(position)){
                    case 2:
                        return 3;
                    case 1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
        list.setAdapter(adapter);
        list.setLayoutManager(llm);

        tipoLista = getIntent().getStringExtra("lista");
        if (toolbar != null) {
            switch (tipoLista){
                case "todos":
                    toolbar.setTitle("Os mais populares");
                    list.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                            adapter.setLoading(true);
                            adapter.notifyItemChanged(adapter.getItemCount());
                            getpopulares(page);
                        }
                    });
                    adapter.replace(getpopulares(1));
                    break;

                case "favoritos":
                    tipoLista = "favoritos";
                    toolbar.setTitle("Meus favoritos");
                    getfavoritos();
                    break;

                case "curtidos":
                    tipoLista = "like";
                    list.addOnScrollListener(new EndlessRecyclerViewScrollListener(llm) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                            adapter.setLoading(true);
                            adapter.notifyItemChanged(adapter.getItemCount());
                            getcurtidos(page);
                        }
                    });
                    adapter.replace(getcurtidos(1));
                    toolbar.setTitle("Os mais curtidos");
                    break;
            }
            toolbar.setTitleTextColor(Color.parseColor("#2d2d2d"));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            setSupportActionBar(toolbar);
        }

  adapter.setRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
                @Override
                public void onClickListener(View view, int position, Object object) {
                    Filme filme = (Filme) object;
                    startActivity(new Intent(getBaseContext(), DetalhesProdutosActivity.class)
                            .putExtra("filme", filme));
                }
            });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Filme> getpopulares(final int page) {

        final List<Filme> filmes = new ArrayList<>();

        new ApiManager(getBaseContext())
                .getRetrofit()
                .create(RequestInterfaceUser.class)
                .getFilmesPopular(MainActivity.api_key,String.valueOf(page))
                .enqueue(new CustomCallback<MainRequest>(this, new CustomCallback.OnResponse<MainRequest>() {
                    @Override
                    public void onResponse(MainRequest response) {
                        Log.d("", "onResponse: ");
                            filmes.addAll(response.getResults());
                            adapter.add(filmes);
                            adapter.setLoading(false);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        adapter.setLoading(false);

                    }

                    @Override
                    public void onRetry(Throwable t) {
                        getpopulares(page);
                    }
                },false));

        return filmes;
    }


    public List<Filme> getcurtidos(final int page) {

        final List<Filme> filmes = new ArrayList<>();

        new ApiManager(getBaseContext())
                .getRetrofit()
                .create(RequestInterfaceUser.class)
                .getFilmesVotados(MainActivity.api_key,String.valueOf(page))
                .enqueue(new CustomCallback<MainRequest>(this, new CustomCallback.OnResponse<MainRequest>() {
                    @Override
                    public void onResponse(MainRequest response) {
                        Log.d("", "onResponse: ");
                            filmes.addAll(response.getResults());
                            adapter.add(filmes);
                            adapter.setLoading(false);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        adapter.setLoading(false);

                    }

                    @Override
                    public void onRetry(Throwable t) {
                        getpopulares(page);
                    }
                },false));

        return filmes;
    }

    public void getfavoritos(){
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this).build();
        final Realm realm = Realm.getInstance(realmConfig);

        List<Filme> favoritos = new ArrayList<>();
        favoritos = realm.where(Filme.class).findAll();

        adapter.add(favoritos);


    }

}
