import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Runs the Goober task management application.
 */
public class Goober {
    private static final String LINE = "____________________________________________________________";

    private static void printWelcomeMessage(){
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
     * @param task Newly added task.
     * @param taskCount Number of tasks currently stored.
     */
    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Parses and adds a todo task.
     *
     * @param input Full todo command.
     * @param taskList List to which the task is added.
     */
    private static void addTodo(String input, TaskList taskList) {
        String description = input.substring(5).trim();
        Task task = new Todo(description);
        taskList.addTask(task);
        printTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds a deadline task.
     *
     * @param input Full deadline command.
     * @param taskList List to which the task is added.
     */
    private static void addDeadline(String input, TaskList taskList){
        int byIndex = input.indexOf("/by ");
        String description = input.substring(9, byIndex).trim();
        String by = input.substring(byIndex + 4).trim();
        Task task = new Deadline(description, by);
        taskList.addTask(task);
        printTaskAdded(task, taskList.getTaskCount());
    }

    /**
     * Parses and adds an event task.
     *
     * @param input Full event command.
     * @param taskList List to which the task is added.
     */
    private static void addEvent(String input, TaskList taskList){
        int fromIndex = input.indexOf("/from ");
        int toIndex = input.indexOf("/to ");
        String description = input.substring(6, fromIndex).trim();
        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 4);
        Task task = new Event(description, from, to);
        taskList.addTask(task);
        printTaskAdded(task, taskList.getTaskCount());
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

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            } else if (input.equals("list")) {
                System.out.println(LINE);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskList.getTaskCount(); i++) {
                    System.out.println((i + 1) + "." + taskList.getTask(i));
                }
                System.out.println("Now you have " + taskList.getTaskCount() + " tasks in the list.");
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                try{
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= taskList.getTaskCount()){
                        System.out.println(LINE);
                        System.out.println("Error: Task not found!");
                        System.out.println(LINE);
                    }
                    else if (taskList.getTask(index).isDone()) {
                        System.out.println(LINE);
                        System.out.println("This task is already marked as done!");
                        System.out.println(LINE);
                    } else {
                        taskList.getTask(index).markAsDone();

                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(taskList.getTask(index).toString());
                        System.out.println(LINE);
                    }
                } catch (NumberFormatException e){
                    System.out.println(LINE);
                    System.out.println("Error: Please enter a valid task!");
                    System.out.println(LINE);
                }

            } else if (input.startsWith("unmark ")) {
                try{
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= taskList.getTaskCount()){
                        System.out.println(LINE);
                        System.out.println("Error: Task not found!");
                        System.out.println(LINE);
                    }
                    else if (!taskList.getTask(index).isDone()) {
                        System.out.println(LINE);
                        System.out.println("This task is already marked as not done!");
                        System.out.println(LINE);
                    } else {
                        taskList.getTask(index).markAsUndone();

                        System.out.println(LINE);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(taskList.getTask(index).toString());
                        System.out.println(LINE);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(LINE);
                    System.out.println("Error: Task not found!");
                    System.out.println(LINE);
                }
            }
            else if(input.startsWith("todo ")){
                addTodo(input, taskList);
            }
            else if(input.startsWith("deadline ")){
                addDeadline(input, taskList);
            }
            else if(input.startsWith("event ")){
                addEvent(input, taskList);
            }
        }
        scanner.close();
    }
}
