package com.asutosh.calendar.calendarPerMonthItem;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.asutosh.calendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomCalendarView extends LinearLayout {
    SimpleDateFormat month_date;
    String month_name;
    int days_in_current_month;
    CalendarDatesAdapter adapter;
    int thisMonth;
    Calendar calendar;
    Context context;
//    TextView tv_month;
    RecyclerView rv_dates;
//    ImageView ivLeftArrow;
//    ImageView ivRightArrow;

    private Set<Days> setEnableOnlyDays;
    private Set<Days> setDisableOnlyDays;
    Set<Calendar> setEnabledDates;
    Set<Calendar> setDisabledDates;
    Calendar SELECTED_DATE;
    int MULTI_SELECT_ITEM_START_POSITION_IN_LIST=-1;
    int MULTI_SELECT_ITEM_START_POSITION_IN_CALENDAR=-1;
    int SELECTED_ITEM_POSITION_IN_CALENDAR=-1;
    int SELECTED_ITEM_POSITION_IN_LIST=-1;
    private boolean ENABLE_ALL_DAYS = true;
    ArrayList<CalendarDateInfo> listDates = new ArrayList<>();
    private OnDateSelected onDateSelected;

    Calendar MIN_DATE;
    Calendar MAX_DATE;
    Calendar calendarTodayDate;

    Map<String,CalendarMonthInfo> mapCalendarMonthInfo=new HashMap<>();
    List<String> listMonths=new ArrayList<>();
    public CustomCalendarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context=context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.layout_custom_calendar_view, this, true);
//        tv_month = findViewById(R.id.tv_month);
        rv_dates = findViewById(R.id.rv_dates);
//        ivLeftArrow = findViewById(R.id.imv_left_arrow);
//        ivRightArrow = findViewById(R.id.imv_right_arrow);

