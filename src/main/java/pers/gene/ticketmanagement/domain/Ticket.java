package pers.gene.ticketmanagement.domain;

import java.util.Date;

public class Ticket {
    String tId;
    String tFrom;
    String tTo;
    Date tStatTTime;
    Date tEndTime;
    int amount;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettFrom() {
        return tFrom;
    }

    public void settFrom(String tFrom) {
        this.tFrom = tFrom;
    }

    public String gettTo() {
        return tTo;
    }

    public void settTo(String tTo) {
        this.tTo = tTo;
    }

    public Date gettStatTTime() {
        return tStatTTime;
    }

    public void settStatTTime(Date tStatTTime) {
        this.tStatTTime = tStatTTime;
    }

    public Date gettEndTime() {
        return tEndTime;
    }

    public void settEndTime(Date tEndTime) {
        this.tEndTime = tEndTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
