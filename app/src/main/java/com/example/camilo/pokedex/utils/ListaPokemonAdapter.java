package com.example.camilo.pokedex.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilo.pokedex.R;
import com.example.camilo.pokedex.models.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pokemon> dataset;
    private int layout;
    private OnItemClickListener itemClickListener;


    public ListaPokemonAdapter(Context context, int layout, OnItemClickListener listener)
    {
        this.context = context;
        this.layout = layout;
        this.itemClickListener = listener;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = dataset.get(position);

        Picasso.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getNumber() +".png")
                .into(holder.img);

        holder.bind(pokemon.getName(), itemClickListener, holder.img);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    //AGREGO LOS NUEVOS 20 POKEMONES QUE ME LLEGARON AL CARGAR
    public void adicionarPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView idPoke,namePoke;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.viewImg);
            idPoke = itemView.findViewById(R.id.viewPid);
            namePoke = itemView.findViewById(R.id.viewName);

        }

        public void bind(final String name, final OnItemClickListener listener, final ImageView img)
        {
            int cont = getAdapterPosition()+1;
            if(cont<10)
            {
                this.idPoke.setText("#00"+cont);
            }
            else
            {
                if(cont<100)
                {
                    this.idPoke.setText("#0"+cont);
                }
                else
                {
                    this.idPoke.setText("#"+cont);
                }
            }
            this.namePoke.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(name, getAdapterPosition(), img);
                }
            });

            final AssetManager assets = context.getAssets();
            final Typeface tvFont = Typeface.createFromAsset(assets, "fonts/roboli.ttf");
            idPoke.setTypeface(tvFont);
            namePoke.setTypeface(tvFont);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(String name, int position, ImageView img);
    }

}
