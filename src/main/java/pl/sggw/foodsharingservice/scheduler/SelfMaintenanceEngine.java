package pl.sggw.foodsharingservice.scheduler;

public interface SelfMaintenanceEngine {
    void removeExpiredNotices();
    void removeDeletedUsers();
}
