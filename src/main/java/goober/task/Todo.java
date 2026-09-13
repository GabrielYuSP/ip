package goober.task;

/**
 * Represents a task without a deadline or event period.
 */
public class Todo extends Task {

    /**
     * Creates a todo task.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the todo task's display format.
     *
     * @return Task type, completion status, and description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
