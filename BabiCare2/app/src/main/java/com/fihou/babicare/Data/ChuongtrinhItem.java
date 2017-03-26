package com.fihou.babicare.Data;

/**
 * Created by TAM on 22/03/2017.
 */

public class ChuongtrinhItem {
    private String time;
    private String content;

    public ChuongtrinhItem() {
    }

    public ChuongtrinhItem(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
