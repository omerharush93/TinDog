package com.example.tindog.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tindog.R;
import com.example.tindog.adapters.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 mviewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        tabLayout = findViewById(R.id.main_bottom_nav);
        mviewPager = findViewById(R.id.main_tabs_pager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), this.getLifecycle());
        mviewPager.setAdapter(tabAdapter);
        mviewPager.setOffscreenPageLimit(2);
        int[] icons = {R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_person_24};
        new TabLayoutMediator(tabLayout, mviewPager,
                (tab, position) -> tab.setIcon(icons[position])
        ).attach();

    }
}