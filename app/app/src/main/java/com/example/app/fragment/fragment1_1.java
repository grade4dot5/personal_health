package com.example.app.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app.DBHelper;
import com.example.app.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragment1_1 extends Fragment {
    static int year = 0;
    static int month = 0;
    String l = "";
    TextView tvp;
    String total;
    View v;
    private LineChart lineChart;
    Cursor z,x,c;
    int z1,x1,c1 =0;

    ArrayList<Entry> entries = new ArrayList<>(); // 값 - 인덱스 넣어주면 순차적으로 그려줘, y축이름(데이터값)
    ArrayList<Entry> entries2 = new ArrayList<>(); // 값 - 인덱스 넣어주면 순차적으로 그려줘, y축이름(데이터값)
    ArrayList<Entry> entries3 = new ArrayList<>(); // 값 - 인덱스 넣어주면 순차적으로 그려줘, y축이름(데이터값)

    ArrayList<String> xVals = new ArrayList<String>(); // X 축 이름 값
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getReadableDatabase();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_1_1, container, false);

        if (getMonth() < 10){
            l = "0" + String.valueOf(getMonth());
        }
        tvp = v.findViewById(R.id.tvp);
        lineChart = (LineChart) v.findViewById(R.id.chart);
        lineChart.setDrawGridBackground(false); //격자 구조
        lineChart.getDescription().setEnabled(false); //하단 description 표출 x





        z = db.rawQuery("SELECT sum(pushup) FROM practice_library where date like '" + getYear() + "년 " + l + "월 %'", null);
        z.moveToNext();
        z1 = z.getInt(0);

        x = db.rawQuery("SELECT sum(crunch) FROM practice_library where date like '" + getYear() + "년 " + l + "월 %'", null);
        x.moveToNext();
        x1 = x.getInt(0);

        c = db.rawQuery("SELECT sum(squart) FROM practice_library where date like '" + getYear() + "년 " + l + "월 %'", null);
        c.moveToNext();
        c1 = c.getInt(0);


        total = "푸쉬업 : " + z1 + "개   크런치 : " + x1 + "개   스쿼트 : " + c1 + "개";

        tvp.setText(total);


        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM practice_library where date like '" + getYear() + "년 " + l + "월 %'" + " ORDER BY date", null);


        entries.clear();
        xVals.clear();


        int i = 0;
        int a = 0;
        int b = 0;

        while (cursor.moveToNext()) {
            //string -> date 변환 (문자열을 파싱하려면 문자열 형태와 같은 DateTime 생성해줘야돼
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date dt = new Date();
            try {
                dt = simpleDateFormat.parse(cursor.getString(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
            String heartFormat = sdf.format(dt);
            entries.add(new Entry(i++, Float.parseFloat(cursor.getString(1)))); // 문자형을 실수로 변환 y축
            entries2.add(new Entry(a++, Float.parseFloat(cursor.getString(2)))); // 문자형을 실수로 변환 y축
            entries3.add(new Entry(b++, Float.parseFloat(cursor.getString(3)))); // 문자형을 실수로 변환 y축

            xVals.add(heartFormat); // 하나씩 받아와서 넣어줌 (X축 시간으로 나온게 이거 때문)
        }

        cursor.close();
        db.close();


        LineDataSet linedataSet = new LineDataSet(entries, "푸쉬업");
        LineDataSet linedataSet2 = new LineDataSet(entries2, "크런치");
        LineDataSet linedataSet3 = new LineDataSet(entries3, "스쿼트");

        linedataSet.setLineWidth(3); //라인 두께
        linedataSet.setCircleRadius(6); // 점 크기
        linedataSet.setDrawCircleHole(true);
        linedataSet.setDrawCircles(true);
        linedataSet.setCircleColor(Color.rgb(255, 155, 155)); // 점 색깔
        linedataSet.setColor(Color.rgb(255, 155, 155));
        linedataSet.setDrawHorizontalHighlightIndicator(false);
        linedataSet.setDrawHighlightIndicators(false);
        linedataSet.setDrawValues(false);

        linedataSet2.setLineWidth(3); //라인 두께
        linedataSet2.setCircleRadius(6); // 점 크기
        linedataSet2.setDrawCircleHole(true);
        linedataSet2.setDrawCircles(true);
        linedataSet2.setCircleColor(Color.rgb(55, 155, 155)); // 점 색깔
        linedataSet2.setColor(Color.rgb(55, 155, 155));
        linedataSet2.setDrawHorizontalHighlightIndicator(false);
        linedataSet2.setDrawHighlightIndicators(false);
        linedataSet2.setDrawValues(false);

        linedataSet3.setLineWidth(3); //라인 두께
        linedataSet3.setCircleRadius(6); // 점 크기
        linedataSet3.setDrawCircleHole(true);
        linedataSet3.setDrawCircles(true);
        linedataSet3.setCircleColor(Color.rgb(0, 68, 255)); // 점 색깔
        linedataSet3.setColor(Color.rgb(0, 68, 255));
        linedataSet3.setDrawHorizontalHighlightIndicator(false);
        linedataSet3.setDrawHighlightIndicators(false);
        linedataSet3.setDrawValues(false);




        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(linedataSet);
        dataSets.add(linedataSet2);
        dataSets.add(linedataSet3);
        LineData lineData = new LineData(dataSets);
        lineData.setDrawValues(true);               // 갯수 보요주기
        lineData.setValueTextSize(10); //갯수 글자 크기
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(5); //가로 스크롤 생김 + 스크롤 넘어가기전 표출되는 데이터 값
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false); //zoom 기능
        lineChart.moveViewToX(1);
        lineChart.setScrollContainer(true);;
        XAxis xAxis = lineChart.getXAxis(); //x축 설정
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 데이터 표시 위치
        xAxis.setLabelCount(30); //x축의 데이터를 최대 몇 개 까지 나타낼지에 대한 설정
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

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
}