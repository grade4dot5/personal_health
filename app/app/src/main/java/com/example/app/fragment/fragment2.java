package com.example.app.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.example.app.DBHelper;
import com.example.app.R;
import com.example.app.start;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class fragment2 extends Fragment {

    Calendar calendar;
    Long today;
    MaterialDatePicker materialDatePicker;
    SimpleDateFormat simpleDateFormat;
    private TextView date_picker_text;
    private Button date_picker_btn;
    private Button sql;
    DBHelper mydb;
    EditText pushupcount, crunchcount, squartcount;
    String dateString1;
    private Context context;




    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_2,container,false);

        getActivity().setTitle("하루 목표 설정하기");


        context = container.getContext();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        today = MaterialDatePicker.todayInUtcMilliseconds();
        date_picker_text = view.findViewById(R.id.date_picker_text);
        date_picker_btn = view.findViewById(R.id.date_picker_btn);
        sql = view.findViewById(R.id.sql);
        pushupcount = view.findViewById(R.id.pushupcount);
        crunchcount = view.findViewById(R.id.crunchcount);
        squartcount = view.findViewById(R.id.squartcount);


        // 중요 이게 되네?        fragment는(onCreateView) this 대신 getActivity 를 쓴다
        mydb = new DBHelper(getActivity());


        // 단일 날짜 선택시
        date_picker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Date Picker").setSelection(today).build();

                materialDatePicker.show(getParentFragmentManager(),"Date Picker");

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                        Date date = new Date();
                        date.setTime(selection);
                        String dateString = simpleDateFormat.format(date);
                        date_picker_text.setText(dateString);

                        dateString1 = simpleDateFormat.format(date);

                        try{
                            Cursor res = mydb.getAllData(dateString1);
                            if(res.getCount() == 0){
                                Toast.makeText(context, "Data is Empty", Toast.LENGTH_LONG).show();
                                pushupcount.setText("0");
                                crunchcount.setText("0");
                                squartcount.setText("0");
                                return;
                            }

                            res.moveToNext();

                            pushupcount.setText(res.getString(1));
                            crunchcount.setText(res.getString(2));
                            squartcount.setText(res.getString(3));

                            Toast.makeText(context, "Loading Success", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(context, "Error1", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        // 목표 설정 선택시
        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{
                    mydb.insertData2(dateString1,
                            Integer.parseInt(pushupcount.getText().toString()),
                            Integer.parseInt(crunchcount.getText().toString()),
                            Integer.parseInt(squartcount.getText().toString()));
                    Toast.makeText(context, "Data Insert Success", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(context, "Error2", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

}
