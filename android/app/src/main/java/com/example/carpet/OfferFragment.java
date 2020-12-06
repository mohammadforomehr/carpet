package com.example.carpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.example.carpet.RecyclerAdapter.Adapter_Home;
import com.example.carpet.config.AppController;
import com.example.carpet.config.Urls;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class OfferFragment extends Fragment {
    EditText name,email,phone,webaddress,message;
    Button btn_save;
    AVLoadingIndicatorView indicatorView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Definition_element(view);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasText(name,"نام خود را وارد کنید")==true){
                    if (hasText(email,"ایمیل خود را وارد کنید")==true)
                        if (hasText(message,"پیغام خود را وارد کنید")==true)sendrequest();
                }

            }
        });
    }
    public static boolean hasText(EditText editText, String error_message) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length() == 0) {
            editText.setError(error_message);
            return false;
        }else{
            editText.setError(null);
            return true;
        }
    }
    public void Definition_element(View view){
        name=view.findViewById(R.id.edt_name);
        email=view.findViewById(R.id.edt_email);
        phone=view.findViewById(R.id.edt_phone);
        webaddress=view.findViewById(R.id.edt_web);
        message=view.findViewById(R.id.edt_message);
        btn_save=view.findViewById(R.id.btn_save_offer);
        indicatorView=view.findViewById(R.id.indicator_offers);
    }
    private void sendrequest(){
        btn_save.setVisibility(View.GONE);
        indicatorView.setVisibility(View.VISIBLE);
        String url = Urls.url+"/app/offers";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name.getText().toString());
        params.put("email", email.getText().toString());
        params.put("phone", phone.getText().toString());
        params.put("addressweb", webaddress.getText().toString());
        params.put("message", message.getText().toString());

        JsonObjectRequest request_json = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        btn_save.setVisibility(View.VISIBLE);
                        indicatorView.setVisibility(View.GONE);
                        clear_edittext();
                        Toast.makeText(getActivity(), "ارسال با موفقیت", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request_json);
    }
    public void clear_edittext(){
        name.setText("");
        email.setText("");
        phone.setText("");
        webaddress.setText("");
        message.setText("");
    }

}
