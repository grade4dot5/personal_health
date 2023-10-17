package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PowerManager;
import android.view.MenuItem;

import com.example.app.fragment.fragment1;
import com.example.app.fragment.fragment2;
import com.example.app.fragment.fragment3;
import com.example.app.fragment.fragment4;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class lobby_frame extends AppCompatActivity {

    BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_frame);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        //처음화면
        getSupportFragmentManager().beginTransaction().add(R.id.frame, new fragment1()).commit(); //FrameLayout에 fragment.xml 띄우기

        //바텀 네비게이션뷰 안의 아이템 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.menu1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new fragment1()).commit();
                        break;
                    case R.id.menu2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new fragment2()).commit();
                        break;
                    case R.id.menu3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new fragment3()).commit();
                        break;
                    case R.id.menu4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new fragment4()).commit();
                        break;
                }
                return true;
            }
        });
    }







}