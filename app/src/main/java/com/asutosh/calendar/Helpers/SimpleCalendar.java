package com.asutosh.calendar.Helpers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.asutosh.calendar.CalendarDatesAdapter;
import com.asutosh.calendar.Bean.CalendarDateInfo;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleCalendar {

    ArrayList<Calendar> list = new ArrayList<>();
    ArrayList<CalendarDateInfo>list_days = new ArrayList<CalendarDateInfo>();
//    CalendarDaysAdapter daysAdapter;

    SimpleDateFormat month_date;
    String month_name;
    int days_in_current_month;
    CalendarDatesAdapter adapter;
    int thisMonth;
    Calendar calendar;
    Context context;
    TextView tv_month;
    RecyclerView rv_dates;
    RecyclerView rv_days;
    ImageView left_arrow;
    ImageView right_arrow;


    public SimpleCalendar(Context context, RecyclerView rv_days, RecyclerView rv_dates, ImageView right_arrow, ImageView left_arrow, TextView tv_month) {

        this.context = context;
        this.rv_dates = rv_dates;
        this.rv_days = rv_days;
        this.left_arrow = left_arrow;
        this.right_arrow = right_arrow;
        this.tv_month = tv_month;

    }



    public void setUpCalendar(ArrayList<Calendar> list_of_enabled_dates) {
//
//
//        CalendarDateInfo model = new CalendarDateInfo();
//        model.setDate("SUN");
//        list_days.add(model);
//
//        CalendarDateInfo model1 = new CalendarDateInfo();
//        model1.setDate("MON");
//        list_days.add(model1);
//
//        CalendarDateInfo model2 = new CalendarDateInfo();
//        model2.setDate("TUE");
//        list_days.add(model2);
//
//        CalendarDateInfo model3 = new CalendarDateInfo();
//        model3.setDate("WED");
//        list_days.add(model3);
//
//        CalendarDateInfo model4 = new CalendarDateInfo();
//        model4.setDate("THU");
//        list_days.add(model4);
//
//        CalendarDateInfo model5 = new CalendarDateInfo();
//        model5.setDate("FRI");
//        list_days.add(model5);
//
//        CalendarDateInfo model6 = new CalendarDateInfo();
//        model6.setDate("SAT");
//        list_days.add(model6);


        calendar = Calendar.getInstance();

        month_date = new SimpleDateFormat("MMMM");
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        month_name = month_date.format(calendar.getTime());

        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String year = new SimpleDateFormat("YYYY").format(calendar.getTime());
        tv_month.setText(month_name + " " + year);


        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));


        for(int i = 0 ; i < calendar.getTime().getDay(); i++){
            list.add(null);
        }


        for(int i = 1; i <=days_in_current_month; i++){
            Calendar calendarNew=Calendar.getInstance();
            calendarNew.set(Calendar.DAY_OF_MONTH,i);
            calendarNew.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
            calendarNew.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
            calendarNew.set(Calendar.HOUR_OF_DAY, 0);
            calendarNew.set(Calendar.MINUTE, 0);
            calendarNew.set(Calendar.SECOND, 0);
            calendarNew.set(Calendar.MILLISECOND, 0);
            list.add(calendarNew);
        }

        rv_dates.setLayoutManager(new GridLayoutManager(context, 7));
//        adapter = new CalendarDatesAdapter(list, list_of_enabled_dates,context);
        rv_dates.setAdapter(adapter);


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNextMonth();
            }
        });


        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPreviousMonth();
            }
        });


        /*rv_dates.setOnTouchListener(object: OnSwipeTouchListener(context) {

            override fun onSwipeRight() {
                super.onSwipeRight()
                showPreviousMonth()

            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                showNextMonth()
            }
        })
*/

        rv_dates.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                showPreviousMonth();
            }
            public void onSwipeLeft() {
                showNextMonth();
            }
            public void onSwipeBottom() {

            }

        });

    }



    void showNextMonth(){

        thisMonth++;
        calendar =  getCurrentDatePlusMonth(thisMonth);
        month_name = month_date.format(calendar.getTime());
        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        month_name = month_date.format(calendar.getTime());

        String year = new SimpleDateFormat("YYYY").format(calendar.getTime());
        tv_month.setText(month_name + " " + year);

        list.clear();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        for(int i = 0 ; i < calendar.getTime().getDay(); i++){
            list.add(null);
        }


        for(int i = 1; i <=days_in_current_month; i++){
            Calendar calendarNew=Calendar.getInstance();
            calendarNew.set(Calendar.DAY_OF_MONTH,i);
            calendarNew.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
            calendarNew.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
            calendarNew.set(Calendar.HOUR_OF_DAY, 0);
            calendarNew.set(Calendar.MINUTE, 0);
            calendarNew.set(Calendar.SECOND, 0);
            calendarNew.set(Calendar.MILLISECOND, 0);
            list.add(calendarNew);
        }

        adapter.notifyDataSetChanged();


    }


    void showPreviousMonth(){
        thisMonth--;
        calendar =  getCurrentDatePlusMonth(thisMonth);
        month_name = month_date.format(calendar.getTime());
        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        days_in_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        month_name = month_date.format(calendar.getTime());

        String year = new SimpleDateFormat("YYYY").format(calendar.getTime());
        tv_month.setText(month_name + " " + year);

        list.clear();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        for(int i = 0 ; i < calendar.getTime().getDay(); i++){
            list.add(null);
        }

        for(int i = 1; i <=days_in_current_month; i++){
            Calendar calendarNew=Calendar.getInstance();
            calendarNew.set(Calendar.DAY_OF_MONTH,i);
            calendarNew.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
            calendarNew.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
            calendarNew.set(Calendar.HOUR_OF_DAY, 0);
            calendarNew.set(Calendar.MINUTE, 0);
            calendarNew.set(Calendar.SECOND, 0);
            calendarNew.set(Calendar.MILLISECOND, 0);
            list.add(calendarNew);
        }
        adapter.notifyDataSetChanged();
    }



    private Calendar getCurrentDatePlusMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, month);
        return calendar;
    }




}

















/////////////////////////////////

/*package com.asutosh.calendar.Helpers


class SimpleCalendar () {






*/


