package prj.library.notification;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import prj.library.utils.CLIUtils;

/**
 * Class to schedule the email notification service
 */
public class NotificationScheduler {
    private final EmailNotificationService notificationService;

    /**
     * Constructor
     * @param notificationService the notification service to schedule
     */
    public NotificationScheduler(EmailNotificationService notificationService) {
        this.notificationService = notificationService;

        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            CLIUtils.serverError("NotificationScheduler already started");
        }
    }

    /**
     * Start the scheduler
     */
    public void startScheduler() {
        ScheduledService<Void> service = new ScheduledService<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        notificationService.checkAndSendNotifications(); //send email notifications
                        return null;
                    }
                };
            }
        };

        //Run the service every 24 hours
        service.setPeriod(Duration.hours(24));
        service.start();
    }
}