package com.example.abner.listviewwithsoftpanel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Abner on 15/6/27.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class SimpleModel implements Serializable {


    private static final long serialVersionUID = -4747713350630189867L;


    private String text;


    private List<String> comments;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
