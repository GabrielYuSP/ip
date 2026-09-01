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

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Starts the interactive task management application.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        Scanner scanner = new Scanner(System.in);
        // Stores up to 100 tasks.
        Task[] tasks = new Task[100];
        // Tracks the number of tasks currently stored.
        int taskCount = 0;

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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                // Print out number of tasks in the list
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                try{
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= taskCount){
                        System.out.println(LINE);
                        System.out.println("Error: Task not found!");
                        System.out.println(LINE);
                    }
                    else if (tasks[index].isDone()) {
                        System.out.println(LINE);
                        System.out.println("This task is already marked as done!");
                        System.out.println(LINE);
                    } else {
                        tasks[index].markAsDone();

                        System.out.println(LINE);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(tasks[index].toString());
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
                    if (index < 0 || index >= taskCount){
                        System.out.println(LINE);
                        System.out.println("Error: Task not found!");
                        System.out.println(LINE);
                    }
                    else if (!tasks[index].isDone()) {
                        System.out.println(LINE);
                        System.out.println("This task is already marked as not done!");
                        System.out.println(LINE);
                    } else {
                        tasks[index].markAsUndone();

                        System.out.println(LINE);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(tasks[index].toString());
                        System.out.println(LINE);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println(LINE);
                    System.out.println("Error: Task not found!");
                    System.out.println(LINE);
                }
            }
            else if(input.startsWith("todo ")){
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                taskCount++;
                printTaskAdded(tasks[taskCount-1], taskCount);
            }
            else if(input.startsWith("deadline ")){
                int byIndex = input.indexOf("/by ");
                String description = input.substring(9, byIndex).trim();
                String by = input.substring(byIndex + 4).trim();
                tasks[taskCount] = new Deadline(description, by);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            }
            else if(input.startsWith("event ")){
                int fromIndex = input.indexOf("/from ");
                int toIndex = input.indexOf("/to ");
                String description = input.substring(6, fromIndex).trim();
                String from = input.substring(fromIndex + 6, toIndex).trim();
                String to = input.substring(toIndex + 4);
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            }
        }
        scanner.close();
    }
}
