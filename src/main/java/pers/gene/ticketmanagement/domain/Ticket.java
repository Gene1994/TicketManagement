package pers.gene.ticketmanagement.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Ticket {
    String trainNumber;
    String checkin;
    String checkout;
    Date startTime;
    Date endTime;
    String seatType;
    String seatNumber;
    double price;
    int amount;
    boolean isOrdered;
    String customerId;
}
