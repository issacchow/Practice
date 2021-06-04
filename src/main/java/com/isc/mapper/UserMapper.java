package com.isc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isc.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDto> {
}
