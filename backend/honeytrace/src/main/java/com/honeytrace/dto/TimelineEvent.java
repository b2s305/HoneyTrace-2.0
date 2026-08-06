package com.honeytrace.dto;

public class TimelineEvent {

    private String time;
    private String message;

    public TimelineEvent() {
    }

    public TimelineEvent(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}