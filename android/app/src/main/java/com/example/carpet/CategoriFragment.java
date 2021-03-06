package com.example.carpet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.Model.Carpet;
import com.example.carpet.Model.Categoris;
import com.example.carpet.RecyclerAdapter.Adapter_Categoris;
import com.example.carpet.RecyclerAdapter.Adapter_List;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Categoris> arrayList=new ArrayList<Categoris>();
    ShimmerRecyclerView shimmerRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gategoris,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        request();

    }
    public void Definition_element(View v){
        //recycle view
        recyclerView=v.findViewById(R.id.rcl_categoris);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //shimmer
        shimmerRecyclerView = v.findViewById(R.id.shimmer_rcl_categoris);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        shimmerRecyclerView.setHasFixedSize(true);
        shimmerRecyclerView.showShimmer();

    }
    private void request() {
        String url = Urls.url+"/app/cat_child/0";
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
                    adapter=new Adapter_Categoris(getActivity(),arrayList,true);
                    shimmerRecyclerView.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }
}
