package com.example.carpet.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpet.ListRecycleHome;
import com.example.carpet.Model.Categoris;
import com.example.carpet.R;
import com.example.carpet.SubCategori;

import java.util.List;

public class Adapter_Categoris_Home extends RecyclerView.Adapter<Adapter_Categoris_Home.ViewHolder>  {
    List<Categoris> arrayList;
    Context context;
    public Adapter_Categoris_Home(Context context, List<Categoris> arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public Adapter_Categoris_Home.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_layout_cat_home,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final Categoris item=arrayList.get(position);
        holder.item_title.setText(item.getTitle());
        holder.click_item_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    holder.intent = new Intent(context, SubCategori.class);
                    holder.intent.putExtra("id", item.getId());
                    context.startActivity(holder.intent);
            }
        });


    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public Intent intent;
        public TextView item_title;
        public CardView click_item_recycler;
        public ViewHolder(View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.txv_categori_home);
            click_item_recycler=itemView.findViewById(R.id.card_categori_home);

        }
    }
}
