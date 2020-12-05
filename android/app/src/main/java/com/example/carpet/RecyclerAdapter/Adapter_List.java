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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.ListRecycleHome;
import com.example.carpet.Model.Carpet;
import com.example.carpet.R;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_List extends RecyclerView.Adapter<Adapter_List.ViewHolder>  {
    List<Carpet> arrayList;
    Context context;
    int currentPage =1;
    int lastPage = 0;
    String next_page;
    AVLoadingIndicatorView indicatorView;
    public Adapter_List(Context context,List<Carpet> arrayList, int lastPage,String next_page,AVLoadingIndicatorView indicatorView){
        this.arrayList=arrayList;
        this.context=context;
        this.lastPage = lastPage;
        this.next_page=next_page;
        this.indicatorView=indicatorView;
    }
    @Override
    public Adapter_List.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.card_layout_vertical,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        DecimalFormat format_number=new DecimalFormat("###,###,###");
        Carpet item=arrayList.get(position);
        holder.item_title.setText(item.getTitle());
        holder.item_size.setText(item.getSize()+"");
        holder.item_color.setText(item.getColor());
        holder.item_shoulder.setText(item.getShoulder()+"");
        holder.item_price.setText(format_number.format(item.getPrice())+" تومان");
        Picasso.with(context).load(Urls.url+item.getImage()).into(holder.item_image);
//        holder.click_item_recycler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.intent=new Intent(context,info_item.class);
//                holder.intent.putExtra("id_carpet",isbn.get(position)+"");
//                context.startActivity( holder.intent);
//            }
//        });
        if(position == getItemCount()-3){
            if (currentPage <= lastPage ){
                indicatorView.setVisibility(View.VISIBLE);
                String url = next_page;
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONArray obj = response.getJSONArray("data");
                            for (int i =0;i < obj.length() ;i++) {
                                Carpet item=new Carpet();
                                JSONObject object = obj.getJSONObject(i);
                                item.setPrice(object.getInt("price"));
                                item.setId(object.getInt("id"));
                                item.setTitle(object.getString("title"));
                                item.setImage(object.getString("image"));
                                item.setShoulder(object.getInt("shoulder"));
                                item.setColor(object.getString("color"));
                                item.setSize(object.getString("size"));
                                arrayList.add(item);
                            }
                            if (response.getString("next_page_url")!=null){
                           next_page=response.getString("next_page_url");
                           }
                            currentPage=response.getInt("current_page");
                            notifyDataSetChanged();
                            indicatorView.setVisibility(View.GONE);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();

                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                };
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
                AppController.getInstance().addToRequestQueue(request);

            }
        }

    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public Intent intent;
        public ImageView item_image;
        public TextView item_title,item_price,item_color,item_size,item_shoulder;
        public CardView click_item_recycler;
        public ViewHolder(View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.img_cardview_list);
            item_title=itemView.findViewById(R.id.txv_title_cardview_list);
            item_price=itemView.findViewById(R.id.txv_price_cardview_list);
            item_color=itemView.findViewById(R.id.txv_color_list);
            item_size=itemView.findViewById(R.id.txv_size_list);
            item_shoulder=itemView.findViewById(R.id.txv_shoulder_list);
            click_item_recycler=itemView.findViewById(R.id.card_view_list);
        }
    }
}
