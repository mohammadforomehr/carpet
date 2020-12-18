package com.example.carpet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.carpet.RecyclerAdapter.Adapter_List;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Carpet> arrayList=new ArrayList<Carpet>();
    EditText edt_search;
    AVLoadingIndicatorView indicatorView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0) {
                    arrayList.clear();
                    request();
                }else {
                    arrayList.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }
    public void Definition_element(View v){
        //recycle view
        recyclerView=v.findViewById(R.id.rcl_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //Text view title
        edt_search=v.findViewById(R.id.edt_search);

        indicatorView=v.findViewById(R.id.indicator_search);
    }
    private void request() {
        String url = Urls.url+"/app/search/"+edt_search.getText();
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
                    adapter=new Adapter_List(getActivity(),arrayList,response.getInt("last_page"),response.getString("next_page_url"),indicatorView);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        AppController.getInstance().addToRequestQueue(request);
    }
}
