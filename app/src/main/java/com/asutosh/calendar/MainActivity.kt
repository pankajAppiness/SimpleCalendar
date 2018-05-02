package com.asutosh.calendar

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.asutosh.calendar.Bean.CalendarDateInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class MainActivity : AppCompatActivity() {

    var list = ArrayList<CalendarDateInfo>()
    var list_days = ArrayList<CalendarDateInfo>()
//    lateinit var daysAdapter: CalendarDaysAdapter

    lateinit var month_date : SimpleDateFormat
    var month_name : String = ""
    var days_in_current_month : Int = 0
    lateinit var adapter: CalendarDatesAdapter
    var thisMonth : Int = 0
    lateinit var calendar : Calendar
    var list_of_enabled_dates : ArrayList<Calendar> = ArrayList()


    internal var listSelectedDates: HashSet<Calendar> = HashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        for (i in 1 until 10) {
//            var calendar = Calendar.getInstance();
//            calendar.set(2018, ((Math.random() * 100) % 12).toInt(), ((Math.random() * 100) % 28).toInt())
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//            list_of_enabled_dates.add(calendar);
//        }
//        var mSimpleCalendar = SimpleCalendar(this, rv_days, rv_dates, imv_right_arrow, imv_left_arrow, tv_month)
//        mSimpleCalendar.setUpCalendar(list_of_enabled_dates)

        for (i in 1..10) {
            val calendar = Calendar.getInstance()
            calendar.set(2018, (Math.random() * 100 % 12).toInt(), (Math.random() * 100 % 28).toInt())
            listSelectedDates.add(calendar)
        }

        custCalendarView.enableAllDays()
        //custCalendarView.setEnabledDates(listSelectedDates)
        //custCalendarView.setMinDate(Calendar.getInstance())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH,8)
        calendar.set(Calendar.DAY_OF_MONTH,12)
        custCalendarView.setMaxDate(calendar)
        custCalendarView.setUpCalendar()
        var context=this
        custCalendarView.setOnDateSelectedListener(object : CustomCalendarView.OnDateSelected {
            override fun onSelected(calendar: Calendar) {
                Toast.makeText(context,"selected---"+calendar.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_LONG).show();
            }
        })
    }

}
