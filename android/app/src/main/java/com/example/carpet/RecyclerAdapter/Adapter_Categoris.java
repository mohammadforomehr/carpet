package com.example.carpet.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpet.DetailCarpet;
import com.example.carpet.ListRecycleHome;
import com.example.carpet.Model.Categoris;
import com.example.carpet.R;
import com.example.carpet.SubCategori;

import java.util.List;

public class Adapter_Categoris extends RecyclerView.Adapter<Adapter_Categoris.ViewHolder>  {
    List<Categoris> arrayList;
    Context context;
    Boolean item_state=false;
    public Adapter_Categoris(Context context, List<Categoris> arrayList,Boolean item_state){
        this.arrayList=arrayList;
        this.context=context;
        this.item_state=item_state;
    }
    @Override
    public Adapter_Categoris.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_categoris,parent,false);
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
                if (item_state==true) {
                    holder.intent = new Intent(context, SubCategori.class);
                    holder.intent.putExtra("id", item.getId());
                    context.startActivity(holder.intent);
                }else
                {
                    holder.intent = new Intent(context, ListRecycleHome.class);
                    holder.intent.putExtra("Url", "/app/related/"+item.getId());
                    holder.intent.putExtra("Title",item.getTitle());
                    context.startActivity(holder.intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public Intent intent;
        public ImageView item_image;
        public TextView item_title;
        public CardView click_item_recycler;
        public ViewHolder(View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.img_sub_cat);
            item_title=itemView.findViewById(R.id.txv_title_categoris);
            click_item_recycler=itemView.findViewById(R.id.card_view_categoris);
            if (item_state==false){
                item_image.setVisibility(View.INVISIBLE);
            }
        }
    }
}
