package com.example.carpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.Model.Categoris;
import com.example.carpet.RecyclerAdapter.Adapter_Categoris;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategori extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Categoris> arrayList=new ArrayList<Categoris>();
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categori);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        bundle = intent.getExtras();
        Definition_element();
        request();
    }
    public void Definition_element(){
        //recycle view
        recyclerView=findViewById(R.id.rcl_cateitem_sub);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubCategori.this,LinearLayoutManager.VERTICAL,false));
    }
    private void request() {

        String url = Urls.url+"/app/cat_child/"+bundle.getInt("id");
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray obj = response.getJSONArray("result");
                    for (int i =0;i <obj.length() ;i++) {
                        Categoris item=new Categoris();
                        JSONObject object = obj.getJSONObject(i);
                        item.setId(object.getInt("id"));
                        item.setTitle(object.getString("title"));
                        item.setImage(object.getString("image"));
                        arrayList.add(item);

                    }
                    adapter=new Adapter_Categoris(SubCategori.this,arrayList,false);
                    recyclerView.setAdapter(adapter);

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
