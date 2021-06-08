package com.isc.controller;

import com.isc.mapper.OrderMapper;
import com.isc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注射多一次Mapper，测试一下FactoryBean
 * 由于每个XXXMapper对应的MapperFactoryBean中，重载了isSingletion方法返回都是true
 * 所以只会从对应的MapperFactoryBean中创建一次
 */
@RestController
public class FactoryBeanTestController {


    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;

}
