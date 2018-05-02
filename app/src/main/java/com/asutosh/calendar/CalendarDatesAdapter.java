package com.asutosh.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.asutosh.calendar.Bean.CalendarDateInfo;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarDatesAdapter extends RecyclerView.Adapter<CalendarDatesAdapter.Viewholder> {

    private ArrayList<CalendarDateInfo> listDate;
    private Context context;
    private CustomCalendarView customCalendarView;
    private int SELECTED_POSITION;
    public CalendarDatesAdapter(CustomCalendarView customCalendarView,ArrayList<CalendarDateInfo> listDate, Context context) {
        this.customCalendarView=customCalendarView;
        this.listDate = listDate;
        this.context = context;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public RadioButton rbDate;
        public Viewholder(View itemView) {
            super(itemView);
            rbDate =  itemView.findViewById(R.id.rbDate);
        }
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_row, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        holder.rbDate.setEnabled(listDate.get(position).isEnabled());
        holder.rbDate.setChecked(listDate.get(position).isChecked());
        holder.rbDate.setText(String.valueOf(listDate.get(position).getDate().get(Calendar.DAY_OF_MONTH)));
        holder.rbDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customCalendarView!=null && listDate!=null &&
                        !customCalendarView.equalsDate(customCalendarView.SELECTED_DATE,listDate.get(position).getDate())) {
                    if(customCalendarView.SELECTED_DATE!=null && listDate.get(10).getDate().get(Calendar.MONTH)
                            == customCalendarView.SELECTED_DATE.get(Calendar.MONTH))
                    {
                        listDate.get(SELECTED_POSITION).setChecked(false);
                        notifyItemChanged(SELECTED_POSITION);
                    }
                    SELECTED_POSITION=position;
                    customCalendarView.dateSelected(customCalendarView.SELECTED_DATE=listDate.get(position).getDate());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }

}

