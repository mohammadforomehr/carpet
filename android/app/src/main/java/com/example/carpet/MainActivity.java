package com.example.carpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.BroadcastReceiver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.example.carpet.RecyclerAdapter.AboutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import com.pushpole.sdk.PushPole;


public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushPole.initialize(this,true);
        AppCenter.start(getApplication(), "084cccf4-4667-426a-befc-7119dd16a8ee", Analytics.class, Crashes.class);
        getSupportActionBar().hide();
        bottomNav=findViewById(R.id.botton_navigation);
        bottomNav.setOnNavigationItemSelectedListener(nav);
        bottomNav.getMenu().clear();
        bottomNav.inflateMenu(R.menu.botton_navigation);
        bottomNav.getMenu().getItem(0).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener nav=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectfragment=null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectfragment=new HomeFragment();
                            break;
                        case R.id.nav_categoris:
                            selectfragment=new CategoriFragment();
                            break;
                        case R.id.nav_news:
                            selectfragment=new NewsFragment();
                            break;
                        case R.id.nav_search:
                            selectfragment=new SearchFragment();
                            break;
                        case R.id.nav_about:
                            selectfragment=new AboutFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectfragment).commit();
                    return true;
                }
            };


    @Override
    public void onBackPressed() {
        if (bottomNav.getMenu().getItem(0).isChecked()){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast toast=Toast.makeText(MainActivity.this, "برای خروج دوباره بازگشت را بزنید", Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.parseColor("#000000"));
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.parseColor("#ffffff"));
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 3000);
        }else{
            bottomNav.getMenu().getItem(0).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

}
