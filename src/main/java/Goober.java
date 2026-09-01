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
                System.out.println(LINE);
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                if (tasks[index].isDone()) {
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
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                if (tasks[index].isDone()) {
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
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(LINE);
                System.out.println("added " + input);
                System.out.println(LINE);
            }
        }
        scanner.close();
    }
}
