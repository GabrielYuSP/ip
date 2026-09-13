import java.util.Scanner;

/**
 * Runs the Goober task management application.
 */
public class Goober {
    private static final String LINE = "____________________________________________________________";

    /**
     * Represents an invalid command or command argument entered by the user.
     */
    private static class GooberException extends Exception {
        private static final long serialVersionUID = 1L;

        GooberException(String message) {
            super(message);
        }
    }

    private static void printWelcomeMessage() {
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

    /**
     * Displays the confirmation message after adding a task.
     *
     * @param task      Newly added task.
     * @param taskCount Number of tasks currently stored.
     */
    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println("OOPS!!! " + message);
        System.out.println(LINE);
    }

    /**
     * Returns a valid command that is one edit away from the input command.
     *
     * @param input User input containing a command.
     * @return Suggested command, or null if no close command is found.
     */
    private static String getSuggestedCommand(String input) {
        String[] commands = {"todo", "deadline", "event", "list", "mark", "unmark", "help", "bye"};
        String command = input.split(" ")[0];

        for (String validCommand : commands) {
            if (getLevenshteinDistance(command, validCommand) == 1) {
                return validCommand;
            }
        }

        return null;
    }

    /**
     * Calculates the minimum number of single-character edits needed to transform one string into another.
     *
     * @param first  First string.
     * @param second Second string.
     * @return Levenshtein distance between the two strings.
     */
    private static int getLevenshteinDistance(String first, String second) {
        int[][] distances = new int[first.length() + 1][second.length() + 1];

        for (int i = 0; i <= first.length(); i++) {
            distances[i][0] = i;
        }

        for (int j = 0; j <= second.length(); j++) {
            distances[0][j] = j;
        }

        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int substitutionCost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                distances[i][j] = Math.min(
                        Math.min(distances[i - 1][j] + 1, distances[i][j - 1] + 1),
                        distances[i - 1][j - 1] + substitutionCost);
            }
        }

        return distances[first.length()][second.length()];
    }

    /**
     * Displays the commands supported by Goober and their expected input formats.
     */
    private static void printHelpMessage() {
        System.out.println(LINE);
        System.out.println("Here are the commands you can use:");
        System.out.println("  todo <description>");
        System.out.println("  deadline <description> /by <date or time>");
        System.out.println("  event <description> /from <date or time> /to <date or time>");
        System.out.println("  list");
        System.out.println("  mark <task number>");
        System.out.println("  unmark <task number>");
        System.out.println("  help");
        System.out.println("  bye");
        System.out.println(LINE);
    }

    /**
     * Parses and adds a todo task.
     *
     * @param input    Full todo command.
     * @param taskList List to which the task is added.
     */
    private static void addTodo(String input, TaskList taskList) throws GooberException {
        String description = input.length() <= 4 ? "" : input.substring(5).trim();
        if (description.isEmpty()) {
            throw new GooberException("The description of a todo cannot be empty!");
        }
        Task task = new Todo(description);
        taskList.addTask(task);
        printTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds a deadline task.
     *
     * @param input    Full deadline command.
     * @param taskList List to which the task is added.
     */
    private static void addDeadline(String input, TaskList taskList) throws GooberException {
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
        printTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds an event task.
     *
     * @param input    Full event command.
     * @param taskList List to which the task is added.
     */
    private static void addEvent(String input, TaskList taskList) throws GooberException {
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
        printTaskAdded(task, taskList.getTaskCount());
    }

    private static void markTask(String input, TaskList taskList) {
        int index;
        try {
            index = Integer.parseInt(input.substring(5)) - 1;
        } catch (NumberFormatException e) {
            printError("Enter a valid task number!");
            return;
        }

        if (index < 0 || index >= taskList.getTaskCount()) {
            printError("Task not found!");
        } else if (taskList.getTask(index).isDone()) {
            printError("This task is already marked as done!");
        } else {
            taskList.getTask(index).markAsDone();

            System.out.println(LINE);
            System.out.println("Good job! I've marked this task as done:");
            System.out.println(taskList.getTask(index).toString());
            System.out.println(LINE);
        }
    }

    private static void unmarkTask(String input, TaskList taskList) {
        int index;
        try {
            index = Integer.parseInt(input.substring(7)) - 1;
        } catch (NumberFormatException e) {
            printError("Enter a valid task number!");
            return;
        }

        if (index < 0 || index >= taskList.getTaskCount()) {
            printError("Task not found!");
        } else if (!taskList.getTask(index).isDone()) {
            printError("This task is already marked as not done!");
        } else {
            taskList.getTask(index).markAsUndone();

            System.out.println(LINE);
            System.out.println("OK bro, I've marked this task as not done yet:");
            System.out.println(taskList.getTask(index).toString());
            System.out.println(LINE);
        }
    }

    /**
     * Starts the interactive task management application.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            String command = input.split(" ", 2)[0].toLowerCase();

            switch (command) {
            case "bye":
                if (input.equalsIgnoreCase("bye")) {
                    System.out.println(LINE);
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(LINE);
                    scanner.close();
                    return;
                }
                printError("The bye command does not accept additional input!");
                break;
            case "list":
                if (input.equalsIgnoreCase("list")) {
                    System.out.println(LINE);
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskList.getTaskCount(); i++) {
                        System.out.println((i + 1) + "." + taskList.getTask(i));
                    }
                    System.out.println("Now you have " + taskList.getTaskCount() + " tasks in the list!");
                    System.out.println(LINE);
                } else {
                    printError("The list command does not accept additional input!");
                }
                break;
            case "help":
                printHelpMessage();
                break;
            case "mark":
                if (input.length() > 4) {
                    markTask(input, taskList);
                } else {
                    printError("Enter a valid task number!");
                }
                break;
            case "unmark":
                if (input.length() > 6) {
                    unmarkTask(input, taskList);
                } else {
                    printError("Enter a valid task number!");
                }
                break;
            case "todo":
                try {
                    addTodo(input, taskList);
                } catch (GooberException e) {
                    printError(e.getMessage());
                }
                break;
            case "deadline":
                try {
                    addDeadline(input, taskList);
                } catch (GooberException e) {
                    printError(e.getMessage());
                }
                break;
            case "event":
                try {
                    addEvent(input, taskList);
                } catch (GooberException e) {
                    printError(e.getMessage());
                }
                break;
            default:
                printError("Sorry bro, I don't know what that means :-(");
                String suggestion = getSuggestedCommand(input);
                if (suggestion != null) {
                    System.out.println("Did you mean \"" + suggestion + "\"?");
                }
                break;
            }
        }
        scanner.close();
    }
}
