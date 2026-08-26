public class Task {
    private String description;
    private boolean isDone;

    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void markAsDone(){
        this.isDone = true;
    }

    public void markAsUndone(){
        this.isDone = false;
    }

    public String getStatus(){
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString(){
        return "[" + getStatus() + "] " + description;
    }
}
