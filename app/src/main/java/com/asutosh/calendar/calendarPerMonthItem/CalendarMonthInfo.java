package com.asutosh.calendar.calendarPerMonthItem;

import java.util.List;

public class CalendarMonthInfo {

    List<CalendarDateInfo> listCalendarDateInfo;
    boolean isChecked;

    public List<CalendarDateInfo> getListCalendarDateInfo() {
        return listCalendarDateInfo;
    }

    public void setListCalendarDateInfo(List<CalendarDateInfo> listCalendarDateInfo) {
        this.listCalendarDateInfo = listCalendarDateInfo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
