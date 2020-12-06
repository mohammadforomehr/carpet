package com.example.carpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.Model.Carpet;
import com.example.carpet.RecyclerAdapter.Adapter_Home;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.squareup.picasso.Picasso;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailCarpet extends AppCompatActivity implements View.OnClickListener {
    TextView txv_title,txv_caption,txv_code_carpet,txv_color,
            txv_Plan,txv_size,txv_shoulder,txv_price;
    ImageView img_detail,img_sahre,img_fullscreen;
    Bundle bundle;
    List<Carpet> arrayList=new ArrayList<Carpet>();
    ScrollView scrollView;
    ShimmerRecyclerView shimmerrcl_related;
    androidx.appcompat.widget.Toolbar toolbar;
    LinearLayout linearLayout;
    Button btn_fullscreen;
    RecyclerView rcl_related;
    RecyclerView.LayoutManager rcl_mng_related;
    RecyclerView.Adapter adp_related;
    AVLoadingIndicatorView indicatorView;
    int IdCarpet;
    String image_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_carpet);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        Definition_element();
        request();
        request_related();
    }

    public void Definition_element(){
        txv_caption=findViewById(R.id.txv_caption_detail);
        txv_code_carpet=findViewById(R.id.txv_code_detail);
        txv_color=findViewById(R.id.txv_color_detail);
        txv_title=findViewById(R.id.txv_title_detail);
        txv_Plan=findViewById(R.id.txv_plan_detail);
        txv_size=findViewById(R.id.txv_size_detail);
        txv_shoulder=findViewById(R.id.txv_shoulder_detail);
        txv_price=findViewById(R.id.txv_price_detail);

        //image
        img_detail=findViewById(R.id.img_detail);
        img_sahre=findViewById(R.id.img_share_detail);
        img_sahre.setOnClickListener(this);
        img_fullscreen=findViewById(R.id.img_fuulscreen);
        img_detail.setOnClickListener(this);
        //scroll
        scrollView=findViewById(R.id.scroll_detail);
        //toolbar
        toolbar= findViewById(R.id.tolbar_detail);
        //layout
        linearLayout=findViewById(R.id.layout_fullscreen);
        //
        btn_fullscreen=findViewById(R.id.close_fullscreen);
        btn_fullscreen.setOnClickListener(this);
        //shimmer
        shimmerrcl_related = findViewById(R.id.shimmer_rcl_Related);
        shimmerrcl_related.setLayoutManager(new LinearLayoutManager(DetailCarpet.this,LinearLayoutManager.HORIZONTAL,false));
        shimmerrcl_related.setHasFixedSize(true);
        shimmerrcl_related.showShimmer();
        //recycle view
        rcl_mng_related=new LinearLayoutManager(DetailCarpet.this);
        rcl_mng_related.canScrollHorizontally();
        rcl_related=findViewById(R.id.rcl_Related_detal);
        rcl_related.setHasFixedSize(true);
        rcl_related.setLayoutManager(rcl_mng_related);
        rcl_related.setLayoutManager(new LinearLayoutManager(DetailCarpet.this,LinearLayoutManager.HORIZONTAL,false));

        indicatorView=findViewById(R.id.indicator_detail);

    }

    private void request(){
        final DecimalFormat format_number=new DecimalFormat("###,###,###");
        String url = Urls.url+bundle.getString("IdCarpet");
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                             Carpet item=new Carpet();
                        txv_price.setText(format_number.format(response.getInt("price")) + "تومان");
                        txv_caption.setText(Html.fromHtml(response.getString("caption").toString()));
                        txv_code_carpet.setText(response.getInt("code_carpet")+"");
                        txv_color.setText("رنگ :   " + response.getString("color").toString());
                        txv_Plan.setText("طرح :   " + response.getString("Plan").toString());
                        txv_shoulder.setText("شانه :   " + response.getInt("shoulder")+"");
                        txv_size.setText("سایز :   " + response.getString("size").toString());
                        txv_title.setText(response.getString("title").toString());
                        IdCarpet=response.getInt("id");
                        image_link=response.getString("image");
                        Picasso.with(DetailCarpet.this).load(Urls.url + response.getString("image")).into(img_detail);
                        Picasso.with(DetailCarpet.this).load(Urls.url + response.getString("image")).into(img_fullscreen);
                        indicatorView.setVisibility(View.GONE);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(DetailCarpet.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    private void request_related() {
        String url = Urls.url+"/app/related/"+IdCarpet;
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    ArrayList<Carpet> arrayList = new ArrayList<>();
                    JSONArray obj = response.getJSONArray("data");
                    for (int i =0;i < obj.length() ;i++) {
                        Carpet item=new Carpet();
                        JSONObject object = obj.getJSONObject(i);
                        item.setPrice(object.getInt("price"));
                        item.setId(object.getInt("id"));
                        item.setTitle(object.getString("title"));
                        item.setImage(object.getString("image"));
                        arrayList.add(item);
                    }
                    adp_related=new Adapter_Home(DetailCarpet.this,arrayList,10);
                    shimmerrcl_related.setVisibility(View.GONE);
                    rcl_related.setAdapter(adp_related);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(DetailCarpet.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(DetailCarpet.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_detail:
                toolbar.setVisibility(View.GONE);
                scrollView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.close_fullscreen:
                toolbar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                break;
            case R.id.img_share_detail:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String finalMsg ="عنوان محصول:"+txv_title.getText()+"\n قیمت محصول:"+txv_price.getText()+"\n لینک محصول:"+Urls.url+image_link;
                intent.putExtra(Intent.EXTRA_TEXT, finalMsg);
                Intent modIntent = Intent.createChooser(intent, "اشتراک گزاری محصول");
                startActivity(modIntent);
                break;
        }
    }
}
