package com.asutosh.calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NumberPickerDialog  extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    ArrayList<Integer> listYear=new ArrayList<>();
    View view;
    RecyclerView recViewYear;
    CustomCalendarView customCalendarView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select year");
        view=LayoutInflater.from(getActivity()).inflate(R.layout.year_picker_dialog,null);
        builder.setView(view);
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        recViewYear=view.findViewById(R.id.recViewYear);

//        builder.setView(numberPicker);

        listYear.clear();
        for(int i=1950;i<2050;i++)
            listYear.add(i);

        YearListAdapter yearListAdapter=new YearListAdapter(customCalendarView,listYear,getActivity());
        recViewYear.setLayoutManager(new LinearLayoutManager(getActivity()));
        recViewYear.setAdapter(yearListAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recViewYear);
        return builder.create();

    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}