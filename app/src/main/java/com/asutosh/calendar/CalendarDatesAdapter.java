package com.asutosh.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asutosh.calendar.Bean.CalendarDateInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarDatesAdapter extends RecyclerView.Adapter{

    private ArrayList<CalendarDateInfo> listDate;
    private Context context;
    private CustomCalendarView customCalendarView;
    private int SELECTED_POSITION;
    private final int VIEW_TYPE_TITLE=123;
    private final int VIEW_TYPE_DATA=124;
    public CalendarDatesAdapter(CustomCalendarView customCalendarView,ArrayList<CalendarDateInfo> listDate, Context context) {
        this.customCalendarView=customCalendarView;
        this.listDate = listDate;
        this.context = context;
    }

    public class DateViewholder extends RecyclerView.ViewHolder {
        public RadioButton rbDate;
        public DateViewholder(View itemView) {
            super(itemView);
            rbDate =  itemView.findViewById(R.id.rbDate);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvMonthNameTitle;
        LinearLayout llCalendarDates;
        public TitleViewHolder(final View view) {
            super(view);
            tvMonthNameTitle=view.findViewById(R.id.tvMonthNameTitle);
            llCalendarDates=view.findViewById(R.id.llCalendarDates);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listDate.get(position).isTitle()?VIEW_TYPE_TITLE:VIEW_TYPE_DATA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_DATA)
        return new DateViewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_row, parent, false));
        else
            return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rv_single_item_for_calendar_title, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(listDate.get(position).isTitle()) {
            TitleViewHolder titleViewHolder= (TitleViewHolder) viewHolder;
            titleViewHolder.tvMonthNameTitle.setText(new SimpleDateFormat("MMMM yyyy").format(listDate.get(position).getDate().getTime()));
        }
        else {
            DateViewholder holder= (DateViewholder) viewHolder;
            holder.rbDate.setBackgroundResource(R.drawable.radio_back);
            holder.rbDate.setTextColor(context.getResources().getColorStateList(R.color.radio_back_text));
            holder.rbDate.setEnabled(listDate.get(position).isEnabled());
            holder.rbDate.setChecked(listDate.get(position).isChecked());
            if (!listDate.get(position).isChecked() && customCalendarView.equalsDate(listDate.get(position).getDate(), customCalendarView.calendarTodayDate)) {
                holder.rbDate.setBackgroundResource(R.drawable.radio_todays_date);
                holder.rbDate.setTextColor(Color.WHITE);
            }
            holder.rbDate.setText(String.valueOf(listDate.get(position).getDate().get(Calendar.DAY_OF_MONTH)));
            holder.rbDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (customCalendarView != null && listDate != null &&
                            !customCalendarView.equalsDate(customCalendarView.SELECTED_DATE, listDate.get(position).getDate())) {
                        if (customCalendarView.SELECTED_DATE != null) {
                            listDate.get(SELECTED_POSITION).setChecked(false);
                            notifyItemChanged(SELECTED_POSITION);
                        }
                        setSelection(position);
                    }
                }
            });

            if(listDate.get(position).isHidden())
                holder.rbDate.setVisibility(View.INVISIBLE);
            else
                holder.rbDate.setVisibility(View.VISIBLE);

            if(listDate.get(position).isEnabled() && !listDate.get(position).isHidden() && listDate.get(position).isMultiSelected()) {
            holder.rbDate.setEnabled(false);
            holder.rbDate.setBackgroundResource(R.drawable.radio_multiselected_date);
            holder.rbDate.setTextColor(Color.WHITE);
        }
        }
    }

    private void setSelection(int newPosition)
    {
        if(customCalendarView.SELECTED_POSITION==-1)
        {
            customCalendarView.SELECTED_POSITION=SELECTED_POSITION=newPosition;
        }else
        {
            if(customCalendarView.MULTI_SELECT_START_POSITION==-1 && customCalendarView.compareDates(listDate.get(customCalendarView.SELECTED_POSITION).getDate(),listDate.get(newPosition).getDate())<0)
            {
                customCalendarView.MULTI_SELECT_START_POSITION=customCalendarView.SELECTED_POSITION;
                for(int i=customCalendarView.MULTI_SELECT_START_POSITION;i<=newPosition;i++)
                {
                    if(!listDate.get(i).isHidden() && listDate.get(i).isEnabled() && !listDate.get(i).isTitle())
                    {
                        listDate.get(i).setMultiSelected(true);
                    }
                }
                customCalendarView.SELECTED_POSITION=SELECTED_POSITION=newPosition;
                notifyItemRangeChanged(customCalendarView.MULTI_SELECT_START_POSITION,newPosition);
            }else if(customCalendarView.MULTI_SELECT_START_POSITION!=-1)
            {
                for(int i=customCalendarView.MULTI_SELECT_START_POSITION;i<=customCalendarView.SELECTED_POSITION;i++)
                {
                        listDate.get(i).setMultiSelected(false);
                }
                notifyItemRangeChanged(customCalendarView.MULTI_SELECT_START_POSITION,SELECTED_POSITION);
                listDate.get(SELECTED_POSITION=newPosition).setChecked(true);
                notifyItemChanged(SELECTED_POSITION);
                customCalendarView.MULTI_SELECT_START_POSITION=-1;
                customCalendarView.SELECTED_POSITION=SELECTED_POSITION = newPosition;
            }else
            {
                customCalendarView.SELECTED_POSITION=SELECTED_POSITION = newPosition;
                listDate.get(SELECTED_POSITION).setChecked(true);
                notifyItemChanged(SELECTED_POSITION);
            }
        }

//        SELECTED_POSITION = newPosition;
//        listDate.get(SELECTED_POSITION).setChecked(true);
        customCalendarView.dateSelected(customCalendarView.SELECTED_DATE = listDate.get(newPosition).getDate());
    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }

}

