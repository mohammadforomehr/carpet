package com.example.carpet.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.carpet.DetailCarpet;
import com.example.carpet.Model.About;
import com.example.carpet.Model.Carpet;
import com.example.carpet.OfferFragment;
import com.example.carpet.R;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutFragment extends Fragment implements View.OnClickListener{
    TextView txv_about;
    ImageView img_tel,img_wat,img_ins,img_tiw,img_web;
    Button btn_phone,btn_offer;
    ArrayList<About> array=new ArrayList<About>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        request();
        Definition_element(view);

    }
    public void Definition_element(final View view){
        txv_about=view.findViewById(R.id.txv_about);
        img_ins=view.findViewById(R.id.about_insta);
        img_tel=view.findViewById(R.id.about_telgram);
        img_wat=view.findViewById(R.id.about_whatsapp);
        img_tiw=view.findViewById(R.id.about_twitter);
        img_web=view.findViewById(R.id.about_website);
        btn_phone=view.findViewById(R.id.about_phone);
        btn_offer=view.findViewById(R.id.about_offer);
        img_ins.setOnClickListener(this);
        img_tel.setOnClickListener(this);
        img_wat.setOnClickListener(this);
        img_tiw.setOnClickListener(this);
        img_web.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_offer.setOnClickListener(this);
    }
    private void request(){
        String url = Urls.url+"/app/about";
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    About item=new About();
                    txv_about.setText(Html.fromHtml(response.getString("about")));
                    item.setWhatsapp(response.getString("whatsapp"));
                    item.setTelegram(response.getString("telegram"));
                    item.setInstagram(response.getString("instagram"));
                    item.setTwitter(response.getString("twitter"));
                    item.setWebsite(response.getString("website"));
                    item.setPhone(response.getString("phone"));
                   array.add(item);

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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (!array.isEmpty()) {
            About about=array.get(0);
            switch (v.getId()) {
                case R.id.about_whatsapp:
                    intent.setData(Uri.parse(about.getWhatsapp()));
                   getActivity().startActivity(intent);
                    break;
                case R.id.about_telgram:
                    intent.setData(Uri.parse(about.getTelegram()));
                    getActivity().startActivity(intent);
                    break;
                case R.id.about_insta:
                    intent.setData(Uri.parse(about.getInstagram()));
                    getActivity().startActivity(intent);
                    break;
                case R.id.about_twitter:
                    intent.setData(Uri.parse(about.getTwitter()));
                    getActivity().startActivity(intent);
                    break;
                case R.id.about_website:
                    intent.setData(Uri.parse(about.getWebsite()));
                    getActivity().startActivity(intent);
                    break;
                case R.id.about_phone:
                    intent.setData(Uri.parse("tel:" + about.getPhone()));
                    getActivity().startActivity(intent);
                    break;
                case R.id.about_offer:
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, new OfferFragment(), "NewFragmentTag");
                    ft.commit();
                    break;
            }
        }
    }
}

