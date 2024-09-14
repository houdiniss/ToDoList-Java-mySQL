
public class Task {
	
	String description;
	boolean isCompleted;
	
	Task(String description, boolean isCompleted) {
		this.description = description;
		this.isCompleted = isCompleted;
	}
	
	@Override
    public String toString() {
        return "Task: " + description + " | Completed: " + isCompleted;
    }
}