package com.isc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isc.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<OrderDto> {


}
