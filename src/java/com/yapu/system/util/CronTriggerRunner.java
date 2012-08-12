package com.yapu.system.util;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
/**
 */
public class CronTriggerRunner {
    public void task() throws SchedulerException
    {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 当前时间
        //long ctime = System.currentTimeMillis();
        JobDetail jobDetail = new JobDetail("dayQuartzJob", "dayQuartzJobGroup", DayQuartzJob.class);
        CronTrigger cronTrigger = new CronTrigger("dayTrigger", "dayTriggerGroup");
        try {
            // 设置触发器频率
            CronExpression cexp = new CronExpression("0 0 0 * * ?");    //每天0秒、0分、0时启动任务
            cronTrigger.setCronExpression(cexp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置定时任务与触发器
        scheduler.scheduleJob(jobDetail, cronTrigger);
        //启动定时任务
        scheduler.start();
    }
  }
