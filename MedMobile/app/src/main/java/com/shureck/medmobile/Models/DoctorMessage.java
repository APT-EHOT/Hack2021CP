package com.shureck.medmobile.Models;

import java.util.Date;

public class DoctorMessage {
    String text;
    String doctorName;
    String phone;
    Date date;

    public String getText() {
        return text;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPhone() {
        return phone;
    }

    public Date getDate() {
        return date;
    }
}
