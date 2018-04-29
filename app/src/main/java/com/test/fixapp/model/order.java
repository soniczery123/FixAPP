package com.test.fixapp.model;

/**
 * Created by MSI-GL62 on 26/4/2561.
 */

public class order {
    public final String title;
    public final String detail;
    public final String date;
    public final int picture;

    public order(String title, String detail, String date, int picture) {
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.picture = picture;
    }
}
