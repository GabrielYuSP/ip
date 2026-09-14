package goober;

import goober.task.Deadline;
import goober.task.Event;
import goober.task.Task;
import goober.task.TaskList;
import goober.task.Todo;

public class CommandHandler {
    private final TaskList taskList;
    private final Ui ui;
    private final Parser parser;
    private final Storage storage;

    public CommandHandler(TaskList taskList, Ui ui, Parser parser, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.parser = parser;
        this.storage = storage;
    }

    /**
     * Parses and adds a todo task.
     *
     * @param input    Full todo command.
     */
    private void addTodo(String input) throws GooberException {
        String description = input.length() <= 4 ? "" : input.substring(5).trim();
        if (description.isEmpty()) {
            throw new GooberException("The description of a todo cannot be empty!");
        }
        Task task = new Todo(description);
        taskList.addTask(task);
        storage.save(taskList);
        ui.showTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds a deadline task.
     *
     * @param input    Full deadline command.
     */
    private void addDeadline(String input) throws GooberException {
        int byIndex = input.indexOf("/by ");
        if (byIndex < 0) {
            throw new GooberException("A deadline must have a description and a /by value!");
        }
        String description = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 4).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new GooberException("A deadline must have a description and a /by value!");
        }
        Task task = new Deadline(description, by);
        taskList.addTask(task);
        storage.save(taskList);
        ui.showTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds an event task.
     *
     * @param input    Full event command.
     */
    private void addEvent(String input) throws GooberException {
        int fromIndex = input.indexOf("/from ");
        int toIndex = input.indexOf("/to ");
        if (fromIndex < 0 || toIndex < 0 || fromIndex >= toIndex) {
            throw new GooberException("An event must have a description, a /from value, and a /to value!");
        }
        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 4).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new GooberException("An event must have a description, a /from value, and a /to value!");
        }
        Task task = new Event(description, from, to);
        taskList.addTask(task);
        storage.save(taskList);
        ui.showTaskAdded(task, taskList.getTaskCount());
    }

    private void markTask(String input) {
        int index;
        try {
            index = Integer.parseInt(input.substring(5)) - 1;
        } catch (NumberFormatException e) {
            ui.showError("Enter a valid task number!");
            return;
        }

        if (index < 0 || index >= taskList.getTaskCount()) {
            ui.showError("Task not found!");
        } else if (taskList.getTask(index).isDone()) {
            ui.showError("This task is already marked as done!");
        } else {
            Task task = taskList.getTask(index);
            task.markAsDone();
            storage.save(taskList);
            ui.showTaskMarked(task);
        }
    }

    private void unmarkTask(String input) {
        int index;
        try {
            index = Integer.parseInt(input.substring(7)) - 1;
        } catch (NumberFormatException e) {
            ui.showError("Enter a valid task number!");
            return;
        }

        if (index < 0 || index >= taskList.getTaskCount()) {
            ui.showError("Task not found!");
        } else if (!taskList.getTask(index).isDone()) {
            ui.showError("This task is already marked as not done!");
        } else {
            Task task = taskList.getTask(index);
            task.markAsUndone();
            storage.save(taskList);
            ui.showTaskUnmarked(task);
        }
    }

    public boolean handleCommand(String input) {
        String command = parser.getCommand(input);
        switch (command) {
            case "bye":
                if (input.equalsIgnoreCase("bye")) {
                    ui.showBye();
                    return true;
                }
                ui.showError("The bye command does not accept additional input!");
                return false;
            case "list":
                if (input.equalsIgnoreCase("list")) {
                    ui.showTaskList(taskList);
                } else {
                    ui.showError("The list command does not accept additional input!");
                }
                return false;
            case "help":
                ui.showHelpMessage();
                return false;
            case "mark":
                if (input.length() > 4) {
                    markTask(input);
                } else {
                    ui.showError("Enter a valid task number!");
                }
                return false;
            case "unmark":
                if (input.length() > 6) {
                    unmarkTask(input);
                } else {
                    ui.showError("Enter a valid task number!");
                }
                return false;
            case "todo":
                try {
                    addTodo(input);
                } catch (GooberException e) {
                    ui.showError(e.getMessage());
                }
                return false;
            case "deadline":
                try {
                    addDeadline(input);
                } catch (GooberException e) {
                    ui.showError(e.getMessage());
                }
                return false;
            case "event":
                try {
                    addEvent(input);
                } catch (GooberException e) {
                    ui.showError(e.getMessage());
                }
                return false;
            default:
                ui.showError("Sorry bro, I don't know what that means :-(");
                String suggestion = parser.getSuggestedCommand(input);
                if (suggestion != null) {
                    ui.showCommandSuggestion(suggestion);
                }
                return false;
        }
    }
}
