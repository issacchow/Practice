package com.isc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 本例子主要用于循环依赖
 *
 */
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}
)
public class Boot {



    public static void main(String[] args) {

        SpringApplication.run(Boot.class);

    }





}
