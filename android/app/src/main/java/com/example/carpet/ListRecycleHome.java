package com.example.carpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.Model.Carpet;
import com.example.carpet.RecyclerAdapter.Adapter_Home;
import com.example.carpet.RecyclerAdapter.Adapter_List;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.todkars.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListRecycleHome extends AppCompatActivity {
    TextView txv_title;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Carpet> arrayList=new ArrayList<Carpet>();
    Bundle bundle;
    ShimmerRecyclerView shimmerRecyclerView;
    AVLoadingIndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recycle_home);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        bundle = intent.getExtras();
        Definition_element();
        Definition_array();
        request();
    }
    public void Definition_array(){
        arrayList=new ArrayList<>();

    }
    public void Definition_element(){
        //recycle view
        recyclerView=findViewById(R.id.rcl_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListRecycleHome.this,LinearLayoutManager.VERTICAL,false));
        //Text view title
        txv_title=findViewById(R.id.txv_list);
        txv_title.setText(bundle.getString("Title"));
        //shimmer
        shimmerRecyclerView = findViewById(R.id.shimmer_rcl_list);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(ListRecycleHome.this,LinearLayoutManager.VERTICAL,false));
        shimmerRecyclerView.setHasFixedSize(true);
        shimmerRecyclerView.showShimmer();
        //
        indicatorView=findViewById(R.id.indicator_list);
    }
    private void request() {
        String url = Urls.url+bundle.getString("Url");
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray obj = response.getJSONArray("data");
                    for (int i =0;i <obj.length() ;i++) {
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
                    adapter=new Adapter_List(ListRecycleHome.this,arrayList,response.getInt("last_page"),response.getString("next_page_url"),indicatorView);
                    shimmerRecyclerView.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(ListRecycleHome.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ListRecycleHome.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }
}
