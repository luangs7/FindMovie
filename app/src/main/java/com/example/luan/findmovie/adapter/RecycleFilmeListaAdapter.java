package com.example.luan.findmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luan.findmovie.R;
import com.example.luan.findmovie.extras.RecyclerViewOnClickListenerHack;
import com.example.luan.findmovie.model.Filme;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleFilmeListaAdapter extends RecyclerView.Adapter<RecycleFilmeListaAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Filme> itens = new ArrayList<>();
    RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    private boolean loading = false;

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public RecyclerViewOnClickListenerHack getRecyclerViewOnClickListenerHack() {
        return recyclerViewOnClickListenerHack;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack) {
        this.recyclerViewOnClickListenerHack = recyclerViewOnClickListenerHack;
    }

    public RecycleFilmeListaAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==(itens.size())){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType==2){
            View v = inflater.inflate(R.layout.item_loader_lista, viewGroup, false);
            RecycleFilmeListaAdapter.ViewHolder mvh = new RecycleFilmeListaAdapter.ViewHolder(v);
            return mvh;
        }else{
            View v = inflater.inflate(R.layout.item_produto_lista, viewGroup, false);
            RecycleFilmeListaAdapter.ViewHolder mvh = new RecycleFilmeListaAdapter.ViewHolder(v);
            return mvh;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == 2){
            prepareLoad(viewHolder);
        }else{
            Filme selector = itens.get(position);
            prepare(viewHolder, selector, position);
        }
    }

    public void prepareLoad(final ViewHolder viewHolder){
        if(loading)
            viewHolder.loading.setVisibility(View.VISIBLE);
        else
            viewHolder.loading.setVisibility(View.GONE);

    }

    private  void prepare(final ViewHolder viewHolder, final Filme selector, final int position){

        viewHolder.nome.setText(selector.getTitle());
        viewHolder.categoria.setText(selector.getOriginalTitle());
        String path = selector.getPosterPath();

        try {
            Picasso.with(context).load(path)
                    .fit().centerCrop()
                    .error(R.drawable.ic_avatar)
                    .into(viewHolder.foto, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("okpicasso","ok");
                        }

                        @Override
                        public void onError() {
                            Log.d("erropicasso","erro");
                        }
                    });

        }catch (Exception e){
            Log.d("erropicasso",e.getMessage());

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerViewOnClickListenerHack!=null)
                    recyclerViewOnClickListenerHack.onClickListener(view,itens.indexOf(selector),selector);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size()+1;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView categoria;
        ImageView foto;
        ProgressBar loading;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = ((TextView) itemView.findViewById(R.id.nome));
            categoria = ((TextView) itemView.findViewById(R.id.categoria));
            loading = (ProgressBar) itemView.findViewById(R.id.loading);
            foto = (ImageView) itemView.findViewById(R.id.foto);
        }
    }

    public void replace(List<Filme> itens){
        this.itens.clear();
        this.itens.addAll(itens);
        //notifyDataSetChanged();
    }

    public void replaceFavoritos(List<Filme> itens){
        this.itens.clear();
        this.itens.addAll(itens);
        notifyDataSetChanged();
    }

    public void clear(){
        this.itens.clear();
        //notifyDataSetChanged();
    }

    public void add(List<Filme> itens){
        int pos = itens.size();
        this.itens.addAll(itens);
//        notifyItemRangeInserted(pos,itens.size()+1);
        notifyDataSetChanged();
    }
}
