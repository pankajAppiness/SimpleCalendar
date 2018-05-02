package com.asutosh.calendar.Bean;

import java.util.Calendar;

public class CalendarDateInfo {

    private boolean enabled;
    private boolean checked;
    private Calendar date;

    public CalendarDateInfo() {
    }

    public CalendarDateInfo(boolean enabled, Calendar date) {
        this.enabled = enabled;
        this.date = date;
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
}
