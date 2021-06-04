package com.isc;

import com.isc.dto.UserDto;
import com.isc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication(scanBasePackageClasses = Boot.class)
@RestController("/")
public class Boot {

    public static void main(String[] args) {

        SpringApplication.run(Boot.class);
    }


    @Autowired
    private UserMapper userMapper;

    @RequestMapping("create")
    public UserDto createUser(@RequestBody UserDto user) {

        user.setCreateAt(new Date());
        userMapper.insert(user);
        return user;

    }

    @RequestMapping("update")
    public UserDto updateUser(@RequestBody UserDto user) {
        userMapper.updateById(user);
        return user;
    }

    @RequestMapping("get/{userId}")
    public UserDto getUser(@PathVariable("userId") Integer userId){
        return userMapper.selectById(userId);
    }

}
