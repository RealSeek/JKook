package snw.jkook.scheduler;

/**
 * Represents a scheduler, you can schedule tasks to run. <p>
 */
public interface Scheduler {
    /**
     * Execute the provided runnable right now.
     *
     * @param runnable The runnable to execute
     */
    void runTask(Runnable runnable);

    /**
     * Schedule the runnable to be executed after the delay.
     *
     * @param runnable The runnable to execute
     * @param delay    The delay time
     * @return The task object
     */
    Task runTaskLater(Runnable runnable, long delay); // note: delay is millisecond.

    /**
     * Execute the runnable again and again.
     *
     * @param runnable The runnable to execute
     * @param period   The time before first run
     * @param delay    The time between two execution
     * @return The task object
     */
    Task runTaskTimer(Runnable runnable, long period, long delay); // note: both period and delay are in millisecond.

    /**
     * Return true if the task that represented by the ID is scheduled.
     *
     * @param taskId The ID to check
     */
    boolean isScheduled(int taskId);

    /**
     * Cancel the specified task. <p>
     * If the provided ID is not matching any task, this will fail silently.
     *
     * @param taskId The ID for finding the task
     */
    void cancelTask(int taskId);
}