//        tv_month.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showYearPickerDialogue();
//            }
//        });
        initializeViews();
    }

    private void initializeViews() {
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(context, 7);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position < listDates.size()) {
//                    if (listDates.get(position).isTitle())
//                        return 7;
//                }
//                return 1;
//            }
//        });
//        rv_dates.setLayoutManager(gridLayoutManager);
        rv_dates.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CalendarDatesAdapter(this, listDates, context,mapCalendarMonthInfo,listMonths);
        rv_dates.setAdapter(adapter);
//        ivRightArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showNextMonth();
//            }
//        });
//        ivLeftArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showPreviousMonth();
//            }
//        });
//
//        rv_dates.setOnTouchListener(new OnSwipeTouchListener(context) {
//            public void onSwipeTop() {
//
//            }
//
//            public void onSwipeRight() {
//                if (!isCurrentMinMonth())
//                    showPreviousMonth();
//            }
//
//            public void onSwipeLeft() {
//                if (!isCurrentMaxMonth())
//                    showNextMonth();
//            }
//
//            public void onSwipeBottom() {
//
//            }
//
//        });

        calendar = Calendar.getInstance();
        calendarTodayDate=Calendar.getInstance();
        month_date = new SimpleDateFormat("MMMM");
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }


    public void setUpCalendar() {
        listDates.clear();
//        month_name = month_date.format(calendar.getTime());
//        String year = new SimpleDateFormat("yyyy").format(calendar.getTime());
//        tv_month.setText(month_name + " " + year);
        setCalendarForMonth();
        for(int i=0;i<12;i++) {
            thisMonth++;
            calendar = getCurrentDatePlusMonth(thisMonth);
            setCalendarForMonth();
        }
        adapter.notifyDataSetChanged();
//        enableDisableLeftRightArrow();
    }

    private void setCalendarForMonth()
    {
        List<CalendarDateInfo> listDatesForMonth=new ArrayList<>();
        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar calendarNew1 = Calendar.getInstance();
        calendarNew1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
//        listDates.add(new CalendarDateInfo(true,calendarNew1,true));
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.setTimeInMillis(calendar.getTimeInMillis());
            calendarNew.add(Calendar.MONTH, -1);
            calendarNew.set(Calendar.DAY_OF_MONTH,
                    calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH) - (calendar.get(Calendar.DAY_OF_WEEK) - i - 1));
            listDates.add(new CalendarDateInfo(false,false, calendarNew,true,false));
            listDatesForMonth.add(new CalendarDateInfo(false,false, calendarNew,true,false));
        }
        for (int i = 1; i <= days_in_current_month; i++) {
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i);
            CalendarDateInfo calendarDateInfo=createCalendarDateInfo(calendarNew);
            listDates.add(calendarDateInfo);
            listDatesForMonth.add(calendarDateInfo);
        }
        int size;
        if ((size = listDates.size() % 7) != 0)
            for (int i = 0; i < 7 - size; i++) {
                Calendar calendarNew = Calendar.getInstance();
                calendarNew.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i + 1);
                calendarNew.add(Calendar.MONTH, 1);
                listDates.add(new CalendarDateInfo(false,false, calendarNew,true,false));
                listDatesForMonth.add(new CalendarDateInfo(false,false, calendarNew,true,false));
            }
            CalendarMonthInfo calendarMonthInfo=new CalendarMonthInfo();
        calendarMonthInfo.setListCalendarDateInfo(listDatesForMonth);
       mapCalendarMonthInfo.put(new SimpleDateFormat("MMMMyyyy").format(calendar.getTime()),calendarMonthInfo);
       listMonths.add(new SimpleDateFormat("MMMMyyyy").format(calendar.getTime()));
    }


    private void showNextMonth() {
        thisMonth++;
        calendar = getCurrentDatePlusMonth(thisMonth);
        setUpCalendar();
    }


    private void showPreviousMonth() {
        thisMonth--;
        calendar = getCurrentDatePlusMonth(thisMonth);
        setUpCalendar();
    }


    private Calendar getCurrentDatePlusMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, month);
        return calendar;
    }

    public void setEnabledDay(Days days) {
        if (setEnableOnlyDays == null)
            setEnableOnlyDays = new HashSet<>();
        setEnableOnlyDays.clear();
        setEnableOnlyDays.add(days);
        setDisableOnlyDays = null;
    }

    public void setEnabledDays(Set<Days> days) {
        if (setEnableOnlyDays == null)
            setEnableOnlyDays = new HashSet<>();
        setEnableOnlyDays.clear();
        setEnableOnlyDays.addAll(days);
        setDisableOnlyDays = null;
    }

    public void setDisabledDay(Days days) {
        if (setDisableOnlyDays == null)
            setDisableOnlyDays = new HashSet<>();
        setDisableOnlyDays.add(days);
        setEnableOnlyDays = null;
    }

    public void setDisabledDays(Set<Days> days) {
        if (setDisableOnlyDays == null)
            setDisableOnlyDays = new HashSet<>();
        setDisableOnlyDays.addAll(days);
        setEnableOnlyDays = null;
    }

    public void setEnabledDates(Set<Calendar> enabledDate) {
        if (setEnabledDates == null)
            setEnabledDates = new HashSet<>();
        setEnabledDates.addAll(enabledDate);
        setDisabledDates = null;
    }

    public void setDisabledDates(Set<Calendar> disabledDate) {
        if (setDisabledDates == null)
            setDisabledDates = new HashSet<>();
        setDisabledDates.addAll(disabledDate);
        setEnabledDates = null;
    }

    public void enableAllDays() {
        ENABLE_ALL_DAYS = true;
    }

    public void disableAllDays() {
        ENABLE_ALL_DAYS = false;
    }

    public void setMinDate(Calendar calendar) {
        MIN_DATE = calendar;
    }

    public void setMaxDate(Calendar calendar) {
        MAX_DATE = calendar;
    }

    public void setMinDate(long date)
    {
        MIN_DATE=Calendar.getInstance();
        MIN_DATE.setTimeInMillis(date);
    }

    public void setMaxDate(long date)
    {
        MAX_DATE=Calendar.getInstance();
        MAX_DATE.setTimeInMillis(date);
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setOnDateSelectedListener(OnDateSelected onDateSelected) {
        this.onDateSelected = onDateSelected;
    }

    private CalendarDateInfo createCalendarDateInfo(Calendar calendar) {
        CalendarDateInfo calendarDateInfo = new CalendarDateInfo(ENABLE_ALL_DAYS, calendar);
        Days days = getDay(calendar.get(Calendar.DAY_OF_WEEK));
        if (setEnableOnlyDays != null && setEnableOnlyDays.contains(days))
            calendarDateInfo.setEnabled(true);
        else if (setDisableOnlyDays != null && setDisableOnlyDays.contains(days))
            calendarDateInfo.setEnabled(false);

        if (setDisabledDates != null)
            for (Calendar calendar1 : setDisabledDates) {
                if (equalsDate(calendar, calendar1)) {
                    calendarDateInfo.setEnabled(false);
                    break;
                }
            }
        else if (setEnabledDates != null)
            for (Calendar calendar1 : setEnabledDates) {
                if (equalsDate(calendar, calendar1)) {
                    calendarDateInfo.setEnabled(true);
                    break;
                }
            }
        if (isLowerThenMin(calendar) || isHigherThenMax(calendar))
            calendarDateInfo.setEnabled(false);
        else if (SELECTED_DATE != null && equalsDate(SELECTED_DATE, calendar))
            calendarDateInfo.setChecked(true);
        return calendarDateInfo;
    }

    private Days getDay(int DAY_OF_WEEK) {
        switch (DAY_OF_WEEK) {
            case 1:
                return Days.SUNDAY;
            case 2:
                return Days.MONDAY;
            case 3:
                return Days.TUESDAY;
            case 4:
                return Days.WEDNESDAY;
            case 5:
                return Days.THURSDAY;
            case 6:
                return Days.FRIDAY;
            default:
                return Days.SATURDAY;

        }
    }

    public enum Days {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    boolean equalsDate(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null)
            return false;
        return convertDateToString(cal1).equals(convertDateToString(cal2));
    }

    void dateSelected(Calendar calendar) {
        if (calendar != null) {
            SELECTED_DATE = calendar;
            if (onDateSelected != null)
                onDateSelected.onSelected(calendar);
        }
    }

    public interface OnDateSelected {
        public void onSelected(Calendar calendar);
    }

    private void enableDisableLeftRightArrow() {
//        if(isCurrentMinMonth())
//            ivLeftArrow.setVisibility(View.INVISIBLE);
//        else
//            ivLeftArrow.setVisibility(View.VISIBLE);
//        if(isCurrentMaxMonth())
//        ivRightArrow.setVisibility(View.INVISIBLE);
//        else
//            ivRightArrow.setVisibility(View.VISIBLE);
    }

    private boolean isCurrentMinMonth() {
        return MIN_DATE != null && MIN_DATE.get(Calendar.MONTH) >= calendar.get(Calendar.MONTH) &&
                MIN_DATE.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
    }

    private boolean isCurrentMaxMonth() {
        return MAX_DATE != null && MAX_DATE.get(Calendar.MONTH) <= calendar.get(Calendar.MONTH) &&
                MAX_DATE.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
    }

    private boolean isLowerThenMin(Calendar calendar) {
        if(MIN_DATE==null)
            return false;
        return convertDateToString(calendar).compareTo(convertDateToString(MIN_DATE)) < 0;
    }

    private boolean isHigherThenMax(Calendar calendar) {
        if(MAX_DATE==null)
            return false;
        return convertDateToString(calendar).compareTo(convertDateToString(MAX_DATE)) > 0;
    }

    public int compareDates(Calendar calendar1,Calendar calendar2)
    {
        if(calendar1==null || calendar2==null)
            return -10000;
        return convertDateToString(calendar1).compareTo(convertDateToString(calendar2));
    }
    private String convertDateToString(Calendar calendar) {
        if (calendar != null)
            return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        return "";
    }


    public void setYear(int year)
    {
        calendar.set(Calendar.YEAR,year);
        setUpCalendar();
    }
}
