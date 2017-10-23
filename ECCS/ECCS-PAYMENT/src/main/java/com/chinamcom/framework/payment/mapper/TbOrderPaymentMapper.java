package com.chinamcom.framework.payment.mapper;

import com.chinamcom.framework.payment.model.TbOrderPayment;
import com.chinamcom.framework.payment.model.TbOrderPaymentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOrderPaymentMapper {
    int countByExample(TbOrderPaymentExample example);

    int deleteByExample(TbOrderPaymentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbOrderPayment record);

    int insertSelective(TbOrderPayment record);

    List<TbOrderPayment> selectByExample(TbOrderPaymentExample example);

    TbOrderPayment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbOrderPayment record, @Param("example") TbOrderPaymentExample example);

    int updateByExample(@Param("record") TbOrderPayment record, @Param("example") TbOrderPaymentExample example);

    int updateByPrimaryKeySelective(TbOrderPayment record);

    int updateByPrimaryKey(TbOrderPayment record);
}