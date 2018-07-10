package com.asutosh.calendar.calendarPerMonthItem;

import java.util.Calendar;

public class CalendarDateInfo {

    private boolean enabled;
    private boolean checked;
    private Calendar date;
    private boolean isHidden;
    private boolean isTitle;
    private boolean isMultiSelected;

    public CalendarDateInfo(boolean enabled, Calendar date) {
        this.enabled = enabled;
        this.date = date;
    }

    public CalendarDateInfo(boolean enabled, Calendar date, boolean isTitle) {
        this.enabled = enabled;
        this.date = date;
        this.isTitle = isTitle;
    }


    public CalendarDateInfo(boolean enabled, boolean checked, Calendar date, boolean isHidden, boolean isTitle) {
        this.enabled = enabled;
        this.checked = checked;
        this.date = date;
        this.isHidden = isHidden;
        this.isTitle = isTitle;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isMultiSelected() {
        return isMultiSelected;
    }

    public void setMultiSelected(boolean multiSelected) {
        isMultiSelected = multiSelected;
    }
}
