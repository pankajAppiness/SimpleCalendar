package com.asutosh.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asutosh.calendar.Bean.CalendarDateInfo;

import java.util.ArrayList;
import java.util.Calendar;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.Viewholder> {

    private ArrayList<Integer> listDates;
    Context context;
    CustomCalendarView customCalendarView;
    public YearListAdapter(CustomCalendarView customCalendarView,ArrayList<Integer> listDates, Context context) {
        this.customCalendarView=customCalendarView;
        this.listDates = listDates;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView tvYear;
        public LinearLayout llYear;
        public Viewholder(View itemView) {
            super(itemView);
            tvYear =  itemView.findViewById(R.id.tvYear);
            llYear =  itemView.findViewById(R.id.llYear);
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_number_picker_item, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        holder.tvYear.setText(String.valueOf(listDates.get(position)));
        holder.llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customCalendarView.setYear(listDates.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDates.size();
    }

}

