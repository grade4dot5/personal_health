package com.example.personal_health;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.app.R;

public class start extends AppCompatActivity {
    TextView text;
    Animation anim;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        text = findViewById(R.id.text);
        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(200);
        anim.setStartOffset(200);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        text.startAnimation(anim);
    }


    public void OnClickHandler(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("※주의사항※").setMessage(" 준비 운동 없이 무리한 동작을 시작할 경우 근육이 놀라 경련과 " +
                "근육통이 생길 수 있으며, 특히 고혈압 환자의 경우 부작용이 생길 수 있습니다. " +
                "시작 전에는 항상 준비 운동으로 5~10분 정도 몸을 풀어준 후 본운동에 들어가는 습관을 들이는 것이 좋습니다.");
        builder.setPositiveButton("알겠습니다.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), lobby_frame.class);
                startActivity(intent);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}