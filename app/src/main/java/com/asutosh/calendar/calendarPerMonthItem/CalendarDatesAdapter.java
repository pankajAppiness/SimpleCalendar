package com.asutosh.calendar.calendarPerMonthItem;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.asutosh.calendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CalendarDatesAdapter extends RecyclerView.Adapter {

    private ArrayList<CalendarDateInfo> listDate;
    private Context context;
    private CustomCalendarView customCalendarView;
    private final int VIEW_TYPE_TITLE = 123;
    private final int VIEW_TYPE_DATA = 124;
    private Map<String, CalendarMonthInfo> mapCalendarMonthInfo;
    private List<String> listMonthNames;

    private ArrayList<TitleViewHolder> listTitleViewHolder=new ArrayList<>();
    public CalendarDatesAdapter(CustomCalendarView customCalendarView, ArrayList<CalendarDateInfo> listDate, Context context, Map<String, CalendarMonthInfo> mapCalendarMonthInfo, List<String> listMonthNames) {
        this.customCalendarView = customCalendarView;
        this.listDate = listDate;
        this.context = context;
        this.mapCalendarMonthInfo = mapCalendarMonthInfo;
//        listCalendarMonthInfo= (List<CalendarMonthInfo>) mapCalendarMonthInfo.values();
        this.listMonthNames = listMonthNames;
//        arMonthNames=mapCalendarMonthInfo.keySet().toArray(String[])
//        arMonthNames=mapCalendarMonthInfo.keySet().toArray();
    }

    public class DateViewholder extends RecyclerView.ViewHolder {
        public RadioButton rbDate;
        public LinearLayout lldate;
        public DateViewholder(View itemView) {
            super(itemView);
            rbDate = itemView.findViewById(R.id.rbDate);
            lldate = itemView.findViewById(R.id.lldate);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        TextView tvMonthNameTitle;
        LinearLayout llCalendarDates;
        List<LinearLayout> listSubLinearLayout = new ArrayList<>();
        List<DateViewholder> listAllDateViewholder = new ArrayList<>();

        public TitleViewHolder(final View view) {
            super(view);
            tvMonthNameTitle = view.findViewById(R.id.tvMonthNameTitle);
            llCalendarDates = view.findViewById(R.id.llCalendarDates);
            setUpDatesForMonth(llCalendarDates, listSubLinearLayout, listAllDateViewholder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listDate.get(position).isTitle() ? VIEW_TYPE_TITLE : VIEW_TYPE_DATA;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_single_item_for_calendar_title_new, parent, false));

    }


    public DateViewholder onCreateDateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_row, parent, false);
        parent.addView(itemView);
        return new DateViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int positionMain) {
        listTitleViewHolder.add((TitleViewHolder) viewHolder);
        TitleViewHolder titleViewHolder = (TitleViewHolder) viewHolder;
        titleViewHolder.tvMonthNameTitle.setText(listMonthNames.get(positionMain));
        CalendarMonthInfo calendarMonthInfo = mapCalendarMonthInfo.get(listMonthNames.get(positionMain));
        final List<CalendarDateInfo> listDate = calendarMonthInfo.getListCalendarDateInfo();
        for (int position = 0; position < calendarMonthInfo.getListCalendarDateInfo().size(); position++) {
            updateDate(listDate,titleViewHolder,position,positionMain);
        }
        if (calendarMonthInfo.getListCalendarDateInfo().size() <= 35)
            titleViewHolder.listSubLinearLayout.get(5).setVisibility(View.GONE);
        else
            titleViewHolder.listSubLinearLayout.get(5).setVisibility(View.VISIBLE);
    }

    private void setSelection(int newSelectedItemPositionInList,int newSelectedItemPositionInCalendar) {
        if (customCalendarView.SELECTED_ITEM_POSITION_IN_LIST == -1) {
            customCalendarView.SELECTED_ITEM_POSITION_IN_LIST = newSelectedItemPositionInList;
            customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR=newSelectedItemPositionInCalendar;
            mapCalendarMonthInfo.get(listMonthNames.get(newSelectedItemPositionInList)).getListCalendarDateInfo().
                    get(newSelectedItemPositionInCalendar).setChecked(true);
            notifyItemChanged(newSelectedItemPositionInList);
        } else {
            if (customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST == -1 &&
                    (customCalendarView.SELECTED_ITEM_POSITION_IN_LIST<newSelectedItemPositionInList
                     || (customCalendarView.SELECTED_ITEM_POSITION_IN_LIST==newSelectedItemPositionInList
                        && customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR<newSelectedItemPositionInCalendar)
                    )) {
                customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST = customCalendarView.SELECTED_ITEM_POSITION_IN_LIST;
                customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR=customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR;
                for (int i = customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST; i <= newSelectedItemPositionInList; i++) {
                    CalendarMonthInfo calendarMonthInfo=mapCalendarMonthInfo.get(listMonthNames.get(i));
                    int MAX_POSITION=i==newSelectedItemPositionInList?newSelectedItemPositionInCalendar:calendarMonthInfo.listCalendarDateInfo.size()-1;
                    int MIN_POSITION=i==customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST?customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR:0;
                    for(int k=MIN_POSITION;k<=MAX_POSITION;k++)
                    if (!calendarMonthInfo.getListCalendarDateInfo().get(k).isHidden() &&
                            calendarMonthInfo.getListCalendarDateInfo().get(k).isEnabled()
//                           && !calendarMonthInfo.getListCalendarDateInfo().get(i).isTitle()
                            ) {
                        calendarMonthInfo.getListCalendarDateInfo().get(k).setMultiSelected(true);
                    }
                }
                customCalendarView.SELECTED_ITEM_POSITION_IN_LIST = newSelectedItemPositionInList;
                customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR=newSelectedItemPositionInCalendar;
//                if(customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST==0 && newSelectedItemPositionInList==listMonthNames.size()-1)
//                    notifyDataSetChanged();
//                else
                notifyItemRangeChanged(customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST, newSelectedItemPositionInList+1);

            } else if (customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST != -1) {
//                for (int i = customCalendarView.MULTI_SELECT_START_POSITION; i <= customCalendarView.SELECTED_POSITION; i++) {
//                    listDate.get(i).setMultiSelected(false);
//                }
                for (int i = customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST; i <= customCalendarView.SELECTED_ITEM_POSITION_IN_LIST; i++) {
                    CalendarMonthInfo calendarMonthInfo=mapCalendarMonthInfo.get(listMonthNames.get(i));
                    int MAX_POSITION=i==customCalendarView.SELECTED_ITEM_POSITION_IN_LIST?customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR:calendarMonthInfo.listCalendarDateInfo.size()-1;
                    int MIN_POSITION=i==customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST?customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR:0;
                    for(int k=MIN_POSITION;k<=MAX_POSITION;k++)
                            calendarMonthInfo.getListCalendarDateInfo().get(k).setMultiSelected(false);
                }
                notifyItemRangeChanged(customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST, customCalendarView.SELECTED_ITEM_POSITION_IN_LIST);


                mapCalendarMonthInfo.get(listMonthNames.get(newSelectedItemPositionInList)).getListCalendarDateInfo()
                        .get(newSelectedItemPositionInCalendar).setChecked(true);
                notifyItemChanged(newSelectedItemPositionInList);


                customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST = -1;
                customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR = -1;
                customCalendarView.SELECTED_ITEM_POSITION_IN_LIST=newSelectedItemPositionInList;
                customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR=newSelectedItemPositionInCalendar;
            } else {
                customCalendarView.SELECTED_ITEM_POSITION_IN_LIST =newSelectedItemPositionInList;
                customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR=newSelectedItemPositionInCalendar;
                mapCalendarMonthInfo.get(listMonthNames.get(newSelectedItemPositionInList)).getListCalendarDateInfo().
                get(newSelectedItemPositionInCalendar).setChecked(true);
                notifyItemChanged(newSelectedItemPositionInList);
            }
        }

//        SELECTED_POSITION = newPosition;
//        listDate.get(SELECTED_POSITION).setChecked(true);


//        customCalendarView.dateSelected(customCalendarView.SELECTED_DATE = listDate.get(newPosition).getDate());
    }

    @Override
    public int getItemCount() {
        return listMonthNames.size();
    }

    private void setUpDatesForMonth(LinearLayout llMainParent, List<LinearLayout> listSubLinearLayout, List<DateViewholder> listAllDateViewholder) {
        for (int i = 0; i < 42; i++) {
            if (listSubLinearLayout.size() == 0 || listSubLinearLayout.size() >= i / 7) {
//                LinearLayout linearLayout = new LinearLayout(context);
//                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0,5,0,5);
//                linearLayout.setLayoutParams(layoutParams);
//                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.rv_single_item_calendar_item_row,llMainParent,false);
//                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                listSubLinearLayout.add(linearLayout);
                llMainParent.addView(linearLayout);
            }
            DateViewholder holder = onCreateDateViewHolder(listSubLinearLayout.get(i / 7));
            holder.rbDate.setBackgroundResource(R.drawable.radio_back);
            holder.rbDate.setTextColor(context.getResources().getColorStateList(R.color.radio_back_text));
            listAllDateViewholder.add(holder);
//          holder.rbDate.setText(String.valueOf(calendarMonthInfo.getListCalendarDateInfo().get(i).getDate().get(Calendar.DAY_OF_MONTH)));
        }
    }

    private void updateDate(final List<CalendarDateInfo> listDate,TitleViewHolder titleViewHolder,int position,final int positionMain)
    {
        RadioButton rbDate = titleViewHolder.listAllDateViewholder.get(position).rbDate;
        titleViewHolder.listAllDateViewholder.get(position).lldate.setBackgroundColor(Color.TRANSPARENT);
        rbDate.setBackgroundResource(R.drawable.radio_back);
        rbDate.setTextColor(context.getResources().getColorStateList(R.color.radio_back_text));
        rbDate.setEnabled(listDate.get(position).isEnabled());
        rbDate.setChecked(listDate.get(position).isChecked());
        if (!listDate.get(position).isChecked() && customCalendarView.equalsDate(listDate.get(position).getDate(), customCalendarView.calendarTodayDate)) {
//                rbDate.setBackgroundResource(R.drawable.radio_todays_date);
            rbDate.setTextColor(Color.MAGENTA);
        }
        rbDate.setText(String.valueOf(listDate.get(position).getDate().get(Calendar.DAY_OF_MONTH)));
        final int selectedDatePosition=position;
        rbDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customCalendarView != null && listDate != null &&
                        !(customCalendarView.equalsDate(customCalendarView.SELECTED_DATE, listDate.get(selectedDatePosition).getDate()) && customCalendarView.SELECTED_ITEM_POSITION_IN_LIST==positionMain)) {

                    if (customCalendarView.SELECTED_ITEM_POSITION_IN_LIST != -1) {
//                            listDate.get(SELECTED_POSITION).setChecked(false);
//                            notifyItemChanged(SELECTED_POSITION);
                        mapCalendarMonthInfo.get(listMonthNames.get(customCalendarView.SELECTED_ITEM_POSITION_IN_LIST)).getListCalendarDateInfo()
                                .get(customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR).setChecked(false);
                        notifyItemChanged(customCalendarView.SELECTED_ITEM_POSITION_IN_LIST);
                    }
                    setSelection(positionMain,selectedDatePosition);
                }
            }
        });

        if (listDate.get(position).isHidden())
            rbDate.setVisibility(View.INVISIBLE);
        else
            rbDate.setVisibility(View.VISIBLE);

        if (listDate.get(position).isEnabled() && !listDate.get(position).isHidden() && listDate.get(position).isMultiSelected()) {
            rbDate.setEnabled(false);
            rbDate.setBackgroundResource(R.drawable.radio_multiselected_date);
            rbDate.setTextColor(Color.WHITE);
            if(positionMain==customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_LIST && position==customCalendarView.MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR)
                titleViewHolder.listAllDateViewholder.get(position).lldate.setBackgroundResource(R.drawable.bg_shape_rect_left_radius);
            else if(positionMain==customCalendarView.SELECTED_ITEM_POSITION_IN_LIST && position==customCalendarView.SELECTED_ITEM_POSITION_IN_CALENDAR)
                titleViewHolder.listAllDateViewholder.get(position).lldate.setBackgroundResource(R.drawable.bg_shape_rect_right_radius);
            else
                titleViewHolder.listAllDateViewholder.get(position).lldate.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
}

