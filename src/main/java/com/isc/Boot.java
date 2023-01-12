package com.isc;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication(scanBasePackageClasses = Boot.class)
@RestController("/")
public class Boot {


    @Bean
    public Scheduler quartzScheduler() throws SchedulerException {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        return scheduler;
    }

    public static void main(String[] args) {
        SpringApplication.run(Boot.class);


    }


    @Autowired
    Scheduler scheduler;

    AtomicInteger jobNumber = new AtomicInteger(0);

    @RequestMapping("addjob")
    public String addjob(@RequestParam("delay") int delaySeconds) {


//        JobDetail jobDetail = JobBuilder.newJob(QuartzSchedulingServiceJob.class)
//                .withIdentity(, null)
//                .build();
//        JobDetailImpl job = new JobDetailImpl();
//        scheduler.scheduleJob(n
        return "hello world";
    }






}
