package com.example.app.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.app.DBHelper;
import com.example.app.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fragment1_2 extends Fragment {

    private LineChart lineChart;
    ArrayList<Entry> entries = new ArrayList<>(); // 값 - 인덱스 넣어주면 순차적으로 그려줘, y축이름(데이터값)
    ArrayList<String> xVals = new ArrayList<String>(); // X 축 이름 값
    DBHelper dbHelper;
    SQLiteDatabase db;
    private Context context;

    EditText kg;
    private Button sql;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1_2, container, false);

        context = container.getContext();


        sql = v.findViewById(R.id.weightBtn);
        kg = v.findViewById(R.id.weight);
        dbHelper = new DBHelper(getActivity());

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy년 MM월 dd일");
        String dateString = sdf1.format(date);


        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    dbHelper.insertWeight(dateString, Float.parseFloat(kg.getText().toString()));
                    Toast.makeText(context, "Data Insert Success", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(context, "Error2", Toast.LENGTH_LONG).show();
                }
            }
        });


        lineChart = (LineChart) v.findViewById(R.id.chart2);
        lineChart.setDrawGridBackground(false); //격자 구조
        lineChart.getDescription().setEnabled(false); //하단 description 표출 x
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM practice_library2 ORDER BY date", null);

        entries.clear();
        xVals.clear();
        int i = 0;

        while (cursor.moveToNext()) {
            //string -> date 변환 (문자열을 파싱하려면 문자열 형태와 같은 DateTime 생성해줘야돼
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date dt = new Date();
            try {
                dt = simpleDateFormat.parse(cursor.getString(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일");
            String heartFormat = sdf.format(dt);
            entries.add(new Entry(i++, Float.parseFloat(cursor.getString(1)))); // 문자형을 실수로 변환 y축
            xVals.add(heartFormat); // 하나씩 받아와서 넣어줌 (X축 시간으로 나온게 이거 때문)
        }

        cursor.close();
        db.close();


        LineDataSet linedataSet = new LineDataSet(entries, "몸무게 kg");
        linedataSet.setLineWidth(3); //라인 두께
        linedataSet.setCircleRadius(6); // 점 크기
        linedataSet.setDrawCircleHole(true);
        linedataSet.setDrawCircles(true);
        linedataSet.setCircleColor(Color.rgb(0, 200, 200)); // 점 색깔
        linedataSet.setColor(Color.rgb(0, 200, 200));
        linedataSet.setDrawHorizontalHighlightIndicator(false);
        linedataSet.setDrawHighlightIndicators(false);
        linedataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(linedataSet);

        LineData lineData = new LineData(dataSets);
        lineData.setDrawValues(true);               // 점에 갯수 보요주기
        lineData.setValueTextSize(10); //갯수 글자 크기
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(3); //가로 스크롤 생김 + 스크롤 넘어가기전 표출되는 데이터 값
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false); //zoom 기능
        lineChart.moveViewToX(1);
        lineChart.setScrollContainer(true);
        XAxis xAxis = lineChart.getXAxis(); //x축 설정
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 데이터 표시 위치
        xAxis.setLabelCount(50); //x축의 데이터를 최대 몇 개 까지 나타낼지에 대한 설정
        xAxis.setTextColor(Color.rgb(118, 118, 118));
        xAxis.setSpaceMax(1f); // 오른쪽으로 얼마나 남았는가
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        xAxis.enableGridDashedLine(10, 24, 0); //수직 격자선
        xAxis.setGranularity(1f);
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.rgb(163, 163, 163));
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(true);
        lineChart.invalidate();

        return v;
    }
}
