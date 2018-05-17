package pers.gene.ticketmanagement.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Order {
    Ticket ticket;
    Customer customer;
    Date orderTime;
    int amount;//订票数量
}
