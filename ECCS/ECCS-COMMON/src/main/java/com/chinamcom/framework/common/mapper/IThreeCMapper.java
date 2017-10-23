package com.chinamcom.framework.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IThreeCMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
