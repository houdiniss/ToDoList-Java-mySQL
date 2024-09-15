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
	
	
}
