package com.staradmin.android.tasku.Model;

public class AlarmItem {
    String id_alarm;
    String time_alarm;
    String title_alarm;
    String freq_alarm;
    String desc_alarm;

    public AlarmItem(String id_alarm, String time_alarm, String title_alarm,  String desc_alarm, String freq_alarm) {
        this.id_alarm = id_alarm;
        this.time_alarm = time_alarm;
        this.title_alarm = title_alarm;
        this.freq_alarm = freq_alarm;
        this.desc_alarm = desc_alarm;
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

    public String getDesc_alarm() {
        return desc_alarm;
    }

    public void setDesc_alarm(String desc_alarm) {
        this.desc_alarm = desc_alarm;
    }


}
