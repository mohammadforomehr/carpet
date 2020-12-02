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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.carpet.RecyclerAdapter.Adapter_Home;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    SliderLayout sliderimage;
    TextSliderView textSliderView;
    RecyclerView rcl_new ,rcl_amz ;
    RecyclerView.LayoutManager rcl_mng_new,rcl_mng_amz;
    RecyclerView.Adapter adp_new,adp_amz;
    ArrayList<Integer> price_new,price_amz,id_new,id_amz;
    ArrayList<String> title_new,title_amz,img_new,img_amz;
    private RequestQueue mQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        Definition_array();
        requestslider();
        request_rclamz();
        request_rclnew();
    }
    public void Definition_array(){
        price_new=new ArrayList<>();
        price_amz=new ArrayList<>();
        id_new=new ArrayList<>();
        id_amz=new ArrayList<>();
        title_new=new ArrayList<>();
        title_amz=new ArrayList<>();
        img_new=new ArrayList<>();
        img_amz=new ArrayList<>();
    }
    public void Definition_element(View view){
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
                    JSONArray obj = response.getJSONArray("result");
                    for (int i =obj.length()-1;i>= 0;i--) {
                        JSONObject object = obj.getJSONObject(i);
                        price_new.add(object.getInt("price"));
                        id_new.add(object.getInt("id"));
                        title_new.add(object.getString("title"));
                        img_new.add(object.getString("image"));
                    }
                    adp_new=new Adapter_Home(getActivity(),id_new,title_new,img_new,price_new);
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
                    JSONArray obj = response.getJSONArray("result");
                    for (int i =obj.length()-1;i>= 0;i--) {
                        JSONObject object = obj.getJSONObject(i);
                        price_amz.add(object.getInt("price"));
                        id_amz.add(object.getInt("id"));
                        title_amz.add(object.getString("title"));
                        img_amz.add(object.getString("image"));
                    }
                    adp_amz=new Adapter_Home(getActivity(),id_amz,title_amz,img_amz,price_amz);
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
}
