package cn.tedu.tmall.basic.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DistrictSchedule {

    @Scheduled(fixedRate = 1000*60*60*24)
    public void xxx() {
        log.debug("DistrictSchedule.xxx()");
    }

}
