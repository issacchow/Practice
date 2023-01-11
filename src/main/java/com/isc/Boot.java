package com.isc;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackageClasses = Boot.class)
@RestController("/")
public class Boot {


    @Bean
    public Scheduler quartzScheduler() throws SchedulerException {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        return factory.getScheduler();
    }

    public static void main(String[] args) {
        SpringApplication.run(Boot.class);
    }



    @RequestMapping("")
    public String helloWorld() {
        return "hello world";
    }




}
