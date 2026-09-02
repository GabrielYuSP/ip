/**
 * Stores and manages the tasks in the application.
 */
public class TaskList {
    private Task[] tasks = new Task[100];
    private int taskCount = 0;

    /**
     * Adds a task to the end of the task list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task){
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Returns the task at the specified zero-based index.
     *
     * @param index Zero-based task index.
     * @return Task at the specified index.
     */
    public Task getTask(int index){
        return tasks[index];
    }

    /**
     * Returns the number of tasks currently stored.
     *
     * @return Current task count.
     */
    public int getTaskCount(){
        return taskCount;
    }
}
