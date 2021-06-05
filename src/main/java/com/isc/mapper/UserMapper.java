package com.isc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isc.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDto> {

    IPage<UserDto> selectPageVo(Page<?> page);

    /**
     * 分页查询
     * @param page 从1开始
     * @return
     */
    List<UserDto> selectPage(@Param("page") int page,@Param("size") int size);
}
