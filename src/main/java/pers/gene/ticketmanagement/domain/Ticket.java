package pers.gene.ticketmanagement.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Ticket {
    String id;
    String trainNumber;
    String checkin;
    String checkout;
    Date startTime;
    Date endTime;
    String seatType;
    String seatNumber;
    double price;
    int amount;
    boolean isOrdered = false;//数据库中 1代表TRUE,0代表FALSE
    String customerId;
}
