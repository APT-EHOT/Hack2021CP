package com.shureck.medmobile.Models;

import java.util.Date;

public class DiaryModel {
    Date id;
    int top;
    int bottom;
    int pulse;
    String activityType;

    public Date getId() {
        return id;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getPulse() {
        return pulse;
    }

    public String getActivityType() {
        return activityType;
    }
}
