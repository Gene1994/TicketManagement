package com.genequ.ticketmanagement.domain;

import lombok.Data;

@Data
public class Customer{
    String id;
    String userName;
    String password;
    String email;
    String cellphone;
    String avatarUrl;//头像
}
