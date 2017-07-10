package com.changhong.system.scheduler;

import com.changhong.system.service.BoxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boxCommandPlanTimeCheckScheduler")
public class BoxCommandPlanTimeCheckScheduler {

    @Autowired
    private BoxInfoService boxInfoService;

    public void update() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                boxInfoService.updateBoxCommandPlanTime();
            }
        };
        thread.start();
    }
}
