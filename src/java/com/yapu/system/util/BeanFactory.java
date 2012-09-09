package com.yapu.system.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: ssg
 * Date: 12-9-5
 * Time: 下午11:17
 * To change this template use File | Settings | File Templates.
 */
public class BeanFactory {
    static private String []paths = {"applicationContext.xml"};
    static private ApplicationContext ctx = null;
    private BeanFactory(){}
    public static void loadAppContext(){
        ctx=new ClassPathXmlApplicationContext(paths);
    }
    static public Object getBean(String beanName){
        if(ctx==null) loadAppContext();
        return ctx.getBean(beanName);
    }
}
