package com.example.carpet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.carpet.Model.Carpet;
import com.example.carpet.Model.News;
import com.example.carpet.RecyclerAdapter.Adapter_List;
import com.example.carpet.RecyclerAdapter.Adapter_News;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {
    RecyclerView rcl_news;
    RecyclerView.Adapter adp_rcl;
    List<News> arrayList=new ArrayList<News>();
    ShimmerRecyclerView shimmerRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        request();
    }
    public void Definition_element(View view){
        //recycle view
        rcl_news=view.findViewById(R.id.rcl_news);
        rcl_news.setHasFixedSize(true);
        rcl_news.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //shimmer
        shimmerRecyclerView = view.findViewById(R.id.shimmer_rcl_news);
        shimmerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        shimmerRecyclerView.setHasFixedSize(true);
        shimmerRecyclerView.showShimmer();
        //
    }
    private void request() {
        String url = Urls.url+"/app/news";
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray obj = response.getJSONArray("data");
                    for (int i =0;i <obj.length() ;i++) {
                        News item=new News();
                        JSONObject object = obj.getJSONObject(i);
                        item.setId(object.getInt("id"));
                        item.setTitle(object.getString("title"));
                        item.setImage(object.getString("image"));
                        item.setCaption(object.getString("caption"));
                        item.setDate(object.getString("created_at"));
                        arrayList.add(item);

                    }
                    adp_rcl=new Adapter_News(getActivity(),arrayList);
                    shimmerRecyclerView.setVisibility(View.GONE);
                    rcl_news.setAdapter(adp_rcl);

                }
                catch (JSONException e)
                {


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
