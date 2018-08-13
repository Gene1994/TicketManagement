package com.genequ.ticketmanagement.pojo;

import lombok.Data;
import java.util.Date;

@Data
public class Order {
    String id;
    Ticket ticket;
    User user;
    Date orderTime;

    int amount;//订票数量


}
