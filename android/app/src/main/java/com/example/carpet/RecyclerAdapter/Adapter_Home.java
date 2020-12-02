package com.example.carpet.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpet.R;
import com.example.carpet.config.Urls;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Home extends RecyclerView.Adapter<Adapter_Home.ViewHolder> {
    ArrayList<Integer> id=new ArrayList<>() ;
    ArrayList<String> title=new ArrayList<>() ;
    ArrayList<String> image=new ArrayList<>() ;
    ArrayList<Integer> price=new ArrayList<>();
    Context context;
    public Adapter_Home(Context context,ArrayList<Integer> id,ArrayList<String> title,ArrayList<String> image,ArrayList<Integer>price){
        this.id=id;
        this.title=title;
        this.image=image;
        this.price=price;
        this.context=context;
    }
    @Override
    public Adapter_Home.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        DecimalFormat format_number=new DecimalFormat("###,###,###");
        holder.item_title.setText(title.get(position));
        holder.item_price.setText(format_number.format(price.get(position)).toString()+" تومان");
        Picasso.with(context).load(Urls.url+image.get(position)).into(holder.item_image);
//        holder.click_item_recycler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.intent=new Intent(context,info_item.class);
//                holder.intent.putExtra("id_carpet",isbn.get(position)+"");
//                context.startActivity( holder.intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (10>id.size()) {
            return id.size();
        }else {
            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Intent intent;
        public ImageView item_image;
        public TextView item_title,item_price;
        public CardView click_item_recycler;
        public ViewHolder(View itemView) {
            super(itemView);

            item_image=itemView.findViewById(R.id.img_cardview);
            item_title=itemView.findViewById(R.id.txv_title_cardview);
            item_price=itemView.findViewById(R.id.txv_price_cardview);
            click_item_recycler=itemView.findViewById(R.id.card_view);
        }
    }
}
