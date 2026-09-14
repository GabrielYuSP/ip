package goober.task;

/**
 * Represents a task that takes place during a specified time period.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event task.
     *
     * @param description Description of the event.
     * @param from        Start date or time of the event.
     * @param to          End date or time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    /**
     * Returns the event task's display format.
     *
     * @return Task type, completion status, description, and event period.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

}
