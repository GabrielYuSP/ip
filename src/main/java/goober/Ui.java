package goober;

import goober.task.Task;
import goober.task.TaskList;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showWelcome() {
        String banner = "____________________________________________________________\n" +
                "  ____              _               \n"
                + " / ___| ___   ___  | |__   ___  _ __  \n"
                + "| |  _ / _ \\ / _ \\ | '_ \\ / _ \\| '__| \n"
                + "| |_| | (_) | (_) || |_) |  __/| |    \n"
                + " \\____|\\___/ \\___/ |_.__/ \\___||_|    \n"
                + "____________________________________________________________\n";
        System.out.println(banner);
        System.out.println("Hello! I'm Goober.");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Displays the confirmation message after adding a task.
     *
     * @param task      Newly added task.
     * @param taskCount Number of tasks currently stored.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }

    /**
     * Displays the commands supported by Goober and their expected input formats.
     */
    public void showHelpMessage() {
        System.out.println(LINE);
        System.out.println("Here are the commands you can use:");
        System.out.println("  todo <description>");
        System.out.println("  deadline <description> /by <date or time>");
        System.out.println("  event <description> /from <date or time> /to <date or time>");
        System.out.println("  list");
        System.out.println("  mark <task number>");
        System.out.println("  unmark <task number>");
        System.out.println("  delete <task number>");
        System.out.println("  help");
        System.out.println("  bye");
        System.out.println(LINE);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param taskList Task list to display.
     */
    public void showTaskList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < taskList.getTaskCount(); i++) {
            System.out.println((i + 1) + "." + taskList.getTask(i));
        }

        System.out.println("Now you have " + taskList.getTaskCount() + " tasks in the list!");
        System.out.println(LINE);
    }

    public void showTaskMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Good job! I've marked this task as done:");
        System.out.println(task);
        System.out.println(LINE);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK bro, I've marked this task as not done yet:");
        System.out.println(task);
        System.out.println(LINE);
    }

    public void showDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showCommandSuggestion(String suggestion) {
        System.out.println("Did you mean \"" + suggestion + "\"?");
    }
}
