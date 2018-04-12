package pers.gene.ticketmanagement.domain;

import java.util.Date;

public class Custom {
    String cId;
    String name;
    String password;
    String cFrom;
    String cTo;
    Date cStartTime;
    Date cEndTime;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getcFrom() {
        return cFrom;
    }

    public void setcFrom(String cFrom) {
        this.cFrom = cFrom;
    }

    public String getcTo() {
        return cTo;
    }

    public void setcTo(String cTo) {
        this.cTo = cTo;
    }

    public Date getcStartTime() {
        return cStartTime;
    }

    public void setcStartTime(Date cStartTime) {
        this.cStartTime = cStartTime;
    }

    public Date getcEndTime() {
        return cEndTime;
    }

    public void setcEndTime(Date cEndTime) {
        this.cEndTime = cEndTime;
    }
}
