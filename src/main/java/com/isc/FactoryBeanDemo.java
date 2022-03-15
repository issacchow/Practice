package com.isc;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * FactoryBean 的示例
 * FactoryBean顾名思义就是应用了工厂模式,
 * 而工厂模式是在那些经常要创建实例的场景下会有用
 * 而对于在单例模式的场景下，效果不明显。
 */
public class FactoryBeanDemo
{
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注意: 这里注入的是FactoryBean 而不是FactoryBean 产生出来的Bean

        context.registerBean("someClzFactoryBean",SomeClzFactoryBean.class);
        context.refresh();

        // 在使用同样的key 获取的bean
        // 如果不带 & 符号，就是获取该FactoryBean产生出来的Bean
        SomeClz bean = (SomeClz) context.getBean("someClzFactoryBean");
        System.out.println(bean.getAge());
        // 如果带 & 符号，就是获取该FactoryBean
        SomeClzFactoryBean someClzFactoryBean = (SomeClzFactoryBean) context.getBean("&someClzFactoryBean");


        SomeClz bean2 = someClzFactoryBean.createInstance();
        System.out.println(bean2.getAge());
    }


    public static class SomeClz{
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
    public static class SomeClzFactoryBean extends AbstractFactoryBean<SomeClz>{

        /**
         * 模板属性
         */
        private String namePrefix = "";
        private AtomicInteger age = new AtomicInteger(1);
        private int ageIncrease = 2;

        @Override
        public Class<?> getObjectType() {
            return SomeClz.class;
        }

        @Override
        protected SomeClz createInstance() throws Exception {

            SomeClz someClz = new SomeClz();
            // 复杂构造过程,略
            someClz.setAge(age.getAndAdd(ageIncrease));
            someClz.setName(this.namePrefix+"diao");

            return someClz;
        }

        public void setNamePrefix(String prefix){
            this.namePrefix = prefix;
        }



        public void setAgeIncrease(int ageIncrease) {
            this.ageIncrease = ageIncrease;
        }
    }
}
