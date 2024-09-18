
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ToDoList list = new ToDoList();
		
		list.loadTasksFromDatabase();

		
		System.out.println("\nTo-Do List Menu:");
        System.out.println("\n1. Add Task");
        System.out.println("2. Remove Task");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. View Tasks");
        System.out.println("5. Exit");
        System.out.println("\nChoose an option: ");
		
        int choice = scanner.nextInt();	
        
        
        switch(choice) {
        	case 1:
        		System.out.println("You chose to add a Task.");
        		System.out.println("Please write the description of the task and press Enter");
        		scanner.nextLine(); // Skips next line
        		String description = scanner.nextLine();
        		list.addTask(description);
        		break;
        	case 2:
        		System.out.println("You chose to remove a Task.");
        		System.out.println("Please enter the id of the task and press Enter");
        		int task_id = scanner.nextInt();
        		list.removeTask(task_id);
        		break;
        	case 3:
        		System.out.println("Please enter the id of the task and press Enter");
        		int id= scanner.nextInt();
        		System.out.println("Please enter 'true' as completed or 'false' as not completed");
        		boolean value = scanner.nextBoolean();
        		list.setCompletedState(value, id);
        		break;
        	case 4:
        		list.viewTask();
        		break;
        	case 5:
        		System.out.println("You chose num 5.");
        		break;
        	default:
        	System.out.println("You need to choose between 1-5..Restart and try again :D");
        }
        
        scanner.close();
        
    }

}
