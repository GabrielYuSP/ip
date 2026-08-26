import java.util.Scanner;

public class Goober {
    public static void main(String[] args) {
        String banner = "____________________________________________________________\n" +
                "  ____              _               \n"
                + " / ___| ___   ___  | |__   ___  _ __  \n"
                + "| |  _ / _ \\ / _ \\ | '_ \\ / _ \\| '__| \n"
                + "| |_| | (_) | (_) || |_) |  __/| |    \n"
                + " \\____|\\___/ \\___/ |_.__/ \\___||_|    \n"
                + "____________________________________________________________\n";

        String line = "____________________________________________________________";

        System.out.println(banner);
        System.out.println("Hello! I'm Goober.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        // Task Object to store up to 100 tasks
        Task[] tasks = new Task[100];
        // Task Counter for loop
        int taskCount = 0;

        while(true){
            String input = scanner.nextLine();
            // Base case, break
            if(input.equalsIgnoreCase("bye")){
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            // List Function
            else if(input.equals("list")){
                System.out.println(line);
                System.out.println("Here are the tasks in your list:");
                // Loop through tasks, taskCount number of times
                for(int i = 0; i < taskCount; i++){
                    System.out.println((i+1) + "." + tasks[i]);
                }
                System.out.println(line);
            }
            // Mark as done
            else if(input.startsWith("mark ")){
                int index = Integer.parseInt(input.substring(5)) - 1;
                if(tasks[index].isDone()){
                    System.out.println(line);
                    System.out.println("This task is already marked as done!");
                    System.out.println(line);
                }
                else{
                    tasks[index].markAsDone();

                    System.out.println(line);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index].toString());
                    System.out.println(line);
                }
            }
            // Unmark
            else if(input.startsWith("unmark ")){
                int index = Integer.parseInt(input.substring(7)) -1;
                // Check if the task is already unmarked
                if(tasks[index].isDone()){
                    System.out.println(line);
                    System.out.println("This task is already marked as not done!");
                    System.out.println(line);
                }
                else{
                    tasks[index].markAsUndone();

                    System.out.println(line);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index].toString());
                    System.out.println(line);
                }
            }
            // Store input as task into array
            else{
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(line);
                System.out.println("added " + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
