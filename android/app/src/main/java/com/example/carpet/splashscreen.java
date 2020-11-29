package com.example.carpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.carpet.checkinternet.CheckNet;
import com.wang.avi.AVLoadingIndicatorView;

public class splashscreen extends AppCompatActivity {
    LinearLayout layout_check,layout_status;
    Handler handler;
    AVLoadingIndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
        Definition_element();
        //Check status internet
        if(CheckNet.getInstance(splashscreen.this).isOnline()) {
            layout_status.setVisibility(View.INVISIBLE);
            indicatorView.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    startActivity(new Intent(splashscreen.this,MainActivity.class));
                }
            },3000);
        } else {
            indicatorView.setVisibility(View.INVISIBLE);
            layout_status.setVisibility(View.VISIBLE);
        }
        //
        //Button check net
        layout_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckNet.getInstance(splashscreen.this).isOnline()) {
                    layout_status.setVisibility(View.INVISIBLE);
                    indicatorView.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(new Intent(splashscreen.this,MainActivity.class));
                        }
                    },3000);
                } else {
                    indicatorView.setVisibility(View.INVISIBLE);
                    layout_status.setVisibility(View.VISIBLE);
                }
            }
        });
        //
    }
    public void Definition_element(){
        layout_check=findViewById(R.id.lnr_check_intenet);
        layout_status=findViewById(R.id.lnr_status_net);
        indicatorView=findViewById(R.id.indicator_spalsh);
        handler=new Handler();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
