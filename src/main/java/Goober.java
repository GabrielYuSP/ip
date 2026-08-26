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
        // Array to store up to 100 tasks
        String[] tasks = new String[100];
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
                // Loop through tasks, taskCount number of times
                for(int i = 0; i < taskCount; i++){
                    System.out.println((i+1) + "." + tasks[i]);
                }
                System.out.println(line);
            }
            // Store input as task into array
            else{
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(line);
                System.out.println("added " + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }
}
