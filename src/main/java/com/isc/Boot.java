package com.isc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isc.dto.UserDto;
import com.isc.mapper.UserMapper;
import com.isc.request.UserSearchRequest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@SpringBootApplication(scanBasePackageClasses = Boot.class)
@RestController("/")
public class Boot {


    // 旧版
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Boot.class);
    }



    /*** 业务代码在下面 ***/

























    @Autowired
    private UserMapper userMapper;

    @RequestMapping("create")
    public UserDto createUser(@RequestBody UserDto user) {

        user.setCreateAt(new Date());
        userMapper.insert(user);
        return user;

    }

    @RequestMapping("update")
    public int updateUser(@RequestBody UserDto user) {
        return userMapper.updateById(user);
    }

    @RequestMapping("get/{userId}")
    public UserDto getUser(@PathVariable("userId") Integer userId){
        return userMapper.selectById(userId);
    }

    @RequestMapping("search")
    public List<UserDto> search(@RequestBody UserSearchRequest request){

        Page<UserDto> pager = new Page<>(request.getPage(),10);
        System.out.println("start :" + new Date());
        IPage<UserDto> result = userMapper.selectPageVo(pager);
        System.out.println("end :"+ new Date());
        return result.getRecords();
    }


    @RequestMapping("search2")
    public List<UserDto> search2(@RequestBody UserSearchRequest request){

        return userMapper.selectPage(request.getPage(),10);
    }


}
