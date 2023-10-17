package com.example.personal_health.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.app.R;

import java.util.Timer;
import java.util.TimerTask;


// 일반타이머
public class fragment4 extends Fragment {

    TextView hourET, minuteET, secondET, recordView;
    Button startBtn, stopBtn, recordBtn;

    int hour, minute, second = 0;

    private final Timer mTimer = new Timer();
    private TimerTask mTimerTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);
        setHasOptionsMenu(true);


        hourET = view.findViewById(R.id.hourET);
        minuteET = view.findViewById(R.id.minuteET);
        secondET = view.findViewById(R.id.secondET);

        hourET.setText("0" + hour + "");
        minuteET.setText("0" + minute + "");
        secondET.setText("0" + second + "");

        startBtn = view.findViewById(R.id.startBtn);
        stopBtn = view.findViewById(R.id.stopBtn);
        recordBtn = view.findViewById(R.id.recordBtn);
        recordView = view.findViewById(R.id.recordView);

        // 나중에 수정해야겠다

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTimerTask = createTimerTask();
                mTimer.schedule(mTimerTask, 0, 1000); //Timer 실행
            }
        });

        // 인터럽트 쓰는게 좋은데 못 하겠네
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimerTask.cancel();
            }
        });

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordView.setText(recordView.getText() + "\n" + hourET.getText() + " : " + minuteET.getText() + " : " + secondET.getText() + "\n");
            }
        });
        return view;
    }

    private TimerTask createTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                second++;
                secondET.setText(second + "");

                if (second == 60) {
                    minute++;
                    minuteET.setText(minute + "");
                    second = 0;
                }

                if (minute == 60) {
                    hour++;
                    hourET.setText(hour + "");
                    minute = 0;
                }

                //시, 분, 초가 10이하(한자리수) 라면
                // 숫자 앞에 0을 붙인다 ( 8 -> 08 )
                if (second <= 9) {
                    secondET.setText("0" + second);
                } else {
                    secondET.setText(Integer.toString(second));
                }

                if (minute <= 9) {
                    minuteET.setText("0" + minute);
                } else {
                    minuteET.setText(Integer.toString(minute));
                }

                if (hour <= 9) {
                    hourET.setText("0" + hour);
                } else {
                    hourET.setText(Integer.toString(hour));
                }
            }
        };
        return timerTask;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menutimer, menu);
    }

}
