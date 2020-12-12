package com.example.carpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.carpet.Model.Carpet;
import com.example.carpet.Model.Categoris;
import com.example.carpet.RecyclerAdapter.Adapter_Categoris;
import com.example.carpet.RecyclerAdapter.Adapter_Categoris_Home;
import com.example.carpet.RecyclerAdapter.Adapter_Home;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.todkars.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  {
    SliderLayout sliderimage;
    TextSliderView textSliderView;
    RecyclerView rcl_new ,rcl_amz,rcl_cat ;
    RecyclerView.LayoutManager rcl_mng_new,rcl_mng_amz,rcl_mng_cat;
    RecyclerView.Adapter adp_new,adp_amz,adp_cat;
    TextView txv_all_new,txv_all_amz;
    Intent intent;
    ShimmerRecyclerView shimmer_new,shimmer_amz;
    ShimmerFrameLayout shimmer_slider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        requestslider();
        request_rclamz();
        request_rclnew();
        request_categori();
    }


    public void Definition_element(final View view){
        //bannerslide
        sliderimage=view.findViewById(R.id.sliderimg_home);
        sliderimage.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderimage.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderimage.setCustomAnimation(new DescriptionAnimation());
        sliderimage.setDuration(4000);
        //recycle view
        rcl_mng_new=new LinearLayoutManager(getActivity());
        rcl_mng_new.canScrollHorizontally();
        rcl_new=view.findViewById(R.id.rcl_new_main);
        rcl_new.setHasFixedSize(true);
        rcl_new.setLayoutManager(rcl_mng_new);
        rcl_new.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rcl_mng_amz=new LinearLayoutManager(getActivity());
        rcl_mng_amz.canScrollHorizontally();
        rcl_amz=view.findViewById(R.id.rcl_amazing_main);
        rcl_amz.setHasFixedSize(true);
        rcl_amz.setLayoutManager(rcl_mng_amz);
        rcl_amz.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

        rcl_mng_cat=new LinearLayoutManager(getActivity());
        rcl_mng_cat.canScrollHorizontally();
        rcl_cat=view.findViewById(R.id.rcl_cat_home);
        rcl_cat.setHasFixedSize(true);
        rcl_cat.setLayoutManager(rcl_mng_cat);
        rcl_cat.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        // Text view
        txv_all_amz=view.findViewById(R.id.txv_amazing);
        txv_all_new=view.findViewById(R.id.txv_new);
        txv_all_amz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getActivity(), ListRecycleHome.class);
                intent.putExtra("Title", "پیشنهاد ویژه");
                intent.putExtra("Url", "/app/amazing?page=1");
                startActivity(intent);
            }
        });
        txv_all_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getActivity(), ListRecycleHome.class);
                intent.putExtra("Title", "جدیدترین محصولات");
                intent.putExtra("Url", "/app/new?page=1");
                startActivity(intent);
            }
        });
        //shimmer
        shimmer_new = view.findViewById(R.id.shimmer_rcl_new);
        shimmer_new.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        shimmer_new.setHasFixedSize(true);
        shimmer_new.showShimmer();

        shimmer_amz = view.findViewById(R.id.shimmer_rcl_amz);
        shimmer_amz.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        shimmer_amz.setHasFixedSize(true);
        shimmer_amz.showShimmer();

        shimmer_slider = view.findViewById(R.id.shimmer_slider);
        shimmer_slider.startShimmer();


    }
    private void requestslider() {
        String url = Urls.url+"/app/baner";
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {

                    JSONArray obj = response.getJSONArray("result");
                    for (int i =obj.length()-1;i>= 0;i--) {
                        textSliderView = new TextSliderView(getActivity());
                        JSONObject object = obj.getJSONObject(i);
                        // initialize a SliderLayout
                        textSliderView
                                .description(object.getString("title"))
                                .image(Urls.url+object.getString("image"))
                                .setScaleType(BaseSliderView.ScaleType.Fit);
                        sliderimage.addSlider(textSliderView);
                        shimmer_slider.setVisibility(View.GONE);
                        sliderimage.setVisibility(View.VISIBLE);

                    }

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
    private void request_rclnew() {
        String url = Urls.url+"/app/new";
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
                    adp_new=new Adapter_Home(getActivity(),arrayList,10);
                    shimmer_new.setVisibility(View.GONE);
                    rcl_new.setAdapter(adp_new);
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
    private void request_rclamz() {
        String url = Urls.url+"/app/amazing";
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
                    adp_amz=new Adapter_Home(getActivity(),arrayList,10);
                    shimmer_amz.setVisibility(View.GONE);
                    rcl_amz.setAdapter(adp_amz);
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
    private void request_categori() {
        String url = Urls.url+"/app/cat_child/0";
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    ArrayList<Categoris> arrayList=new ArrayList<>();
                    JSONArray obj = response.getJSONArray("result");
                    for (int i =0;i <obj.length() ;i++) {
                        Categoris item=new Categoris();
                        JSONObject object = obj.getJSONObject(i);
                        item.setId(object.getInt("id"));
                        item.setTitle(object.getString("title"));
                        item.setImage(object.getString("image"));
                        arrayList.add(item);

                    }
                    adp_cat=new Adapter_Categoris_Home(getActivity(),arrayList);
                    rcl_cat.setAdapter(adp_cat);

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
