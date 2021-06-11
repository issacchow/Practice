package com.isc;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 本例子主要用于测试AnnotationApplicationContext的各种功能
 * 包括注射注解解释器
 */
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}
)
public class Boot {



    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Boot.class);

//        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
//        BeanDefinition beanDef = new GenericBeanDefinition();
//        beanDef.setBeanClassName("java.lang.Integer");
//        beanDef.setFactoryBeanName("MyIntegerFactoryBean");
//        beanDef.setScope(BeanDefinition.SCOPE_PROTOTYPE);
////        appContext.scan("com.isc");
////        appContext.registerBeanDefinition("myBeanDef",beanDef);
//        appContext.refresh();
//        appContext
//
////        appContext.getBean("myBeanDef");


    }

    @Bean
    public Integer myInteger(){
        return new Integer(100);
    }

    @Component
    public class MyBeanInitialization implements InitializingBean , BeanFactoryAware {

        BeanFactory beanFactory;
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("My bean initializing::: after set properties");
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            System.out.println("aware");
            this.beanFactory = beanFactory;
        }
    }

    @Component
    public class MyBeanPostProcessor implements BeanPostProcessor{
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if("myInteger".equalsIgnoreCase(beanName)){
                System.out.println("myInteger before init");
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

            if("myInteger".equalsIgnoreCase(beanName)){
                System.out.println("myInteger after init");
            }
            return bean;
        }
    }


    @Component
    public class MyIntegerFactoryBean implements FactoryBean<Integer> {

        @Override
        public Integer getObject() throws Exception {
            return new Integer("1");
        }

        @Override
        public Class<?> getObjectType() {
            return Integer.class;
        }
    }


}
