package com.example.app.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.example.app.R;


public class fragment1 extends Fragment {

    Button btnYearMonthPicker;
    Button weightbun;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        }
    };

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_1,container,false);
        getActivity().setTitle("홈");

        btnYearMonthPicker = view.findViewById(R.id.homebtn1);
        weightbun = view.findViewById(R.id.homebtn2);


        // 1번째 버튼 차트
        btnYearMonthPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YearMonthPicker pd = new YearMonthPicker();
                pd.setListener(d);
                pd.show(getParentFragmentManager(), "YearMonthPickerTest");
            }
        });
        // 2번째 버튼
        weightbun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.homeframe, new fragment1_2()).commit();
            }
        });



        return view;
    }





}
