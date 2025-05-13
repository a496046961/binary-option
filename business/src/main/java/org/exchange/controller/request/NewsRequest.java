package org.exchange.controller.request;

import lombok.Data;

import java.io.Serializable;


public class NewsRequest implements Serializable {

    private String title;
    private String content;
    private String axisPoint;
    private boolean expanded;

    private long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAxisPoint() {
        return axisPoint;
    }

    public void setAxisPoint(String axisPoint) {
        this.axisPoint = axisPoint;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NewsRequest() {

    }

    public NewsRequest(String title, String content, String axisPoint, long id) {
        this.title = title;
        this.content = content;
        this.axisPoint = axisPoint;
        this.expanded = false;
        this.id = id;
    }

    @Override
    public String toString() {
        return "NewsRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", axisPoint='" + axisPoint + '\'' +
                ", expanded=" + expanded +
                ", id=" + id +
                '}';
    }
}
