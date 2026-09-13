package goober.task;

/**
 * Represents a task that can be marked as done or not done.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns the display marker for this task's completion state.
     *
     * @return {@code X} when the task is done, or a blank space otherwise.
     */
    public String getStatus() {
        return isDone ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description;
    }
}
