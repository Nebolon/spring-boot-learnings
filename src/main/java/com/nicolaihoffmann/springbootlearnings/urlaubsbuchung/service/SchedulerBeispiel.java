package com.nicolaihoffmann.springbootlearnings.urlaubsbuchung.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerBeispiel {

    Logger logger = LoggerFactory.getLogger(SchedulerBeispiel.class);

    @Scheduled(fixedRate = 600000)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
        logger.info("Beispiel von LoggerFactory");
    }

}
