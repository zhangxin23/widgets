package net.coderland.server.task.launcher;

import net.coderland.server.common.system.Processes;
import net.coderland.server.task.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: zhangxin
 * Date:   15-12-24
 */
public class StockTask {
    private static Logger logger = LoggerFactory.getLogger(StockTask.class);

    public static void main(String ... args) {
        try {
            if (Processes.check(StockTask.class.getName())) {
                logger.error("task is running....");
                System.exit(1);
            }
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext-*.xml");
            StockService service = applicationContext.getBean("stockService", StockService.class);
            service.collect();
        } catch (Exception e) {
            logger.error("task occurs error: ", e);
        }

        System.exit(0);
    }
}
