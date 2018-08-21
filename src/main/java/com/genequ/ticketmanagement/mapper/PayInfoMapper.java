package com.genequ.ticketmanagement.mapper;


import com.genequ.ticketmanagement.pojo.PayInfo;
import org.apache.ibatis.annotations.Insert;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    @Insert("insert into pay_info (id, user_id, order_no, pay_platform, platform_number, platform_status, create_time, update_time)" +
            "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{orderNo,jdbcType=BIGINT}, #{payPlatform,jdbcType=INTEGER}, #{platformNumber,jdbcType=VARCHAR}, #{platformStatus,jdbcType=VARCHAR}, now(), now())")
    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}