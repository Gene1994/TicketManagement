package com.genequ.ticketmanagement.mapper;

import com.genequ.ticketmanagement.pojo.Shipping;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShippingMapper {
    String SHIPPING_COLUMN_LIST = " id, user_id, receiver_name, receiver_phone, receiver_mobile, receiver_province, receiver_city," +
            " receiver_district, receiver_address, receiver_zip, create_time, update_time ";
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    @Select(" select" + SHIPPING_COLUMN_LIST+ "from hipping where id = #{id,jdbcType=INTEGER}")
    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    int updateByShipping(Shipping record);

    Shipping selectByShippingIdUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUserId(@Param("userId") Integer userId);

}