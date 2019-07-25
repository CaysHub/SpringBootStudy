package cays.spring.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调度程序
 * 1. cron调度程序，在固定时间段进行任务
 * 2. fixedRate调度程序，以固定时间间隔执行程序
 * 3. fixedRate，initialDelay调度程序，上一个任务完成后x秒，以固定时间间隔执行程序
 * @author Chai yansheng
 * @create 2019-07-25 13:44
 **/
//@Component
public class TimeScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeScheduler.class);
    @Scheduled(cron = "0 * 13 * * ?")
    public void cronJob() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sdf.format(new Date());
        LOGGER.info(time);
    }

    @Scheduled(fixedRate = 1000)
    public void fixedRateSch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sdf.format(new Date());
        LOGGER.info(time);
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 3000)
    public void fixedDelaySch() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sdf.format(new Date());
        LOGGER.info(time);
    }
}
