package com.example.carpet.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.carpet.ListRecycleHome;
import com.example.carpet.Model.Categoris;
import com.example.carpet.Model.News;
import com.example.carpet.R;
import com.example.carpet.SubCategori;
import com.example.carpet.config.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import saman.zamani.persiandate.PersianDate;

public class Adapter_News extends RecyclerView.Adapter<Adapter_News.ViewHolder>  {
    List<News> arrayList;
    Context context;
    public Adapter_News(Context context, List<News> arrayList){
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public Adapter_News.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_layout_news,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final News item=arrayList.get(position);
        holder.item_title.setText(item.getTitle());
        holder.item_caption.setText(item.getCaption());
        String[] separated = item.getDate().split("-");
        String[] day=separated[2].split("T",2);
        int[] date=holder.persianDate.toJalali(Integer.parseInt(separated[0]),Integer.parseInt(separated[1]),Integer.parseInt(day[0]));
        holder.item_date.setText(date[0]+"/"+date[1]+"/"+date[2]);
        Picasso.with(context).load(Urls.url+item.getImage()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public Intent intent;
        ReadMoreTextView item_caption;
        TextView item_title,item_date;
        ImageView item_image;
        PersianDate persianDate;
        public ViewHolder(View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.img_cardview_news);
            item_title=itemView.findViewById(R.id.txv_title_news);
            item_date=itemView.findViewById(R.id.txv_date_news);
            item_caption=itemView.findViewById(R.id.txv_caption_news);
            item_caption.setTrimCollapsedText("مشاهده بیشتر");
            item_caption.setTrimExpandedText("  بستن");
            persianDate=new PersianDate();
        }
    }
}
