package pl.sggw.foodsharingservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSchedulerImpl implements JobScheduler{
    private final SelfMaintenanceEngine selfMaintenanceEngine;

    @Scheduled(cron = "${fss.scheduler.cron.expression}")
    @Override
    public void cleanNotices() {
        log.info("About to start removing expired Notices process...");
        selfMaintenanceEngine.removeExpiredNotices();
        log.info("Removing expired Notices process finished.");
    }

    @Override
    @Scheduled(cron = "${fss.scheduler.cron.expression}")
    public void cleanUsers() {
        log.info("About to start removing requested Users process...");
        selfMaintenanceEngine.removeDeletedUsers();
        log.info("Removing requested Users process finished.");
    }
}
