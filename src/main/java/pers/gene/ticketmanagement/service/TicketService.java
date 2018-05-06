package pers.gene.ticketmanagement.service;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gene.ticketmanagement.domain.PageBean;
import pers.gene.ticketmanagement.domain.Ticket;
import pers.gene.ticketmanagement.repository.TicketMapper;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {
//    void add(Ticket ticket);
//    void sell();
//    void roll();
    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    public PageBean<Ticket> pageBean;

    public List<Ticket> search(String checkin, String checkout, String startTime, int currentPage, int pageSize){
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        List<Ticket> ticketList = ticketMapper.findByCheckinCheckout(checkin, checkout, startTime);
        int countNums = ticketMapper.countByChecckinCheckout(checkin, checkout, startTime);//总记录数
        pageBean = new PageBean<>(currentPage, pageSize, countNums);
        pageBean.setItems(ticketList);
        return pageBean.getItems();
    }
}
