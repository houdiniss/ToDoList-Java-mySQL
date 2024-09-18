import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDoList {
	
	private final static String DB_URL = "DB_URL";
	private final static String DB_USER = "DB_USER";
	private final static String DB_PASSWORD = "DB_PASSWORD";
	
	
	ArrayList<Task> tasks;
	
	ToDoList() {
		tasks = new ArrayList<>();
	}
	
	
	
	public static Connection makeConnection() throws SQLException {
		Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");	// Load MySQL JDBC driver  
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);		// Connecting to the Database 
																						// through DriverManager.getConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");			// If the driver is not found 
            e.printStackTrace();										// it throws an error
        }
        return connection;				// returned object used later for making queries
	}
	
	
	
	
	public void addTask(String description) {
		String sql = "INSERT INTO tasks (task_description, is_completed) VALUES (?, ?)";   // The SQL Query we want to execute
		
		try (
			  Connection connection = makeConnection();
			  PreparedStatement statement = connection.prepareStatement(sql);	// Creating a 'PreparedStatement' object
			){																	// which will execute the SQL query string.

		      statement.setString(1, description);
		      statement.setBoolean(2, false);
	
		      int rowsAffected = statement.executeUpdate();		// The 'executeUpdate' method returns the number of rows affected.
		      System.out.println("Task added to database. " + rowsAffected + " row(s) inserted.");
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	}
	
	
	
	public void loadTasksFromDatabase() {
        String sql = "SELECT * FROM tasks";			// The SQL Query we want to execute

        try (
        	 Connection connection = makeConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery();	// The 'executeQuery' method is used to execute the SELECT query.
            ) {													// The result of this is stored in the resultSet object.
        														// The resultSet object is a 'table' of the database's data.
        	
            while (resultSet.next()) {	 // This while loop iterates through the rows of the resultSet.Each row represents a Task.
                int id = resultSet.getInt("id");
                String description = resultSet.getString("task_description");
                boolean isCompleted = resultSet.getBoolean("is_completed");

                Task task = new Task(description, isCompleted);		// Creating new Task object for every setResult's row.
                tasks.add(task);  // Add each task to the ArrayList

                System.out.println("Loaded Task ID: " + id + ", Description: " + description + ", Completed: " + isCompleted);
            }
        } catch (SQLException e) {		// Handling errors that might occur while interacting with the database.
            e.printStackTrace();
        }
    }
	
	
	
	public void removeTask(int task_id) {
		String sql = "DELETE FROM tasks WHERE id = ?";
		
		try(Connection connection = makeConnection();
			PreparedStatement statement = connection.prepareStatement(sql);) 
		{
			statement.setInt(1, task_id);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Task removed from database. " + rowsAffected + " row(s) affected.");
			} else {
				System.out.println("Task was not removed from database. " + rowsAffected + " row(s) affected.Please try again...");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setCompletedState(boolean value, int task_id) {
		String sql = "UPDATE tasks SET is_completed = ? WHERE id = ?";
		
		try(Connection connection = makeConnection();
			PreparedStatement statement = connection.prepareStatement(sql);) {
			
			statement.setBoolean(1, value);
			statement.setInt(2, task_id);
			
			int rowsAffected = statement.executeUpdate();	
			
			if(rowsAffected > 0) {
				System.out.println("Task with an id of " + task_id + ", has now has a completed value of : " + value + "\n" + rowsAffected + " row(s) affected.");
			} else {
				System.out.println("Something went wrong.\n " + rowsAffected + " row(s) affected.Please try again...");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void viewTask() {
		if(tasks.isEmpty()) {
			System.out.println("There are no tasks registered...");
		} else {
			for(int i=0; i<tasks.size(); i++) {
				System.out.println(tasks.get(i));
			}
		}
		
	}
	
	
	
}
