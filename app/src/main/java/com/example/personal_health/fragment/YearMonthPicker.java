package com.example.personal_health.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.example.app.R;

import java.util.Calendar;

public class YearMonthPicker extends DialogFragment {

    private static final int MAX_YEAR = 2040;
    private static final int MIN_YEAR = 2020;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal = Calendar.getInstance();


    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        fragment1_1 a = new fragment1_1();

        View dialog = inflater.inflate(R.layout.year_month_picker, null);

        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);


        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        int year = cal.get(Calendar.YEAR);
        yearPicker.setValue(year);
        builder.setView(dialog);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {



                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(),0);

                a.setYear(yearPicker.getValue());
                a.setMonth(monthPicker.getValue());

                System.out.println(a.getYear());


                getParentFragmentManager().beginTransaction().replace(R.id.homeframe, new fragment1_1()).commit();

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                YearMonthPicker.this.getDialog().cancel();
            }
        });

        return builder.create();
    }





}
