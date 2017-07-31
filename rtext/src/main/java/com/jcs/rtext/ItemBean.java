package com.jcs.rtext;

import java.io.Serializable;

/**
 * Created by ice on 2016/5/21.
 */
public class ItemBean implements Serializable {
    int drawable;
    String title;
    String detail;


    public ItemBean(int drawable, String title, String detail) {
        this.drawable = drawable;
        this.title = title;
        this.detail = detail;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
