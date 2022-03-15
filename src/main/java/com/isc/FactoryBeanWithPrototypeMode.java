package com.isc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FactoryBean 的示例2
 * 测试在非单例模式下，FactoryBean创建实例的情况:
 * 每次都会创建新的实例
 */
@SpringBootApplication(
        exclude = {
                DruidDataSourceAutoConfigure.class,
                DataSourceAutoConfiguration.class
        },
        scanBasePackageClasses = {FactoryBeanWithPrototypeMode.class}
)
public class FactoryBeanWithPrototypeMode {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(FactoryBeanWithPrototypeMode.class);
        ConfigurableApplicationContext ctx = application.run();


        SomeClz bean;
        bean = ctx.getBean(SomeClz.class);
        dump(bean);

        bean = ctx.getBean(SomeClz.class);
        dump(bean);

        bean = ctx.getBean(SomeClz.class);
        dump(bean);

        bean = ctx.getBean(SomeClz.class);
        dump(bean);

        bean = ctx.getBean(SomeClz.class);
        dump(bean);

        bean = ctx.getBean(SomeClz.class);
        dump(bean);

    }

    public static void dump(SomeClz bean) {
        System.out.println();
        System.out.printf("name:%s , age:%s", bean.getName(), bean.getAge());
        System.out.println();
    }

    @Bean
    public SomeClzFactoryBean someClzFactoryBean() {
        SomeClzFactoryBean someClzFactoryBean = new SomeClzFactoryBean();
        someClzFactoryBean.setAgeIncrease(10);
        someClzFactoryBean.setNamePrefix("factory bean-");
        someClzFactoryBean.setSingleton(false);
        return someClzFactoryBean;
    }


    public static class SomeClz {
        private int age;
        private String name;

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * FactoryBean 应用了工厂模式
     * 其作用:(设定如何装配Bean)
     * 1. 预先配置Bean生成出来的模板,通过设置FactoryBean本身各种属性
     * 2. 做一些复杂的初始化处理
     */
    public static class SomeClzFactoryBean extends AbstractFactoryBean<SomeClz> {

        /**
         * 模板属性
         */
        private String namePrefix = "";
        private AtomicInteger age = new AtomicInteger(1);
        private int ageIncrease = 2;
        private AtomicInteger instanceCount = new AtomicInteger(0);

        @Override
        public Class<?> getObjectType() {
            return SomeClz.class;
        }

        @Override
        protected SomeClz createInstance() throws Exception {

            SomeClz someClz = new SomeClz();
            // 复杂构造过程,略
            someClz.setAge(age.getAndAdd(ageIncrease));
            someClz.setName(this.namePrefix + "No." + instanceCount.incrementAndGet());

            return someClz;
        }

        public void setNamePrefix(String prefix) {
            this.namePrefix = prefix;
        }


        public void setAgeIncrease(int ageIncrease) {
            this.ageIncrease = ageIncrease;
        }
    }
}
