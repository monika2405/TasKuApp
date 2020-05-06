package com.staradmin.android.tasku.Model;

public class AlarmItem {
    String id_alarm;
    String time_alarm;
    String title_alarm;
    String freq_alarm;
    int onOff_alarm;

    public int getOnOff_alarm() {
        return onOff_alarm;
    }

    public void setOnOff_alarm(int onOff_alarm) {
        this.onOff_alarm = onOff_alarm;
    }



    public AlarmItem(String id_alarm, String time_alarm, String title_alarm,  String freq_alarm, int onOff_alarm) {
        this.id_alarm = id_alarm;
        this.time_alarm = time_alarm;
        this.title_alarm = title_alarm;
        this.freq_alarm = freq_alarm;
        this.onOff_alarm = onOff_alarm;
    }


    public String getId_alarm() {
        return id_alarm;
    }

    public void setId_alarm(String id_alarm) {
        this.id_alarm = id_alarm;
    }

    public String getTime_alarm() {
        return time_alarm;
    }

    public void setTime_alarm(String time_alarm) {
        this.time_alarm = time_alarm;
    }

    public String getTitle_alarm() {
        return title_alarm;
    }

    public void setTitle_alarm(String title_alarm) {
        this.title_alarm = title_alarm;
    }

    public String getFreq_alarm() {
        return freq_alarm;
    }

    public void setFreq_alarm(String freq_alarm) {
        this.freq_alarm = freq_alarm;
    }



}
